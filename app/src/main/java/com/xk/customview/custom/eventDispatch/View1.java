package com.xk.customview.custom.eventDispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xuekai on 2017/2/27.
 */

public class View1 extends View {
    private static final String TAG = "小弟";
    public View1(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEnabled(false);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG,"dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"onTouchEvent");
        return super.onTouchEvent(event);
    }
}
