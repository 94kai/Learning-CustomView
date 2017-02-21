package com.xk.customview.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import static android.R.attr.path;
import static android.R.attr.start;

/**
 * 搜索View  点击屏幕左边启动动画  点击右边结束动画
 * Created by xuekai on 2017/2/21.
 */

public class SearchView extends View {

    private Paint mPaint;
    //加载圈的半径
    private int loadingRadius = 150;
    //放大镜圆圈的中心
    private float[] magnifyPos = new float[2];

    private final int STATE_PRE = 0;
    private final int STATE_LOADING = 1;
    private final int STATE_AFTER = 2;
    private int STATE_CURRENT = STATE_PRE;

    private Path magnifyPath;
    private Path loadPath;

    private float pre_progress = 0;
    private float after_progress = 0;
    private float load_progress = 0;
    private PathMeasure magnifyPathMeasure;
    private float magnifyPathLength;

    /**
     * 让动画停止的flag
     */
    private boolean isStop = false;
    /**
     * 从放大镜截取的path
     */
    private Path magnifyPathDst;
    private PathMeasure loadingPathMeasure;
    private float loadingPathLength;
    private Path loadingPathDst;

    private float start;
    private float stop;

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        magnifyPos[0] = (float) (-loadingRadius * Math.sqrt(2) / 4);
        magnifyPos[1] = (float) (-loadingRadius * Math.sqrt(2) / 4);

        setBackgroundColor(0xff007FD4);

        mPaint = new Paint();
        mPaint.setColor(0xffffffff);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);


        magnifyPath = new Path();
        magnifyPath.addArc(new RectF(magnifyPos[0] - loadingRadius / 2, magnifyPos[1] - loadingRadius / 2, magnifyPos[0] + loadingRadius / 2, magnifyPos[1] + loadingRadius / 2), 45, 359);
        magnifyPath.lineTo((float) (loadingRadius / Math.sqrt(2)), (float) (loadingRadius / Math.sqrt(2)));
        loadPath = new Path();
        loadPath.addArc(new RectF(-loadingRadius, -loadingRadius, loadingRadius, loadingRadius), 45, -359.9f);


        magnifyPathMeasure = new PathMeasure();
        magnifyPathMeasure.setPath(magnifyPath, false);
        magnifyPathLength = magnifyPathMeasure.getLength();

        loadingPathMeasure = new PathMeasure();
        loadingPathMeasure.setPath(loadPath, false);
        loadingPathLength = loadingPathMeasure.getLength();

        magnifyPathDst = new Path();
        loadingPathDst = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth() / 2, getHeight() / 2);

        drawSearch(canvas);


    }

    private static final String TAG = "SearchView";

    private void drawSearch(Canvas canvas) {


        Log.e(TAG, "drawSearch");
        switch (STATE_CURRENT) {
            case STATE_PRE:
                magnifyPathDst.rewind();
                magnifyPathMeasure.getSegment(pre_progress * magnifyPathLength, magnifyPathLength, magnifyPathDst, true);
                canvas.drawPath(magnifyPathDst, mPaint);
                break;
            case STATE_LOADING:



                loadingPathDst.rewind();
                stop = (float) (1.1 * load_progress * loadingPathLength);
                start = (float) (stop - loadingRadius / 5 * Math.PI * Math.sin(load_progress * Math.PI));

                loadingPathMeasure.getSegment(start, stop, loadingPathDst, true);
                canvas.drawPath(loadingPathDst, mPaint);


                break;
            case STATE_AFTER:

                magnifyPathDst.rewind();
                magnifyPathMeasure.getSegment((1 - after_progress) * magnifyPathLength, magnifyPathLength, magnifyPathDst, true);
                canvas.drawPath(magnifyPathDst, mPaint);
                break;
        }


    }

    /**
     * 加载之前的动画
     */
    ValueAnimator preAnimator = ValueAnimator.ofFloat(0, 1);
    /**
     * 加载之前的动画
     */
    ValueAnimator afterAnimator = ValueAnimator.ofFloat(0, 1);

    /**
     * 加载中中的动画
     */
    ValueAnimator loadingAnimator = ValueAnimator.ofFloat(0, 1);
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            loadingAnimator.start();
            return false;
        }
    });

    /**
     * 开始加载动画
     */
    private void startLoading() {
        STATE_CURRENT = STATE_LOADING;
        loadingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                load_progress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        loadingAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //判断是否需要停止，如果需要停止，调用stopAnimal（）
                if (isStop) {
                    isStop = false;
                    stopAnimation();
                } else {
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        loadingAnimator.setDuration(2000);

        loadingAnimator.start();
    }

    /**
     * 开始准备动画
     */
    private void stateAnimation() {
        STATE_CURRENT = STATE_PRE;
        preAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pre_progress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        preAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                STATE_CURRENT = STATE_LOADING;
                after_progress = 0;
                startLoading();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        preAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        preAnimator.setDuration(2000);
        preAnimator.start();
    }

    /**
     * 开始结束动画
     */
    private void stopAnimation() {
        STATE_CURRENT = STATE_AFTER;
        afterAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                after_progress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        afterAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                STATE_CURRENT = STATE_PRE;
//                pre_progress = 0;
//                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        afterAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        afterAnimator.setDuration(2000);
        afterAnimator.start();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getX()<getWidth()/2) {
            start();

        }else{
            stop();
        }
        return false;
    }


    /**
     * xxxxx
     */
    public void start() {
        stateAnimation();
    }
    /**
     * xxxxx
     */
    public void stop() {
        isStop = true;
    }
}
