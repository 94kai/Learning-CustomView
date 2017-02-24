package com.xk.customview.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xk.customview.R;

import static android.R.attr.bitmap;
import static android.R.attr.src;

/**
 * Created by xuekai on 2017/2/23.
 */

public class FoldingLayout extends View {
    //real表示完全展开的宽度 current表示折叠后的宽度
    private float foldAngle = (float) (Math.PI / 2);
    private float realWidth;
    private float realHeight;
    private float currentWidth;
    private float currentHeight;

    private float bitmapWidth;
    private float bitmapHeight;

    private float[] src = new float[8];
    private float[] dst = new float[8];

    //折叠后y的偏移量
    private float deltaY;

    private int FOLD_COUNT = 6;
    private Matrix[] matrices = new Matrix[FOLD_COUNT];
    private Bitmap bitmap;
    private Paint paint;


    public FoldingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        realHeight = h;
        realWidth = w;

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.dada);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();


        for (int i = 0; i < FOLD_COUNT; i++) {
            matrices[i] = new Matrix();

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initMatrix();

        for (int i = 0; i < FOLD_COUNT; i++) {


            canvas.save();
            canvas.clipRect(currentWidth / FOLD_COUNT * i, 0, currentWidth / FOLD_COUNT * (i + 1), getHeight());
            canvas.concat(matrices[i]);
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.restore();

        }
        change();

    }

    private static final String TAG = "FoldingLayout";

    private void initMatrix() {
        currentWidth = (float) (bitmapWidth * Math.sin(foldAngle));
        currentHeight = bitmapHeight;

        deltaY = (float) (currentWidth / FOLD_COUNT / Math.tan(foldAngle));

        for (int i = 0; i < FOLD_COUNT; i++) {
            matrices[i].reset();
            src[0] = i * bitmapWidth / FOLD_COUNT;
            src[1] = 0;
            src[2] = src[0] + bitmapWidth / FOLD_COUNT;
            src[3] = 0;
            src[4] = src[0];
            src[5] = bitmapHeight;
            src[6] = src[2];
            src[7] = bitmapHeight;

            dst[0] = i * currentWidth / FOLD_COUNT;
            dst[1] = i % 2 == 0 ? 0 : deltaY;
            dst[2] = dst[0] + currentWidth / FOLD_COUNT;
            dst[3] = i % 2 == 0 ? deltaY : 0;
            dst[4] = dst[0];
            dst[5] = i % 2 == 0 ? currentHeight : currentHeight - deltaY;
            dst[6] = dst[2];
            dst[7] = i % 2 == 0 ? currentHeight - deltaY : currentHeight;

            matrices[i].setPolyToPoly(src, 0, dst, 0, 4);

        }
    }


    public void change() {
        foldAngle = (float) (foldAngle - 0.01);
        if (foldAngle <= 0) foldAngle = (float) (Math.PI / 2);
        invalidate();
    }
}
