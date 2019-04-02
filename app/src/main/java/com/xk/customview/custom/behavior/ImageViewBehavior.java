package com.xk.customview.custom.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by xuekai on 2019/3/31.
 */
public class ImageViewBehavior extends CustomBehavior<ImageView> {

    public ImageViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onNestedScroll(View child, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(child, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        child.offsetTopAndBottom(-dyUnconsumed);
        Log.i("ImageViewBehavior","onNestedScroll-->"+dyConsumed+"  "+dyUnconsumed);

    }
}
