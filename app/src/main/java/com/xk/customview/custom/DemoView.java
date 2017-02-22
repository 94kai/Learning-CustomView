package com.xk.customview.custom;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.xk.customview.R;

import static android.R.attr.bitmap;

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
        float[] src = {0, 0,                                    // 左上
                bitmap.getWidth(), 0,                          // 右上
                bitmap.getWidth(), bitmap.getHeight(),        // 右下
                0, bitmap.getHeight()};                        // 左下

        float[] dst = {100, 100,                                    // 左上
                bitmap.getWidth(), 0,
                bitmap.getWidth(), bitmap.getHeight() *2,  // 右下
                0, bitmap.getHeight()*2};
        // 左下
        matrix.setPolyToPoly(src,0,dst,0,4);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(bitmap,matrix,null);


    }
}
