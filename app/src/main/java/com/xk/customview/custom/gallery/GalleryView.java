package com.xk.customview.custom.gallery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * Created by xuekai on 2017/2/27.
 */

public class GalleryView extends RecyclerView {
    private static final String TAG = "GalleryView";
    private Scroller mScroller;
    private float lastX;
    private float lastY;

    private int startScrollX;

    public GalleryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(getContext(),new LinearInterpolator());
    }



    /**
     * 下一张图片
     */
    public void next() {
        startScrollX=getScrollX();
        smoothScroll(getWidth() / 3);
    }

    /**
     * 上一张图片
     */
    public void last() {
        smoothScroll(-getWidth() / 3);

    }

    @Override
    public void computeScroll() {
//        int lastX = 0;
//        if (isFirst) {
//            isFirst = false;
//        } else {
//            lastX = mScroller.getCurrX();
//        }
        if (mScroller.computeScrollOffset()) {
            Log.e("GalleryView","computeScroll"+(mScroller.getCurrX()));
            scrollBy(10 , 100);
            invalidate();
        }
    }

    private boolean isFirst;

    public void smoothScroll(int dx) {
        mScroller.startScroll(getScrollX(), getScrollY(), dx, 0, 300);
        isFirst = true;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}
