package com.xk.customview.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * QQ消息起泡
 * Created by X.Sation on 2017/2/19.
 */

public class QQRemindView extends View {
    //注：old指的是原地的

    private PointF control;
    private PointF oldTop;
    private PointF oldBottom;
    private PointF newTop;
    private PointF newBottom;
    private int oldRadius;
    private int newRadius;
    private Paint paint;


    public QQRemindView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        control = new PointF(0, 0);
        oldRadius = newRadius = 50;
        oldTop = new PointF(0, -newRadius);
        oldBottom = new PointF(0, newRadius);
        newTop = new PointF(0, -newRadius);
        newBottom = new PointF(0, newRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        control.x = (newTop.x - oldTop.x) / 2;
        canvas.translate(getWidth()/2,getHeight()/2);

        Path path = new Path();
        path.moveTo(oldTop.x,oldTop.y);
        path.quadTo(control.x,control.y,newTop.x,newTop.y);
        path.moveTo(oldBottom.x, oldBottom.y);
        path.quadTo(control.x, control.y, newBottom.x, newBottom.y);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
    }

    private int maxDistance=500;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX()-getWidth()/2;
        newTop.x = x;
        newBottom.x = x;

        float distance = x - oldTop.x;
       oldTop.y=-(2*newTop.y/3/Math.abs(maxDistance)*Math.abs(distance)-newTop.y);
       oldBottom.y=(2*newTop.y/3/Math.abs(maxDistance)*Math.abs(distance)-newTop.y);
        invalidate();
        return true;
    }
}
