package com.xk.customview.custom.gallery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;


/**
 * Created by xuekai on 2017/2/27.
 */

public class GalleryItem extends ImageView {
    private static final String TAG = "GalleryItem";

    public static final int STATE_LEFT = 1;
    public static final int STATE_RIGHT = 2;
    public static final int STATE_MIDDLE = 3;
    private int width;
    private int height;

    //原始矩形
    private RectF originalRect;

    private
    @MatrixState
    int currentState;

    @IntDef({STATE_LEFT, STATE_RIGHT, STATE_MIDDLE})
    public @interface MatrixState {
    }

    private Drawable drawable;
    private Matrix matrix;
    private float[] src;
    private float[] dst;
    //上下缩回去的值与总高度的比例
    private float scaleRatio = 0.2f;
    private int intrinsicWidth;
    private int intrinsicHeight;

    private boolean sizeChange = false;

    private @MatrixState int originalState;

    public void setOriginalState(int originalState) {
        this.originalState = originalState;
    }

    public GalleryItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
        matrix = new Matrix();
        src = new float[8];
        dst = new float[8];
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        sizeChange = true;
        width = w;
        height = h;
        originalRect = new RectF();
        drawable = getDrawable();
        maunalFitXY();

        setMatrix(originalState);
    }

    private void maunalFitXY() {
            matrix.reset();
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

            dst[0] = 0;
            dst[1] = 0;
            dst[2] = width - getPaddingLeft() - getPaddingRight();
            dst[3] = 0;
            dst[4] = 0;
            dst[5] = height - getPaddingTop() - getPaddingBottom();
            dst[6] = width - getPaddingLeft() - getPaddingRight();
            dst[7] = height - getPaddingTop() - getPaddingBottom();

            matrix.setPolyToPoly(src, 0, dst, 0, 4);
            setImageMatrix(matrix);

            originalRect.set(0, 0, intrinsicWidth, intrinsicHeight);
            matrix.mapRect(originalRect);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (sizeChange) {
            maunalFitXY();
        }

    }

    private void initDst() {
        dst[0] = 0;
        dst[1] = 0;
        dst[2] = width - getPaddingLeft() - getPaddingRight();
        dst[3] = 0;
        dst[4] = 0;
        dst[5] = height - getPaddingTop() - getPaddingBottom();
        dst[6] = width - getPaddingLeft() - getPaddingRight();
        dst[7] = height - getPaddingTop() - getPaddingBottom();
    }


    /**
     * 设置展示样式
     */
    public void setMatrix(@MatrixState int state) {
        initDst();
        matrix.reset();

        switch (state) {
            case STATE_LEFT:
                dst[3] = dst[3] + intrinsicHeight * scaleRatio;
                dst[7] = dst[7] - intrinsicHeight * scaleRatio;
                break;
            case STATE_RIGHT:
                dst[1] = dst[1] + intrinsicHeight * scaleRatio;
                dst[5] = dst[5] - intrinsicHeight * scaleRatio;
                break;
            case STATE_MIDDLE:
                break;
        }
        matrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
        setImageMatrix(matrix);
    }


    /**
     * 变形
     *
     * @param progress  根据这个参数，来控制形变（正数表示向右，负数表示向左）
     * @param fromState
     * @param toState
     */
    public void change(float progress, @MatrixState int fromState, @MatrixState int toState) {
        initDst();
        matrix.reset();

        switch (fromState) {
            case STATE_LEFT:
                if (toState == STATE_MIDDLE) {
                    dst[3] = dst[3] + intrinsicHeight * scaleRatio * (1 - progress);
                    dst[7] = dst[7] - intrinsicHeight * scaleRatio * (1 - progress);
                    currentState = STATE_MIDDLE;
                } else {
                    throw new IllegalStateException("只能从STATE_LEFT转换成STATE_MIDDLE");
                }
                break;
            case STATE_RIGHT:
                if (toState == STATE_MIDDLE) {
                    dst[1] = dst[1] + intrinsicHeight * scaleRatio * (1 - progress);
                    dst[5] = dst[5] - intrinsicHeight * scaleRatio * (1 - progress);
                    currentState = STATE_MIDDLE;

                } else {
                    throw new IllegalStateException("只能从STATE_RIGHT转换成STATE_MIDDLE");
                }
                break;
            case STATE_MIDDLE:
                if (toState == STATE_RIGHT) {
                    dst[1] = dst[1] + intrinsicHeight * scaleRatio * progress;
                    dst[5] = dst[5] - intrinsicHeight * scaleRatio * progress;
                    currentState = STATE_RIGHT;

                } else if (toState == STATE_LEFT) {
                    dst[3] = dst[3] + intrinsicHeight * scaleRatio * progress;
                    dst[7] = dst[7] - intrinsicHeight * scaleRatio * progress;
                    currentState = STATE_LEFT;
                } else {
                    throw new IllegalStateException("不能从STATE_MIDDLE转换成STATE_LEFT");

                }
                break;
        }
        matrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
        setImageMatrix(matrix);
    }
}
