package com.xk.customview.custom.strategyanimation;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by xuekai on 2019/3/27.
 */
public class RoationState extends StrategyState {
    public RoationState(final View view, int width, int height) {
        super(view, width, height);
        //构造这个状态的时候，就开始一个动画
        animator = ValueAnimator.ofInt(0,359);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                canvasAngle = (int) animation.getAnimatedValue();
                view.invalidate();
            }
        });
        animator.setRepeatCount(-1);
        animator.setDuration(1500);
        animator.start();
    }

    @Override
    public void drawState(Canvas canvas) {
        drawBg(canvas);
        drawBall(canvas);
    }
}
