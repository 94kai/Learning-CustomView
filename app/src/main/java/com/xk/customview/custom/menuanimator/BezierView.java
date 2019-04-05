package com.xk.customview.custom.menuanimator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by xuekai on 2019/4/5.
 */
public class BezierView extends android.support.v7.widget.AppCompatImageView {

    private Paint paint;
    private Path path;
    private int x;
    private int y;

    public BezierView(Context context) {
        super(context);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10);
        path = new Path();
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        path.reset();
        path.moveTo(measuredWidth / 2, 0);
        path.quadTo(x, y, measuredWidth / 2, measuredHeight);
        path.close();
        canvas.drawPath(path, paint);
        canvas.drawLine(measuredWidth / 2, 0, measuredWidth / 2, measuredHeight, paint);
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
        invalidate();

    }

}
