package com.xk.customview.custom.recyclerview;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.xk.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 大致实现了一个可以向上滚动，回收上面item，添加下面item的功能。不借助scrollBy方法的实现。着重理解原理
 * Created by xuekai on 2019/4/6.
 */
public class RecyclerView extends ViewGroup {
    @IdRes
    private final int TAG_TYPE = 1;
    /**
     * 内容的偏移量，比如内容的头部距离view的顶部一段距离了，那段距离就是scrollY
     */
    int scrollY;
    private Adapter adapter;
    //当前显示的view
    private List<View> viewList;
    //行数
    private int rowCount;
    //初始化???
    private boolean needRelayout;
    //recyclerview的宽度
    private int width;
    //recyclerview的高度
    private int height;
    //孩子们的高度
    private int[] heights;
    //回收池
    private Recycler recycler;
    //第一个显示的item的position，该变量用来记录第一个显示的item的position。在onLayout的时候，从这个position开始向后布局
    private int firstVisiablePosition = 0;
    //手指按下的Y
    private float downY;
    //上一次事件的Y
    private float lastY;
    private int scaledTouchSlop;

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        init();
        requestLayout();
    }

    private void init() {
        ViewConfiguration vc = ViewConfiguration.get(getContext());
        scaledTouchSlop = vc.getScaledTouchSlop();
        needRelayout = true;
        viewList = new ArrayList<>();
        if (adapter != null) {
            recycler = new Recycler(adapter.getViewTypeCount());
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (adapter != null && needRelayout) {
            needRelayout = false;
            viewList.clear();
            removeAllViews();
            //正在摆放的孩子的top。
            // TODO: by xk 2019/4/6 18:41 如果只是第一次初始化，top为0，但是如果实在刷新的话，可能第一个显示的孩子的top并不一定是0
            int top = 0;
            if (adapter != null) {
                width = r - l;
                height = b - t;
                // TODO: by xk 2019/4/6 18:43 为啥从0开始，应该是从firstVisiableView开始
                //开始循环摆放子控件，但是不是从0开始摆所有，而是只摆放边界之内的。通过top<=height来判断
                for (int i = 0; i < adapter.getCount() && top <= height; i++) {
                    makeAndStepView(0, top, r, top + heights[i], i);
                    top += heights[i];
                }
            }
        }

    }

    /**
     * 创建、获取并且安装一个子View
     */
    private void makeAndStepView(int l, int t, int r, int b, int position) {
        View view = obtainView(position);
        //测量孩子。说实话，我们下面layout的时候，位置父亲已经制定好了，不需要测量了。毕竟测量出来的数字也就是为了layout使用。但是最好还是测量一下，毕竟子view自定义控件里面或许自己需要。
        view.measure(MeasureSpec.makeMeasureSpec(r - l, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(b - t, MeasureSpec.EXACTLY));
        view.layout(l, t, r, b);
    }

    private View obtainView(int position) {
        //通过回收池拿到了一个View，可能为空。然后让adapter去处理。如果是空的，getView中创建。如果不为空，getView设置一下数据直接给返回。
        View convertView = recycler.getViewByItemType(adapter.getItemType(position));
        View view = adapter.getView(position, convertView, this);
        if (view == null) {
            throw new RuntimeException("getView返回的view为空了");
        }
        //???为啥要给第一个添加
        viewList.add(view);
        view.setTag(R.id.tag_type, adapter.getItemType(position));
        addView(view);
        return view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int tempH = 0;
        //把所有孩子的高度遍历放到heights集合中。同时，计算出recyclerview的高度。
        if (adapter != null) {
            heights = new int[adapter.getCount()];
            for (int i = 0; i < adapter.getCount(); i++) {
                heights[i] = adapter.getHeight();
                tempH += heights[i];
            }
        }
        //上一步计算的高度和控件父亲约束的高度取最小值
        int minHeight = Math.min(heightSize, tempH);
        setMeasuredDimension(widthSize,
                minHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = downY = ev.getY();
                Log.i("RecyclerView", "onInterceptTouchEvent-->down");
                break;
            case MotionEvent.ACTION_MOVE:
                float diffY = downY - ev.getY();
                if (Math.abs(diffY) > Math.abs(scaledTouchSlop)) {
                    Log.i("RecyclerView", "onInterceptTouchEvent-->move");
                    //拦截
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("RecyclerView", "onTouchEvent" + event.getAction());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int diffY = (int) (lastY - event.getY());
                lastY = event.getY();
                scrollBy(0, diffY);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void scrollBy(int x, int y) {
        scrollY += y;
        //添加孩子、删除孩子
        View firstVisiableView = viewList.get(0);
        if (y > 0) {

            if (-firstVisiableView.getTop() > adapter.getHeight()) {
                //这个被划出去了
                removeTopView(firstVisiableView);
            }

            if (needAddBottom(y)) {
                addBottomView();
            }

        }

        firstTop = viewList.get(0).getTop() - y;
        reLayoutChild();
    }

    private void addBottomView() {
        int lastPosition = firstVisiablePosition + viewList.size();
        View view = obtainView(lastPosition);
        //测量孩子。说实话，我们下面layout的时候，位置父亲已经制定好了，不需要测量了。毕竟测量出来的数字也就是为了layout使用。但是最好还是测量一下，毕竟子view自定义控件里面或许自己需要。
        view.measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(adapter.getHeight(), MeasureSpec.EXACTLY));
    }

    private boolean needAddBottom(int y) {
        int firstTop = viewList.get(0).getTop() - y;
        int height = 0;
        for (View view : viewList) {
            height += view.getHeight();
        }
        //屏幕展示的内容的高度
        int showHeight = height - firstTop;
        return showHeight < getHeight();
    }

    private void removeTopView(View view) {
        removeView(view);
        viewList.remove(0);
        firstVisiablePosition++;
        recycler.saveView(view, (Integer) view.getTag(R.id.tag_type));
    }

    int firstTop = 0;

    private void reLayoutChild() {
        int top = firstTop;
        for (View view : viewList) {
            view.layout(0, top, width, adapter.getHeight() + top);
            top += adapter.getHeight();
        }
    }


    @Override
    public void removeView(View view) {
        super.removeView(view);
    }
}
