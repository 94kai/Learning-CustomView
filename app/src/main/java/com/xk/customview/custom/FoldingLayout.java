package com.xk.customview.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xk.customview.R;

import static android.R.attr.bitmap;
import static android.R.attr.src;

/**
 * 这个View坑死我了，deltaY = (float) (currentWidth / FOLD_COUNT / Math.tan(foldAngle))/3;
 * 现在我都不知道为啥要除以3，为啥不除以三就会不显示
 * <p>
 * 注意：像这种使用矩阵变形，并且结合clipRect，需注意以上
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

    //左半边的阴影画笔
    private Paint allShaderpaint;
    //右半边的阴影画笔
    private Paint halfShaderpaint;

    private int FOLD_COUNT = 6;
    private Matrix[] matrices = new Matrix[FOLD_COUNT];
    private Bitmap bitmap;


    public FoldingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        allShaderpaint = new Paint();
        allShaderpaint.setColor(0xff000000);

        halfShaderpaint = new Paint();
        halfShaderpaint.setStyle(Paint.Style.FILL);

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
            canvas.concat(matrices[i]);

            canvas.clipRect(bitmapWidth / FOLD_COUNT * i, 0, bitmapWidth / FOLD_COUNT * (i + 1), getHeight());
            canvas.drawBitmap(bitmap, 0, 0, null);


            canvas.translate(bitmapWidth / FOLD_COUNT * i, 0);


            double value = 0.9 - 1.8 / Math.PI * foldAngle;
            double value1 = bitmapWidth / FOLD_COUNT -2*bitmapWidth/FOLD_COUNT/Math.PI*foldAngle;

            allShaderpaint.setAlpha((int) (255 * 0.8f * value));
            halfShaderpaint.setAlpha((int) (255 * 0.8f * value));
            if (i % 2 == 0) {
                //绘制左半边阴影
                canvas.drawRect(0, 0, bitmapWidth / FOLD_COUNT, bitmapHeight, allShaderpaint);
            } else {
//                //绘制右半边阴影
                LinearGradient linearGradient = new LinearGradient(0, 0,0.7f ,0,
                        Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
                halfShaderpaint.setShader(linearGradient);
                Matrix matrix = new Matrix();
                Log.e(TAG,"onDraw11111"+i+"  "+value1);
                matrix.postScale((float) value1,1);
                linearGradient.setLocalMatrix(matrix);
                canvas.drawRect(0, 0, bitmapWidth / FOLD_COUNT, bitmapHeight, halfShaderpaint);
            }
            canvas.restore();
            allShaderpaint.setAlpha(00);

        }
        change();

    }

    private static final String TAG = "FoldingLayout";

    private void initMatrix() {
        currentWidth = (float) (bitmapWidth * Math.sin(foldAngle));
        currentHeight = bitmapHeight;

        //这里比较坑爹
        deltaY = (float) (currentWidth / FOLD_COUNT / Math.tan(foldAngle)) / 3;

        for (int i = 0; i < FOLD_COUNT; i++) {
            matrices[i].reset();
            src[0] = i * bitmapWidth / FOLD_COUNT;
            src[1] = 0;
            src[2] = src[0] + bitmapWidth / FOLD_COUNT;
            src[3] = 0;
            src[4] = src[0];
            src[5] = bitmapHeight;
            src[6] = src[2];
            src[7] = src[5];


            boolean isEven = i % 2 == 0;

            dst[0] = i * currentWidth / FOLD_COUNT;
            dst[1] = isEven ? 0 : deltaY;
            dst[2] = dst[0] + currentWidth / FOLD_COUNT;
            dst[3] = isEven ? deltaY : 0;
            dst[4] = dst[0];
            dst[5] = isEven ? currentHeight : currentHeight - deltaY;
            dst[6] = dst[2];
            dst[7] = isEven ? currentHeight - deltaY : currentHeight;

            for (int y = 0; y < 8; y++) {
                dst[y] = Math.round(dst[y]);
            }


            matrices[i].setPolyToPoly(src, 0, dst, 0, 4);

        }


    }


    public void change() {
        foldAngle = (float) (foldAngle - 0.01);
        if (foldAngle <= 0) foldAngle = (float) (Math.PI / 2);
        invalidate();
    }
}
