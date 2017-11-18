package com.xk.customview.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 仿华为分类标签，可根据宽度自动换行
 * 宽度：
 * 1.match_parent: xxx
 * 2.wrap_content: 按照match_parent处理
 * 3.xxdp:具体宽度
 * 高度：
 * 1.match_parent: xxx
 * 2.wrap_content: xxx
 * <p>
 * Created by xuekai on 2017/11/2.
 */

public class HotLabelView extends ViewGroup {


    public HotLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams lp;
        int childCount = getChildCount();
        Log.i("HotLabelView", "onLayout-->" + l + " " + t + " " + r + " " + b);
        int left = 0, top = 0, right, bottom = 0;
        for (int i = 0; i < childCount; ++i) {

            final View child = getChildAt(i);
            lp = (MarginLayoutParams) child.getLayoutParams();

            if (top == 0) {
                top += lp.topMargin;
            }
            if (left + lp.leftMargin + child.getMeasuredWidth() < r) {//不换行

            } else {//换行
                top = bottom + lp.bottomMargin + lp.topMargin ;
//                top += top + lp.bottomMargin + lp.topMargin + child.getMeasuredHeight();
                left = 0;
            }

            left += lp.leftMargin;
            right = left + child.getMeasuredWidth();
            bottom = top + child.getMeasuredHeight();
            child.layout(left, top, right, bottom);

            left = left + child.getMeasuredWidth() + lp.rightMargin;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MarginLayoutParams lp = null;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int childCount = getChildCount();
        int maxHeight = 0;
        int currentWidth = 0;
        int maxWidth = 0;
        for (int i = 0; i < childCount; ++i) {
            final View child = getChildAt(i);
            lp = (MarginLayoutParams) child.getLayoutParams();

            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

            if ((widthSize - currentWidth) < (lp.leftMargin + lp.rightMargin + child.getMeasuredWidth())) {
                currentWidth = 0;
                maxHeight += child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            } else {
                currentWidth = currentWidth + lp.leftMargin + lp.rightMargin + child.getMeasuredWidth();
            }

            maxWidth= Math.max(maxWidth,currentWidth);
        }
        maxHeight += getChildAt(0).getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

        int heightResult = 0;
        int widthResult = widthSize;

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                widthResult = widthSize;
                break;
            default:
                widthResult = Math.min(maxWidth, widthSize);
//                widthResult = widthSize;
                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                heightResult = heightSize;
                break;
            default:
                heightResult = maxHeight;
                break;
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightResult, MeasureSpec.EXACTLY));
        setMeasuredDimension(widthResult, heightResult);
    }
}
