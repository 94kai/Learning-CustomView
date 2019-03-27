package com.xk.customview.custom.strategyanimation;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.view.View;

/**
 * 展开背景图片东湖a
 * Created by xuekai on 2019/3/27.
 */
public class OpenState extends StrategyState {
    public OpenState(final View view, int width, int height) {
        super(view, width, height);
        //构造这个状态的时候，就开始一个动画
        animator = ValueAnimator.ofInt(0, height / 2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bgRadius = (int) animation.getAnimatedValue();
                view.invalidate();
            }
        });
        animator.setDuration(1500);
        animator.start();
    }

    @Override
    public void drawState(Canvas canvas) {
        drawBg(canvas);
    }
}
