package com.xk.customview.custom.zoomImageViewPager;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable
        .Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import static android.R.attr.factor;
import static android.graphics.Matrix.MSCALE_X;
import static android.graphics.Matrix.MSCALE_Y;

/**
 * Created by xuekai on 2017/2/22.
 */

public class ZoomImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener {
    private static final String TAG = "ZoomImageView";
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix;
    private int intrinsicHeight;
    private int intrinsicWidth;
    private float[] values = new float[9];

    //最小的缩放比例
    private float scale = 1;


    //正常显示的图片对应的矩形
    private RectF originalRect;
    //当前缩放比例下显示的图片对应的矩形
    private RectF currentRect;

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
        scaleGestureDetector = new ScaleGestureDetector(getContext(), this);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        matrix = new Matrix();
        originalRect=new RectF();
        currentRect=new RectF();


        Drawable drawable = getDrawable();
        if (drawable == null) return;
        intrinsicHeight = drawable.getIntrinsicHeight();
        intrinsicWidth = drawable.getIntrinsicWidth();


        //图片高度小于控件高度，宽度大于等于控件宽度，按照控件宽度缩放
        if (intrinsicHeight < h && intrinsicWidth >= w) {
            scale = w * 1f / intrinsicWidth;
        }

        //图片宽度小于控件宽度，高度大于等于控件高度，按照控件高度缩放
        if (intrinsicHeight >= h && intrinsicWidth < w) {
            scale = h * 1f / intrinsicHeight;
        }

        //图片宽度大于等于控件宽度，高度大于等于控件高度，按照控件高度缩放
        if ((intrinsicHeight >= h && intrinsicWidth >= w) || (intrinsicHeight < h && intrinsicWidth < w)) {
            scale = Math.min(h * 1f / intrinsicHeight, w * 1f / intrinsicWidth);
        }
        float dx = w / 2 - intrinsicWidth / 2;
        float dy = h / 2 - intrinsicHeight / 2;

        matrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
        matrix.postTranslate(dx, dy);


        setImageMatrix(matrix);

        originalRect.set(0, 0, intrinsicWidth, intrinsicHeight);
        matrix.mapRect(originalRect);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return scaleGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        scale(scaleFactor, detector.getFocusX(), detector.getFocusY());
        return true;
    }

    /**
     * 缩放一定的倍数
     *
     * @param scaleFactor 在当前的基础上缩放的倍数
     * @param focusX
     * @param scaleFactor
     */
    private void scale(float scaleFactor, float focusX, float focusY) {
        if (getScale() * scaleFactor <= scale) {
            matrix.postScale(scale / getScale(), scale / getScale(), focusX, focusY);
        } else {
            matrix.postScale(scaleFactor, scaleFactor, focusX, focusY);
        }
        checkBorder();
        setImageMatrix(matrix);
    }

    /**
     * 检查边框是否有白边
     */
    private void checkBorder() {
        getCurrentRectf();
        //首先获取初始状态下的 上下左右，如果现在的上下左右 大于、小于就要调整

        float dy=0;
        float dx=0;
        //上下调整
        if (originalRect.top < currentRect.top) {
            dy = originalRect.top - currentRect.top;
        } else if (originalRect.bottom > currentRect.bottom) {
            dy = originalRect.bottom - currentRect.bottom;
        }

        //左右调整
        if (originalRect.left < currentRect.left) {
            dx = originalRect.left - currentRect.left;
        } else if (originalRect.right > currentRect.right) {
            dx = originalRect.right - currentRect.right;
        }
        matrix.postTranslate(dx,dy);

    }

    /**
     * 获取当前的缩放比例
     *
     * @return
     */
    private float getScale() {
        matrix.getValues(values);
        return values[MSCALE_X];
    }

    /**
     * 获取当前图片的rect
     *
     * @return
     */
    private void getCurrentRectf() {
        currentRect.set(0, 0, intrinsicWidth, intrinsicHeight);
        matrix.mapRect(currentRect);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

}
