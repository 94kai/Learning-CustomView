package com.xk.customview.custom.gallery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
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
        int lastX = 0;
        if (isFirst) {
            isFirst = false;
        } else {
            lastX = mScroller.getCurrX();
        }

        if (mScroller.computeScrollOffset()) {
            scrollBy(mScroller.getCurrX() - lastX, 0);
            float  progress=  (10);
            GalleryItem item= (GalleryItem) getChildAt(0);
            item.change(progress);
            invalidate();
        }
    }

    private boolean isFirst;

    public void smoothScroll(int dx) {

//        getScrollX()

        mScroller.startScroll(getScrollX(), getScrollY(), dx, 0, 3000);
        isFirst = true;
        invalidate();
        Log.e(TAG, "mStartScroller");
    }

}
