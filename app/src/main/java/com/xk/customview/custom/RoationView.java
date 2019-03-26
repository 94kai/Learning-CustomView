package com.xk.customview.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.xk.customview.R;

/**
 * Created by xuekai on 2017/2/20.
 */

public class RoationView extends View {

    private Paint paint;
    private float[] pos = new float[2];
    private float[] tan = new float[2];

    private int progress =1;

    public RoationView(Context context, AttributeSet attrs) {
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
        canvas.translate(getWidth() / 2, getHeight() / 2);
        Path path = new Path();
        path.addCircle(0, 0, 200, Path.Direction.CW);
        canvas.drawCircle(0, 0, 200, new Paint());

        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.mipmap.jiantou);
        Bitmap bitmap = drawable.getBitmap();

        PathMeasure pathMeasure = new PathMeasure(path, false);
        progress += 3;
        //1200 大约是周长，随便一估，主要是逻辑
        if (progress > 1200) {
            progress = 1;
        }

//        pathMeasure.getPosTan(progress, pos, tan);
//        Matrix matrix = new Matrix();
//        matrix.postRotate((float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI), bitmap.getWidth() / 2, bitmap.getHeight() / 2);
//        matrix.postTranslate(pos[0]-bitmap.getWidth()/2, pos[1]-bitmap.getHeight()/2);


        Matrix matrix = new Matrix();
        boolean matrix1 = pathMeasure.getMatrix(progress, matrix, PathMeasure.POSITION_MATRIX_FLAG|PathMeasure.TANGENT_MATRIX_FLAG);;
        matrix.preTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2);

        canvas.drawBitmap(bitmap, matrix, new Paint());


        postInvalidate();

    }
}
