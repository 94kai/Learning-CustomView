package com.xk.customview.custom;

import android.animation.ValueAnimator;
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

/**
 * 从圆到心
 * 来自https://github.com/GcsSloop/AndroidNote/blob/master/CustomView/Advance/%5B06%5DPath_Bezier.md
 * Created by xuekai on 2017/2/17.
 */

public class CircleAndHeartView extends View {

    private PointF dataA;
    private PointF dataB;
    private PointF dataC;
    private PointF dataD;
    private PointF controlA;
    private PointF controlB;
    private PointF controlC;
    private PointF controlD;
    private PointF controlE;
    private PointF controlF;
    private PointF controlG;
    private PointF controlH;
    private int width;
    private int height;
    private Paint paint;
    private int raduis=200;
    //datac的偏移量(变心的时候)
    private float dcOffset;
    private float daOffset;

    private final float C = 0.551915024494f;     // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置

    private float mDifference = raduis * C;        // 圆形的控制点与数据点的差值


    public CircleAndHeartView(Context context) {
        this(context, null);
    }

    public CircleAndHeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        dataA = new PointF();
        dataB = new PointF();
        dataC = new PointF();
        dataD = new PointF();
        controlA = new PointF();
        controlB = new PointF();
        controlC = new PointF();
        controlD = new PointF();
        controlE = new PointF();
        controlF = new PointF();
        controlG = new PointF();
        controlH = new PointF();
        paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        dataB.set(raduis, 0);
        dataD.set(-raduis, 0);


        controlA.set(0 + mDifference, raduis);
        controlB.set(raduis, 0 + mDifference);
        controlC.set(raduis, 0 - mDifference);
        controlD.set(0 + mDifference, -raduis);


        controlE.set(0 - mDifference, -raduis);
        controlF.set(-raduis, 0 - mDifference);
        controlG.set(-raduis, 0 + mDifference);
        controlH.set(0 - mDifference, raduis);


        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        dataC.set(0, -raduis+dcOffset);
        dataA.set(0, raduis+daOffset);


        drawCoordinate(canvas);
        canvas.save();
        canvas.translate(width / 2, height / 2);

        Path path = new Path();
        path.moveTo(dataA.x, dataA.y);


        path.cubicTo(controlA.x, controlA.y, controlB.x, controlB.y, dataB.x, dataB.y);
        path.cubicTo(controlC.x, controlC.y, controlD.x, controlD.y, dataC.x, dataC.y);
        path.cubicTo(controlE.x, controlE.y, controlF.x, controlF.y, dataD.x, dataD.y);
        path.cubicTo(controlG.x, controlG.y, controlH.x, controlH.y, dataA.x, dataA.y);

        canvas.drawPath(path, paint);
        paint.setColor(Color.BLUE);


        paint.setTextSize(30);
        canvas.drawText("a",dataA.x,dataA.y,paint);
        canvas.drawText("b",dataB.x,dataB.y,paint);
        canvas.drawText("c",dataC.x,dataC.y,paint);
        canvas.drawText("d",dataD.x,dataD.y,paint);
        canvas.drawText("ka",controlA.x,controlA.y,paint);
        canvas.drawText("kb",controlB.x,controlB.y,paint);
        canvas.drawText("kc",controlC.x,controlC.y,paint);
        canvas.drawText("kd",controlD.x,controlD.y,paint);
        canvas.drawText("ke", controlE.x, controlE.y, paint);
        canvas.drawText("kf", controlF.x, controlF.y, paint);
        canvas.drawText("kg", controlG.x, controlG.y, paint);
        canvas.drawText("kh", controlH.x, controlH.y, paint);

        canvas.restore();
    }

    private void drawCoordinate(Canvas canvas) {
        canvas.save();
        paint.setColor(0xff000000);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawLine(-getWidth() / 2 + 5, 0, getWidth() / 2 - 5, 0, paint);
        canvas.drawLine(0, -getHeight() / 2 + 5, 0, getHeight() / 2 - 5, paint);
        paint.setColor(Color.RED);           // 绘制合并后的路径
        canvas.restore();
    }

   public void startAnimation(){
       ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
       valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
           @Override
           public void onAnimationUpdate(ValueAnimator animation) {
               float value = (float) animation.getAnimatedValue();
               //这里自己调整，通过调整各个data点和control点，可以使心更好看，只是为了学习，所以我就随便动了两个点
               dcOffset=value*raduis/2;
               daOffset=value*raduis/2;
               invalidate();
            }
       });
       valueAnimator.setDuration(2000);
       valueAnimator.start();
   }


}
