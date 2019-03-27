package com.xk.customview.custom.strategyanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.view.View;

/**
 * 收缩动画
 * Created by xuekai on 2019/3/27.
 */
public class ShrinkState extends StrategyState {
    public ShrinkState(final StrategyAnimationView view, int width, int height) {
        super(view, width, height);
        //构造这个状态的时候，就开始一个动画
        animator = ValueAnimator.ofInt(radius, radius + 100, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius = (int) animation.getAnimatedValue();
                view.invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.openBg();
            }
        });
        animator.setDuration(1500);
        animator.start();
    }

    @Override
    public void drawState(Canvas canvas) {
        drawBg(canvas);
        drawBall(canvas);
    }
}
