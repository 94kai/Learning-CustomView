package com.xk.customview.custom.revealdrawable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.xk.customview.R;


/**
 * Created by xuekai on 2019/3/14.
 */
public class MScrollView extends HorizontalScrollView {

    private LinearLayout linearLayout;

    public MScrollView(Context context) {
        super(context);
    }

    public MScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    int imageWidth = 300;
    int[] drawableIds = {
            R.drawable.avft,
            R.drawable.box_stack,
            R.drawable.bubble_frame,
            R.drawable.bubbles,
            R.drawable.bullseye,
            R.drawable.circle_filled,
            R.drawable.circle_outline,
    };
    int[] drawableActiveIds = {
            R.drawable.avft_active,
            R.drawable.box_stack_active,
            R.drawable.bubble_frame_active,
            R.drawable.bubbles_active,
            R.drawable.bullseye_active,
            R.drawable.circle_filled_active,
            R.drawable.circle_outline_active,
    };

    private void init() {
        linearLayout = new LinearLayout(getContext());
        addView(linearLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imageWidth));
        for (int i = 0; i < drawableIds.length; i++) {
            View view = new View(getContext());
            view.setBackgroundDrawable(new RevealDrawable(getResources().getDrawable(drawableIds[i]), getResources().getDrawable(drawableActiveIds[i])));
            linearLayout.addView(view, new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(imageWidth, imageWidth)));
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        int left = getWidth() / 2 - imageWidth / 2;
        int realLeft;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View childAt = linearLayout.getChildAt(i);
            realLeft = childAt.getLeft() - l;
            ((RevealDrawable) childAt.getBackground()).setRevert(false);
            if (realLeft < left - imageWidth) {
                childAt.getBackground().setLevel(0);
            } else if (realLeft > left + imageWidth) {
                childAt.getBackground().setLevel(0);
            } else {
                int A;
                int B;
                if ((realLeft < left)) {
                    A = left - imageWidth;
                    B = left;
                    ((RevealDrawable) childAt.getBackground()).setRevert(false);
                } else {
                    A = left;
                    B = left + imageWidth;
                    ((RevealDrawable) childAt.getBackground()).setRevert(true);
                }
                int v = (int) ((10000f * A / (A - B) - 10000F / (A - B) * realLeft));
                childAt.getBackground().setLevel( v);

            }
        }
    }
}
