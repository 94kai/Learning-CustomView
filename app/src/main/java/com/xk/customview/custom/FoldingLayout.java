package com.xk.customview.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xk.customview.R;

/**
 * Created by xuekai on 2017/2/23.
 */

public class FoldingLayout extends View {
    //current代表当前  real代表展开状态
    private BitmapFactory.Options options;
    private Bitmap bitmap;
    //总共分成6块
    private int PIECE_COUNT = 6;
    //矩阵数组，每一块一个，总共6个
    private Matrix[] matrices = new Matrix[PIECE_COUNT];

    private float foldAngle= (float) (Math.PI/2f);

    private float realWidth;
    private float realHeight;
    private float realPerWidth;
    private float realPerHeight;
    private float currentWidth;

    private float[] src = new float[8];
    private float[] dst = new float[8];

    public FoldingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.dada, options);
        options.inSampleSize = calculateInSampleSize(options, w, h);
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.dada, options);

        //在这里设置真实图片的宽高
        realWidth = w;
        realHeight = h;

        realPerWidth = realWidth / PIECE_COUNT;
        realPerHeight = realHeight;

        for (int i = 0; i < PIECE_COUNT; i++) {
            matrices[i] = new Matrix();
        }

//        matrix = new Matrix();
//        float[] src = {0, 0,
//                bitmap.getWidth(), 0,
//                0, bitmap.getHeight(),
//                bitmap.getWidth(), bitmap.getHeight()
//        };
//        float[] dst = {0, 0,
//                w, 0 + 100,
//                0, h,
//                w, h - 100
//        };
//        matrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
//
//
//        LinearGradient linearGradient = new LinearGradient(0, 0, getWidth() / 2, 0, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
//        shaderMatrix = new Matrix();
////        shaderMatrix.setScale(1,1);
//
//        float[] src1 = {0, 0,
//                getWidth(), 0,
//                0, getHeight(),
//                getWidth(), getHeight()
//        };
//        float[] dst1 = {0, 0,
//                getWidth(), 0 + 100,
//                0, getHeight(),
//                getWidth(), getHeight() - 100
//        };
//        shaderMatrix.setPolyToPoly(src1, 0, dst1, 0, src.length >> 1);
//
////        linearGradient.setLocalMatrix(shaderMatrix);
//        mPaint = new Paint();
//        mPaint.setShader(linearGradient);
//        mPaint.setAlpha(200);
    }

    private void initMatrix() {

        currentWidth = (float) (Math.sin(foldAngle) * realPerWidth);

        float deltaY = (float) (currentWidth / Math.tan(foldAngle));
        for (int i = 0; i < PIECE_COUNT; i++) {

            src[0] = realWidth / PIECE_COUNT * i;
            src[1] = 0;
            src[2] = realWidth / PIECE_COUNT * (i + 1);
            src[3] = 0;
            src[4] = realWidth / PIECE_COUNT * i;
            src[5] = realHeight;
            src[6] = realWidth / PIECE_COUNT * (i + 1);
            src[7] = realHeight;

            dst[0] = currentWidth / PIECE_COUNT * i;
            dst[1] = i % 2 == 0 ? 0 : deltaY;
            dst[2] = currentWidth / PIECE_COUNT * (i + 1);
            dst[3] = i % 2 == 0 ? deltaY : 0;
            dst[4] = currentWidth / PIECE_COUNT * i;
            dst[5] = i % 2 == 0 ? realHeight : realHeight - deltaY;
            dst[6] = currentWidth / PIECE_COUNT * (i + 1);
            dst[7] = i % 2 == 0 ? realHeight - deltaY : realHeight;


            matrices[i].setPolyToPoly(src, 0, dst, 0, src.length >> 1);
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        initMatrix();

        for (int i = 0; i < PIECE_COUNT; i++) {
            canvas.save();
            canvas.concat(matrices[i]);
            canvas.clipRect(realWidth / PIECE_COUNT * i, 0, realWidth / PIECE_COUNT * (i + 1), realHeight);
            canvas.drawBitmap(bitmap, 0, 0, new Paint());
            canvas.restore();
        }


//        canvas.drawBitmap(bitmap, matrix, new Paint());
//        canvas.concat(shaderMatrix);
//        canvas.drawRect(0, 0,
//                getWidth(), getHeight(), mPaint);

    }
}

//    private Paint mShadowPaint;
//    private Matrix mShadowGradientMatrix;
//    private LinearGradient mShadowGradientShader;
//
//    public PolyToPolyView(Context context)
//    {
//        super(context);
//        mBitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.tanyan);
//        mMatrix = new Matrix();
//
//        mShadowPaint = new Paint();
//        mShadowPaint.setStyle(Style.FILL);
//        mShadowGradientShader = new LinearGradient(0, 0, 0.5f, 0,
//                Color.BLACK, Color.TRANSPARENT, TileMode.CLAMP);
//        mShadowPaint.setShader(mShadowGradientShader);
//
//        mShadowGradientMatrix = new Matrix();
//        mShadowGradientMatrix.setScale(mBitmap.getWidth(), 1);
//        mShadowGradientShader.setLocalMatrix(mShadowGradientMatrix);
//        mShadowPaint.setAlpha((int) (0.9*255));
//
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas)
//    {
//        super.onDraw(canvas);
//        canvas.save();
//        float[] src = //...;
//        float[] dst = //...;
//                mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
//
//        canvas.concat(mMatrix);
//        canvas.drawBitmap(mBitmap, 0, 0, null);
//        //绘制阴影
//  canvas.drawRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(),
//        mShadowPaint);
//        canvas.restore();
//
//    }