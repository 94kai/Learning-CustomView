package com.xk.customview.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.R.attr.max;
import static android.R.attr.radius;
import static android.R.attr.y;

/**
 * QQ消息气泡
 * Created by X.Sation on 2017/2/19.
 */

public class QQBubbleView extends View {

    //注：old指的是原地的

    private static final String TAG = "QQBubbleView";
    private PointF control;
    private PointF oldTop;
    private PointF oldBottom;
    private PointF newTop;
    private PointF newBottom;
    private int radius;
    private Paint paint;
    private boolean isBreak = false;
    private boolean isShow = true;

    /**
     * 当前手指的位置与中心连线的角度
     */
    private float direction = 0;
    private double roation;


    public QQBubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);
        control = new PointF(0, 0);
        radius = 30;
        oldTop = new PointF(0, -radius);
        oldBottom = new PointF(0, radius);
        newTop = new PointF(0, -radius);
        newBottom = new PointF(0, radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isShow) {


            canvas.save();
            canvas.translate(getWidth() / 2, getHeight() / 2);
            canvas.rotate((float) ((float) roation / Math.PI * 180));

            control.x = (newTop.x - oldTop.x) / 2;
            paint.setStyle(Paint.Style.FILL);
            Path path = new Path();
            path.moveTo(oldTop.x, oldTop.y);
            path.quadTo(control.x, control.y, newTop.x, newTop.y);
            path.lineTo(newBottom.x, newBottom.y);
            path.quadTo(control.x, control.y, oldBottom.x, oldBottom.y);
            path.close();

            path.setFillType(Path.FillType.WINDING);

            if (!isBreak) {
                canvas.drawPath(path, paint);
            }

            paint.setStyle(Paint.Style.FILL);
            if (!isBreak) {
                drawOldCircle(canvas);
            }
            drawNewCircle(canvas);
            canvas.restore();

        }
//        drawCoordinate(canvas);

    }

    private void drawNewCircle(Canvas canvas) {
        canvas.drawCircle(newTop.x, 0, radius, paint);
    }

    private void drawCoordinate(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
//        canvas.rotate((float) ((float) roation / Math.PI * 180));
        canvas.drawLine(0, -getHeight() / 2 + 5, 0, getHeight() / 2 - 5, paint);
        canvas.drawLine(-getWidth() / 2 + 5, 0, getWidth() / 2 - 5, 0, paint);
        canvas.restore();
    }

    private void drawOldCircle(Canvas canvas) {
        canvas.drawCircle(oldTop.x, 0, oldBottom.y, paint);
    }

    private int maxDistance = 500;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = (event.getX() - getWidth() / 2);
        float y = (event.getY() - getHeight() / 2);
        roation = Math.atan(y / x);

        x = (float) (x / Math.cos(roation));
        y = 0;

        newTop.x = x;
        newBottom.x = x;
        float distance = (float) Math.sqrt((x - oldTop.x) * (x - oldTop.x) + (y - oldTop.y) * (y - oldTop.y));

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (Math.abs(x) < radius && Math.abs(y) < radius) {
                    return true;
                } else {
                    return false;
                }
            case MotionEvent.ACTION_MOVE:


                if (distance < maxDistance) {
                    oldTop.y = -(2 * newTop.y / 3 / Math.abs(maxDistance) * Math.abs(distance) - newTop.y);
                    oldBottom.y = (2 * newTop.y / 3 / Math.abs(maxDistance) * Math.abs(distance) - newTop.y);
                } else {
                    isBreak = true;
                }

                break;
            case MotionEvent.ACTION_UP:
                if (isBreak) {
                    if (distance < maxDistance) {
                        newTop.set(0, -radius);
                        newBottom.set(0, radius);
                        isBreak=false;
                    } else {
                        isShow = false;
                    }
                } else {
                    newTop.set(0, -radius);
                    newBottom.set(0, radius);
                }

                break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }
}
