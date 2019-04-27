package com.xk.customview.custom.flowlayoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author xuekai
 * @date 2019/4/26
 */
public class FlowLayoutManager extends RecyclerView.LayoutManager {
    /**
     * 存放所有孩子的位置。认为第一个孩子所处的位置为0，0点
     */
    SparseArray<Rect> frames = new SparseArray<>();

    /**
     */
    int scrollY = 0;


    //保存所有item偏移量信息
    //    所有数据高度和
    private int totalHeight = 0;

    /**
     * 定义一个全局的变量，用来存放当前recyclerview滚了多高了
     * 滑动偏移量
     * 如果是正的就是在向上滑，展现上面的view
     * 如果是负的向下
     */
    private int verticalScrollOffset = 0;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

//    在RecyclerView中，有两个缓存：Scrap和Recycle。Scrap中文就是废料的意思，Recycle对应是回收的意思。这两个缓存有什么作用呢？首先Scrap缓存是指里面缓存的View是接下来需要用到的，即里面的绑定的数据无需更改，可以直接拿来用的，是一个轻量级的缓存集合；而Recycle的缓存的View为里面的数据需要重新绑定，即需要通过Adapter重新绑定数据。关于这两个缓存的使用场景，下一节详细介绍。
//
//    当我们去获取一个新的View时，RecyclerView首先去检查Scrap缓存是否有对应的position的View，如果有，则直接拿出来可以直接用，不用去重新绑定数据；如果没有，则从Recycle缓存中取，并且会回调Adapter的onBindViewHolder方法（当然了，如果Recycle缓存为空，还会调用onCreateViewHolder方法），最后再将绑定好新数据的View返回。


    //    前面我们了解到，RecyclerView中有二级缓存，我们可以自己选择将View缓存到哪里。我们有两种选择的方式：Detach和Remove。Detach的View放在Scrap缓存中，Remove掉的View放在Recycle缓存中；那我们应该如何去选择呢？
//
//    在什么样的场景中使用Detach呢？主要是在我们的代码执行结束之前，我们需要反复去将View移除并且马上又要添加进去时，选择Datach方式，比如：当我们对View进行重新排序的时候，可以选择Detach，因为屏幕上显示的就是这些position对应的View，我们并不需要重新去绑定数据，这明显可以提高效率。使用Detach方式可以通过函数detachAndScrapView()实现。
//
//    而使用Remove的方式，是当View不在屏幕中有任何显示的时候，你需要将它Remove掉，以备后面循环利用。可以通过函数removeAndRecycleView()实现。
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.i("FlowLayoutManager","onLayoutChildren-->");
        //如果没有item，直接返回
        if (getItemCount() <= 0) {
            return;
        }
        // 跳过preLayout，preLayout主要用于支持动画
        if (state.isPreLayout()) {
            return;
        }
        //onLayout之前，处理一下
        detachAndScrapAttachedViews(recycler);
        //可以理解为：准备摆放孩子时，当前光标所在的位置。0，0点在第一个孩子那里，而不是RecyclerView左上角
        int yOffset = 0, xOffset = 0;
        //一行中最高的那个孩子的高度
        int lineMaxHeight = 0;
        //遍历所有孩子。其实这里可以优化，只遍历可以显示在屏幕上的孩子
        for (int i = 0; i < getItemCount(); i++) {
            //通过回收池，通过position拿到一个view。其实就是从各种缓存池等地方或者通过onCreateViewHolder，然后通过getItemType
            //把position转成viewtype，从而拿到一个对应的view
            View child = recycler.getViewForPosition(i);
            //要布局view，就需要知道他的宽高，而要测量，肯定要依托于父亲的参数，所以先addView
            addView(child);
            //利用layoutmanager提供的测量方法测量。后两个参数表示used的宽度，也就是测量的时候，需要减去这些宽度，作为父亲的可用宽度进行测量
            measureChildWithMargins(child, 0, 0);
            //测量结束，拿孩子的宽高，其实可以直接通过child.getMeasureWidth的，但是child可能会设置一些边距之类的，所以通过下面
            //方法，这样获取到的才是孩子的宽高，所以孩子的宽高为真实宽高加上他的边距。
            int decoratedMeasuredWidth = getDecoratedMeasuredWidth(child);
            int decoratedMeasuredHeight = getDecoratedMeasuredHeight(child);
//            child.layout(0, top, decoratedMeasuredWidth, top + decoratedMeasuredHeight);
            Rect rect = frames.get(i);
            if (rect == null) {
                rect = new Rect();
                frames.put(i, rect);
            }
            if (xOffset + decoratedMeasuredWidth > getWidth()) {//换行
                xOffset = 0;
                yOffset += lineMaxHeight;
                lineMaxHeight = 0;
            }
            rect.set(xOffset, yOffset, xOffset + decoratedMeasuredWidth, yOffset + decoratedMeasuredHeight);
            xOffset += decoratedMeasuredWidth;
            lineMaxHeight = Math.max(decoratedMeasuredHeight, lineMaxHeight);
        }
        totalHeight = yOffset + lineMaxHeight;

        //把view进行一个轻量的移除
        fill(recycler, state);
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.i("FlowLayoutManager","fill-->");
        //对于不显示的区域的item进行recycle
        //recyclerview显示区域
        detachAndScrapAttachedViews(recycler);

        Rect visibleRect = new Rect(0, verticalScrollOffset, getWidth(), verticalScrollOffset + getHeight());
        for (int i = 0; i < getItemCount(); i++) {
            boolean intersects = Rect.intersects(visibleRect, frames.get(i));
            View child = getChildAt(i);
            if (!intersects) {
//                //这个item不在可见区域内，所以要recycle
//                // TODO: by xk 2019/4/26 下午3:32 目前发现获取到的孩子是空的，因为上面调用了 detachAndScrapAttachedViews，但是也可能是因为没显示出来过。所以后续确认一下，这里是否有不为空的情况。
                if (child != null) {
                    removeAndRecycleView(child, recycler);
                }else{
                    Log.i("FlowLayoutManager","fill-->null");
                }
            }
        }
        for (int i = 0; i < getItemCount(); i++) {
            boolean intersects = Rect.intersects(visibleRect, frames.get(i));
            if (intersects) {
                //item和可见区域有交集，所以这个item要attach
                View child = recycler.getViewForPosition(i);
                Rect rect = frames.get(i);
                measureChildWithMargins(child, 0, 0);
                addView(child);
                layoutDecorated(child, rect.left, rect.top - verticalScrollOffset, rect.right, rect.bottom - verticalScrollOffset);
            }
        }

    }


    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        //实际滑动距离  dx
        int trval = dy;
//        如果滑动到最顶部  往下滑   verticalScrollOffset   -
//        第一个坐标值 减 以前最后一个坐标值  //记死
        if (verticalScrollOffset + dy < 0) {
            trval = -verticalScrollOffset;
        } else if (verticalScrollOffset + dy > totalHeight - getHeight()) {
//            如果滑动到最底部  往上滑   verticalScrollOffset   +
            trval = totalHeight - getHeight() - verticalScrollOffset;
        }
//        边界值判断
        verticalScrollOffset += trval;

        //平移容器内的item
        offsetChildrenVertical(trval);
        fill(recycler, state);
        return trval;
    }

    @Override
    public boolean canScrollVertically() {
        return true;

    }
}
