package com.xk.customview.custom.strategyanimation;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.xk.customview.R;

/**
 * Created by xuekai on 2019/3/27.
 */
public abstract class StrategyState {
    /**
     * 颜色
     */
    private final int[] colorArray;
    private final int width;
    private final int height;
    private final Paint ballPaint;
    private final Paint bgPaint;
    //画布初始的角度，用来实现旋转效果
    protected int canvasAngle;
    //白色背景的半径
    protected int bgRadius = -1;

    protected ValueAnimator animator;

    public StrategyState(View view, int width, int height) {
        colorArray = view.getContext().getResources().getIntArray(R.array.splash_circle_colors);
        this.width = width;
        this.height = height;
        ballPaint = new Paint();
        bgPaint = new Paint();
        bgPaint.setColor(Color.WHITE);
    }

    /**
     * 小球所处的圆的半径
     */
    protected int radius = 100;

    public abstract void drawState(Canvas canvas);


    protected void drawBall(Canvas canvas) {
        int length = colorArray.length;
        float perAngle = (360f / length);
        canvas.save();
        canvas.translate(width / 2, height / 2);
        canvas.rotate(canvasAngle);

        for (int color : colorArray) {
            ballPaint.setColor(color);
            //小球半径
            int ballRadius = 20;
            canvas.drawCircle(radius, 0, ballRadius, ballPaint);
            canvas.rotate(perAngle);
        }
        canvas.restore();
    }

    protected void drawBg(Canvas canvas) {
        if (bgRadius >= 0) {
            bgPaint.setStrokeWidth(height);
            bgPaint.setStyle(Paint.Style.STROKE);
            canvas.save();
            canvas.translate(width / 2, height / 2);
            canvas.drawCircle(0, 0, bgRadius, bgPaint);
            canvas.restore();
        } else {
            canvas.drawRect(0, 0, width, height, bgPaint);
        }
    }

    public void cancel() {
        if (animator != null) {
            animator.cancel();
        }
    }

}
