package com.xk.customview.custom.zoomImageViewPager;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable
        .Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.Toast;

import static android.R.attr.factor;
import static android.R.attr.handle;
import static android.graphics.Matrix.MSCALE_X;
import static android.graphics.Matrix.MSCALE_Y;

/**
 * Created by xuekai on 2017/2/22.
 */

public class ZoomImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener {
    private static final String TAG = "ZoomImageView";
    private static final long DURATION_SCALE = 1000;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix;
    private int intrinsicHeight;
    private int intrinsicWidth;
    private float[] values = new float[9];
    //缓慢缩放的进度
    private float scaleProgress;

    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (getScale() == scale) {
                //放大1.5倍
//                scale(1.5f, e.getX(), e.getY());
                smoothScale(1.5f, e.getX(), e.getY());

            } else {
                //缩回去
                //缓慢缩回去有坑，暂时不管了
                scale((scale / getScale()), e.getX(), e.getY());
            }
            return super.onDoubleTap(e);
        }
    };

    //最小的缩放比例
    private float scale = 1;


    //正常显示的图片对应的矩形
    private RectF originalRect;
    //当前缩放比例下显示的图片对应的矩形
    private RectF currentRect;
    private GestureDetector gestureDetector;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        setScaleType(ScaleType.MATRIX);
        scaleGestureDetector = new ScaleGestureDetector(getContext(), this);

        gestureDetector = new GestureDetector(getContext(), simpleOnGestureListener);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        matrix = new Matrix();
        originalRect = new RectF();
        currentRect = new RectF();


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
        if (intrinsicHeight >= h && intrinsicWidth >= w) {
            scale = Math.min(h * 1f / intrinsicHeight, w * 1f / intrinsicWidth);
        }
        if (intrinsicHeight <= h && intrinsicWidth <= w) {
            scale = Math.min(h * 1f / intrinsicHeight, w * 1f / intrinsicWidth);

        }
        float dx = w / 2 - intrinsicWidth / 2;
        float dy = h / 2 - intrinsicHeight / 2;

        matrix.postTranslate(dx, dy);
        matrix.postScale(scale, scale, w / 2, h / 2);


        setImageMatrix(matrix);

        originalRect.set(0, 0, intrinsicWidth, intrinsicHeight);
        matrix.mapRect(originalRect);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || handleSlideStep1(event) || scaleGestureDetector.onTouchEvent(event);
    }

    /**
     * 处理滑动1(判断是否需要滑动)
     *
     * @param event
     */
    private boolean handleSlideStep1(MotionEvent event) {
        if (event.getPointerCount() > 1) {
            return false;
        }
        if (getScale() > scale) {
            getParent().requestDisallowInterceptTouchEvent(true);
            handleSlideStep2(event);
            return true;
        }
        return false;
    }

    private float x, y;



    /**
     * 处理滑动2(让图片移动)
     *
     * @param event
     */
    private void handleSlideStep2(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - x;
                float dy = event.getY() - y;

                slide(dx, dy);

                x = event.getX();
                y = event.getY();
                break;
        }
    }

    /**
     * 让图片滑动
     *
     * @param dx
     * @param dy
     */
    private void slide(float dx, float dy) {
        getCurrentRectf();
        if (dx > 0) {//向右移动
            if (currentRect.left + dx >= 0) {
                dx = 0 - currentRect.left;
            }
        }
        if (dx < 0) {//向左移动
            if (currentRect.right + dx <= getWidth()) {
                dx = getWidth() - currentRect.right;
            }
        }

        if (dy > 0) {//向下移动
            if (currentRect.top + dy >= 0) {
                dy = 0 - currentRect.top;
            }
        }
        if (dy < 0) {//向上移动
            if (currentRect.bottom + dy <= getHeight()) {
                dy = getHeight() - currentRect.bottom;
            }
        }
        if (currentRect.width() <= getWidth()) {
            dx = 0;
        }
        if (currentRect.height() <= getHeight()) {
            dy = 0;
        }

        matrix.postTranslate(dx, dy);
        setImageMatrix(matrix);
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
     * @param scaleFactor 在当前的基础上缩放的倍数,scaleFactor小于0，则恢复
     * @param focusX
     * @param scaleFactor
     */
    private void scale(float scaleFactor, float focusX, float focusY) {
        if (getScale() * scaleFactor < scale) {
            matrix.postScale(scale / getScale(), scale / getScale(), focusX, focusY);
        } else {
            matrix.postScale(scaleFactor, scaleFactor, focusX, focusY);
        }
        checkBorder();
        setImageMatrix(matrix);
    }

    /**
     * 流畅的缩放一定的倍数
     *
     * @param scaleFactor 在当前的基础上缩放的倍数,scaleFactor小于0，则恢复
     * @param focusX
     * @param scaleFactor
     */
    private void smoothScale(final float scaleFactor, final float focusX, final float focusY) {
        scaleAnimal.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scaleProgress = (float) animation.getAnimatedValue();
                float scaleValue = (scale * 1f / getScale() * (1 + scaleProgress * (scaleFactor - 1)));
                scale(scaleValue, focusX, focusY);


                //根据进度 计算每一时刻的缩放比例

                Log.e("ZoomImageView", "onAnimationUpdate" + scaleValue);
            }
        });

        scaleAnimal.setDuration(DURATION_SCALE);
        scaleAnimal.start();

    }

    private ValueAnimator scaleAnimal = ValueAnimator.ofFloat(0, 1);

    /**
     * 检查边框是否有白边
     */
    private void checkBorder() {
        getCurrentRectf();
        //首先获取初始状态下的 上下左右，如果现在的上下左右 大于、小于就要调整

        float dy = 0;
        float dx = 0;
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
        matrix.postTranslate(dx, dy);

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
