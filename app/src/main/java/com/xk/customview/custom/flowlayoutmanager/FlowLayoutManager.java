package com.xk.customview.custom.flowlayoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
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

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

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
        //把之前添加的view全部分离
//        detachxxx/removexxx的区别。
        detachAndScrapAttachedViews(recycler);
        for (int i = 0; i < frames.size(); i++) {
            Rect rect = frames.get(i);
            layoutDecorated(getChildAt(i), rect.left, rect.top, rect.right, rect.bottom);
        }

        // TODO: by xk 2019/4/26 下午12:04 移除屏幕外面的 。
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    @Override
    public boolean canScrollVertically() {
        return true;

    }
}
