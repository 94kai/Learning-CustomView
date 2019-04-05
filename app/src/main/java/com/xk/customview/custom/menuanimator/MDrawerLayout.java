package com.xk.customview.custom.menuanimator;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.xk.customview.R;

/**
 * Created by xuekai on 2019/4/5.
 */
public class MDrawerLayout extends DrawerLayout {
    public MDrawerLayout(Context context) {
        super(context);
    }

    private float slideOffset;

    public MDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        addDrawerListener(new DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                MDrawerLayout.this.slideOffset = slideOffset;
                Log.i("MDrawerLayout", "offsetLeftAndRight-->" + slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            // TODO: by xk 2019/4/5 19:41 如果打开，则关闭，并且return true，否则return false
            closeDrawers();
            // TODO: by xk 2019/4/5 19:42 回调给BezierView，触发最右边的按钮的点击事件
            return true;
        }
        Log.i("MDrawerLayout", "dispatchTouchEvent-->" + ev.getY());
        BezierView drawer = findViewById(R.id.drawer);
        drawer.set((int) (getMeasuredWidth() * slideOffset), (int) ev.getRawY());
        return super.dispatchTouchEvent(ev);
    }

    public MDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
