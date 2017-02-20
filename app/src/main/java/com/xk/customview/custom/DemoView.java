package com.xk.customview.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.xk.customview.R;

/**
 * Created by xuekai on 2017/2/20.
 */

public class DemoView extends View {

    private Paint paint;
    private int radius=200;
    public DemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xff000000);
        paint.setStrokeWidth(30);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth()/2,getHeight()/2);
        Path path1 = new Path();
        Path path2 = new Path();
        Path path3 = new Path();
        Path path4 = new Path();


        path1.addCircle(0,0,radius, Path.Direction.CW);
        path2.addRect(0, -radius, radius, radius, Path.Direction.CW);
        path3.addCircle(0,-radius/2,radius/2, Path.Direction.CW);
        path4.addCircle(0,radius/2,radius/2, Path.Direction.CW);



        path1.op(path2, Path.Op.DIFFERENCE);
        path1.op(path4, Path.Op.DIFFERENCE);
        path1.op(path3, Path.Op.UNION);


        canvas.drawPath(path1, new Paint());

    }
}
