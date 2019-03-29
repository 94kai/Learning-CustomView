package com.xk.customview.custom.animatorframe;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.xk.customview.R;

/**
 * Created by xuekai on 2019/3/29.
 */
public class MLinearLayout extends LinearLayout {
    public MLinearLayout(Context context) {
        this(context, null);
    }

    public MLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public MLinearLayout(Context context, AttributeSet attrs, int i) {
        super(context, attrs, i);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((ScrollView) getParent()).setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int childCount = getChildCount();

                for (int i = 0; i < childCount; i++) {
                    MLinearLayout.MLayoutParams layoutParams = (MLayoutParams) getChildAt(i).getLayoutParams();
                    if (layoutParams.from == 1000) {
                        Log.i("MLinearLayout","onScrollChange-->"+scrollY);
                        performAnimation(getChildAt(i), layoutParams.from,  getChildAt(i).getTop()-scrollY);
                    }
                }
            }
        });
    }

    /**
     * 在这个父布局中处理各个孩子的动画。  也可以重写addView，给子View套一个View，把子View的属性传递给父View，让那个布局去处理动画。
     * @param childAt
     * @param from
     * @param top
     */
    private void performAnimation(View childAt, int from, int top) {
        Log.i("MLinearLayout","performAnimation-->"+top);
        int height = getHeight();
        int i = (int) (height - top);
        float i1 = i * 1f / height;
        childAt.setTranslationX(i1 * from);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MLayoutParams(getContext(), attrs);
    }


    class MLayoutParams extends LinearLayout.LayoutParams {

        public final int from;

        public MLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a =
                    c.obtainStyledAttributes(attrs, R.styleable.View);

            from = a.getInteger(R.styleable.View_from, 0);

            a.recycle();
        }
    }
}
