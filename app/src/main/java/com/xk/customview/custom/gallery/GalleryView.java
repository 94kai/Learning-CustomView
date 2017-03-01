package com.xk.customview.custom.gallery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
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

    private boolean isLeft;

    private GalleryItem galleryRightItem1;
    private GalleryItem galleryRightItem2;
    private GalleryItem galleryRightItem3;
    //动画事件
    private int duration = 300;
    //间隔的事件
    private int interval = 1000;
    private GalleryAdapter galleryAdapter;

    public GalleryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext(), new LinearInterpolator());
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        galleryAdapter = new GalleryAdapter(getContext(), null);
        setAdapter(galleryAdapter);
    }

    public void setDatas(SparseArray<Integer> datas) {
        galleryAdapter.setDatas(datas);
    }

    /**
     * 下一张图片
     */
    public void next() {
        if (!mScroller.isFinished()) return;

        isLeft = false;
        isPrepare = false;
        //runnable,让即将出现的那个一个item提前变成梯形
        smoothScroll(getWidth() / 3, new Runnable() {
            @Override
            public void run() {
                galleryRightItem1 = (GalleryItem) getChildAt(1);
                galleryRightItem2 = (GalleryItem) getChildAt(2);
                galleryRightItem3 = (GalleryItem) getChildAt(3);
                galleryRightItem3.setMatrix(GalleryItem.STATE_RIGHT);
                isPrepare = true;
            }
        }, 10);
    }

    @Override
    public void onChildAttachedToWindow(View child) {
        if (getChildAt(0) == child) {
            ((GalleryItem) child).setOriginalState(GalleryItem.STATE_LEFT);
        }
        if (getChildAt(1) == child) {
            ((GalleryItem) child).setOriginalState(GalleryItem.STATE_MIDDLE);
        }
        if (getChildAt(2) == child) {
            ((GalleryItem) child).setOriginalState(GalleryItem.STATE_RIGHT);
        }
    }

    private GalleryItem galleryLeftItem0;
    private GalleryItem galleryLeftItem1;
    private GalleryItem galleryLeftItem2;
    private GalleryItem galleryLeftItem3;


    /**
     * 上一张图片
     */
    public void last() {
        if (!mScroller.isFinished()) return;
        isLeft = true;
        isPrepare = false;

        smoothScroll(-getWidth() / 3, new Runnable() {
            @Override
            public void run() {
                //从left变成middle
                galleryLeftItem0 = (GalleryItem) getChildAt(0);
                galleryLeftItem1 = (GalleryItem) getChildAt(1);
                galleryLeftItem2 = (GalleryItem) getChildAt(2);
                galleryLeftItem3 = (GalleryItem) getChildAt(3);
                galleryLeftItem0.setMatrix(GalleryItem.STATE_LEFT);
                galleryLeftItem3.setMatrix(GalleryItem.STATE_RIGHT);
                isPrepare = true;
            }
        }, 10);

    }

    boolean isPrepare = false;

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
            if (isPrepare) {
                onScroll(mScroller.timePassed() * 1f / mScroller.getDuration());
            }
            invalidate();
        }
    }

    private boolean isFirst;


    public void smoothScroll(int dx, Runnable runnable, int time) {
        mScroller.startScroll(getScrollX(), getScrollY(), dx, 0, duration);
        isFirst = true;
        invalidate();
        if (time == 0) {
            runnable.run();
        } else {
            postDelayed(runnable, time);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }

    private void onScroll(float progress) {
        if (progress > 1) {
            progress = 1;
        }
        if (isLeft) {
            //向左滑动
            if (galleryLeftItem1 != null && galleryLeftItem2 != null) {
                galleryLeftItem1.change(progress, GalleryItem.STATE_LEFT, GalleryItem.STATE_MIDDLE);
                galleryLeftItem2.change(progress, GalleryItem.STATE_MIDDLE, GalleryItem.STATE_RIGHT);
            }

        } else {
            //向右滑动
            if (galleryRightItem1 != null && galleryRightItem2 != null) {
                galleryRightItem1.change(progress, GalleryItem.STATE_MIDDLE, GalleryItem.STATE_LEFT);
                galleryRightItem2.change(progress, GalleryItem.STATE_RIGHT, GalleryItem.STATE_MIDDLE);

            }

        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        scrollToPosition(Integer.MAX_VALUE / 2);
    }

    private boolean isStop = true;

    public void stop() {
        isStop = true;
    }

    public void start() {
        isStop = false;
        play();
    }

    /**
     * 设置动画执行的时间
     * @param duration
     */
    public void setDuration(int duration){
        this.duration=duration;
    } /**
     * 设置动画执行的时间
     * @param interval
     */
    public void setInterval(int interval){
        this.interval=interval;
    }
    private void play() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isStop) {
                    next();
                    play();
                }
            }
        }, interval + duration);
    }
}
