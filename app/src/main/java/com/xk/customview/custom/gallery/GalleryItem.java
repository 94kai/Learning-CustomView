package com.xk.customview.custom.gallery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by xuekai on 2017/2/27.
 */

public class GalleryItem extends ImageView {

    private Drawable drawable;
    private Matrix matrix;
    private float[] src;
    private float[] dstLeft;
    private float[] dstRight;
    //上下缩回去的值与总高度的比例
    private float scaleRatio = 0.2f;
    private int intrinsicWidth;
    private int intrinsicHeight;

    public GalleryItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        src = new float[8];
        dstLeft = new float[8];
        dstRight = new float[8];
        drawable = getDrawable();
        intrinsicWidth = drawable.getIntrinsicWidth();
        intrinsicHeight = drawable.getIntrinsicHeight();
        src[0] = 0;
        src[1] = 0;
        src[2] = intrinsicWidth;
        src[3] = 0;
        src[4] = 0;
        src[5] = intrinsicHeight;
        src[6] = intrinsicWidth;
        src[7] = intrinsicHeight;

        matrix = new Matrix();

        dstLeft[0] = src[0];
        dstLeft[1] = src[1];
        dstLeft[2] = src[2];
        dstLeft[4] = src[4];
        dstLeft[5] = src[5];
        dstLeft[6] = src[6];


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 变形
     *
     * @param progress 根据这个参数，来控制形变
     */
    public void change(float progress) {
        matrix.reset();

        dstLeft[3] = src[3] + intrinsicHeight * scaleRatio * progress;
        dstLeft[7] = src[7] - intrinsicHeight * scaleRatio * progress;

        matrix.setPolyToPoly(src, 0, dstLeft, 0, src.length >> 2);
        setImageMatrix(matrix);
    }
}
