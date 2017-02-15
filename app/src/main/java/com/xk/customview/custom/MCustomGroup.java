package com.xk.customview.custom;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by xuekai on 2017/2/14.
 */

public class MCustomGroup extends FrameLayout {
    private static final String TAG = "MCustomGroup";
    private Scroller scroller;

    public MCustomGroup(Context context) {
        super(context);
    }

    public MCustomGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MCustomGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(getContext());
    }



    /**
     * 用来进行事件分发，一旦事件传到该View，那么这个方法一定会被执行。
     *
     * @param event
     * @return 受onTouchEvent和下一级的dispatchTouchEvent的影响，表示是否消费该事件
     */
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    /**
     * 用来表示是否拦截某事件，一旦拦截，那么同一个事件序列中，该方法不会被再次调用。
     *
     * @param ev
     * @return 是否拦截
     */
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Window w;
        return super.onInterceptTouchEvent(ev);
    }

    /**
     *
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"onTouchEvent");
        return super.onTouchEvent(event);
    }
}
