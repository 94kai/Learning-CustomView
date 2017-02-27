package com.xk.customview.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.xk.customview.R;

import static android.R.attr.centerX;
import static android.R.attr.centerY;

/**
 * Created by xuekai on 2017/2/20.
 */

public class DemoView extends View {

    private Paint paint;
    private int radius=200;
    private Matrix matrix;
    private Bitmap bitmap;

    public DemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xff000000);
        paint.setStrokeWidth(30);
        paint.setStyle(Paint.Style.FILL);


        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.mipmap.abc);
        bitmap = drawable.getBitmap();
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth()/2-bitmap.getWidth()/2,getHeight()/2);
        Camera camera = new Camera();
        camera.rotate(10,0,0);
        camera.getMatrix(matrix);
        // 调节中心点
        matrix.preTranslate(-getWidth()/2, -getHeight()/2);
        matrix.postTranslate(getWidth()/2, getHeight()/2);
    canvas.drawBitmap(bitmap,matrix,null);


    }
}
