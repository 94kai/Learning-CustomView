package com.xk.customview.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.xk.customview.R;

import static android.R.attr.bitmap;
import static android.R.attr.direction;
import static android.R.attr.path;


public class PathDemoView extends View {
    private static final String TAG = "PathDemoView";
    private Paint mPaint;
    private int scale = 3;
    //辅助计算文字位置
    private float v;

    public PathDemoView(Context context) {
        this(context, null);
    }

    public PathDemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        v = (fontMetrics.bottom + fontMetrics.top) / 2;

        mPaint.setTextSize(mPaint.getTextSize() * scale);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);  // 移动坐标系到屏幕中心

        Path path = new Path();

        path.moveTo(10 * scale, 0 * scale);
        path.lineTo(5 * scale, (float) (5 * scale * Math.sqrt(3)));
        path.lineTo(-5 * scale, (float) (5 * scale * Math.sqrt(3)));
        path.lineTo(-10 * scale, 0 * scale);
        path.lineTo(-5 * scale, (float) (-5 * scale * Math.sqrt(3)));
        path.lineTo(5 * scale, (float) (-5 * scale * Math.sqrt(3)));
        path.close();

        path.moveTo(20 * scale, 0 * scale);
        path.lineTo(10 * scale, (float) (10 * scale * Math.sqrt(3)));
        path.lineTo(-10 * scale, (float) (10 * scale * Math.sqrt(3)));
        path.lineTo(-20 * scale, 0 * scale);
        path.lineTo(-10 * scale, (float) (-10 * scale * Math.sqrt(3)));
        path.lineTo(10 * scale, (float) (-10 * scale * Math.sqrt(3)));
        path.close();

        path.moveTo(30 * scale, 0 * scale);
        path.lineTo(15 * scale, (float) (15 * scale * Math.sqrt(3)));
        path.lineTo(-15 * scale, (float) (15 * scale * Math.sqrt(3)));
        path.lineTo(-30 * scale, 0 * scale);
        path.lineTo(-15 * scale, (float) (-15 * scale * Math.sqrt(3)));
        path.lineTo(15 * scale, (float) (-15 * scale * Math.sqrt(3)));
        path.close();

        path.moveTo(40 * scale, 0 * scale);
        path.lineTo(20 * scale, (float) (20 * scale * Math.sqrt(3)));
        path.lineTo(-20 * scale, (float) (20 * scale * Math.sqrt(3)));
        path.lineTo(-40 * scale, 0 * scale);
        path.lineTo(-20 * scale, (float) (-20 * scale * Math.sqrt(3)));
        path.lineTo(20 * scale, (float) (-20 * scale * Math.sqrt(3)));
        path.close();

        drawValue(canvas);

        canvas.drawPath(path, mPaint);


        canvas.drawText("A", 25 * scale, ((float) (-25 * Math.sqrt(3)) - v) * scale, mPaint);
        canvas.drawText("B", 50 * scale, (0 - v) * scale, mPaint);
        canvas.drawText("C", 25 * scale, ((float) (25 * Math.sqrt(3)) - v) * scale, mPaint);
        canvas.drawText("D", -25 * scale, ((float) (25 * Math.sqrt(3)) - v) * scale, mPaint);
        canvas.drawText("E", -50 * scale, (0 - v) * scale, mPaint);
        canvas.drawText("F", -25 * scale, ((float) (-25 * Math.sqrt(3)) - v) * scale, mPaint);
        canvas.restore();

        drawCoordinate(canvas);

    }

    private int aValue = 60;
    private int bValue = 50;
    private int cValue = 40;
    private int dValue = 58;
    private int eValue = 80;
    private int fValue = 30;


    private void drawValue(Canvas canvas) {
        Paint paint = new Paint(mPaint);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);

        Path path = new Path();
        //假设范围是0-100
        float ax = 20 * scale / 100f * aValue;
        float bx = 40 * scale / 100f * bValue;
        float cx = 20 * scale / 100f * cValue;
        float dx = -20 * scale / 100f * dValue;
        float ex = -40 * scale / 100f * eValue;
        float fx = -20 * scale / 100f * fValue;


        float ay = ((float) (-20 * Math.sqrt(3)) - v) * scale / 100f * aValue;
        float by = (0 - v) * scale / 100f * bValue;
        float cy = ((float) (20 * Math.sqrt(3)) - v) * scale / 100f * cValue;
        float dy = ((float) (20 * Math.sqrt(3)) - v) * scale / 100f * dValue;
        float ey = (0 - v) * scale / 100f * eValue;
        float fy = ((float) (-20 * Math.sqrt(3)) - v) * scale / 100f * fValue;

        path.moveTo(ax, ay);
        path.lineTo(bx, by);
        path.lineTo(cx, cy);
        path.lineTo(dx, dy);
        path.lineTo(ex, ey);
        path.lineTo(fx, fy);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawCoordinate(Canvas canvas) {
        canvas.save();
        mPaint.setColor(0xff000000);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawLine(-getWidth() / 2 + 5, 0, getWidth() / 2 - 5, 0, mPaint);
        canvas.drawLine(0, -getHeight() / 2 + 5, 0, getHeight() / 2 - 5, mPaint);
        mPaint.setColor(Color.RED);           // 绘制合并后的路径
        canvas.restore();
    }


    public int getaValue() {
        return aValue;
    }

    public void setaValue(int aValue) {
        this.aValue = aValue;
        postInvalidate();
    }


    public int getbValue() {
        return bValue;
    }

    public void setbValue(int bValue) {
        this.bValue = bValue;
        postInvalidate();

    }


    public int getcValue() {
        return cValue;
    }

    public void setcValue(int cValue) {
        this.cValue = cValue;
        postInvalidate();

    }


    public int getdValue() {
        return dValue;
    }

    public void setdValue(int dValue) {
        this.dValue = dValue;
        postInvalidate();

    }


    public int geteValue() {
        return eValue;
    }

    public void seteValue(int eValue) {
        this.eValue = eValue;
        postInvalidate();

    }

    public int getfValue() {
        return fValue;
    }

    public void setfValue(int fValue) {
        this.fValue = fValue;
        postInvalidate();

    }
}