package com.xk.customview.custom.strategyanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xuekai on 2019/3/27.
 */
public class StrategyAnimationView extends View {
    private StrategyState state;

    public StrategyAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (state == null) {
            state = new RoationState(this, getWidth(), getHeight());
        }
        state.drawState(canvas);
    }

    /**
     * 数据加载完成，开始播放聚合、放大动画
     */
    public void overAnimation() {
        state.cancel();
        state = new ShrinkState(this, getWidth(), getHeight());
    }

    public void openBg() {
        state = new OpenState(this, getWidth(), getHeight());
    }
}
