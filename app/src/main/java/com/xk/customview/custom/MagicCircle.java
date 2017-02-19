package com.xk.customview.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import static android.R.attr.centerX;
import static android.R.attr.centerY;
import static android.R.attr.logo;
import static android.R.attr.offset;

/**
 * Created by xuekai on 2017/2/17.
 * 弹性球 完了看
 */

public class MagicCircle extends View {

    private Path mPath;
    private Paint mFillCirclePaint;

    /**
     * View的宽度
     **/
    private int width;
    /**
     * View的高度，这里View应该是正方形，所以宽高是一样的
     **/
    private int height;

    /**
     * 最大位移量
     **/
    private float maxLength;
    private float mInterpolatedTime;
    private float stretchDistance;
    //每一个阶段如果左右控制点的y需要变的话，最终的结果
    private float cDistance;
    private float radius;
    private float c;
    private float blackMagic = 0.551915024494f;
    private VPoint p2, p4;
    private HPoint p1, p3;


    private Paint textPaint;

    public MagicCircle(Context context) {
        this(context, null, 0);
    }

    public MagicCircle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MagicCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mFillCirclePaint = new Paint();
        mFillCirclePaint.setColor(0xFFfe626d);
        mFillCirclePaint.setStyle(Paint.Style.FILL);
        mFillCirclePaint.setStrokeWidth(1);
        mFillCirclePaint.setAntiAlias(true);
        mPath = new Path();
        p2 = new VPoint();
        p4 = new VPoint();

        p1 = new HPoint();
        p3 = new HPoint();

        textPaint = new Paint();
        textPaint.setTextSize(30);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        radius = 100;
        c = radius * blackMagic;
        stretchDistance = radius;
        cDistance = c * 0.45f;
        maxLength = width - radius - radius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        canvas.translate(radius, getHeight()/2);

        if (mInterpolatedTime >= 0 && mInterpolatedTime <= 0.2) {
            model1(mInterpolatedTime);
        } else if (mInterpolatedTime > 0.2 && mInterpolatedTime <= 0.5) {
            model2(mInterpolatedTime);
        } else if (mInterpolatedTime > 0.5 && mInterpolatedTime <= 0.8) {
            model3(mInterpolatedTime);
        } else if (mInterpolatedTime > 0.8 && mInterpolatedTime <= 0.9) {
            model4(mInterpolatedTime);
        } else if (mInterpolatedTime > 0.9 && mInterpolatedTime <= 1) {
            model5(mInterpolatedTime);
        }

        //让所有的点整体向右偏移（前两秒不需要，因为前两秒的工作是让最右边的点移动到2r的位置）
        float offset = maxLength * (mInterpolatedTime - 0.2f);
        offset = offset > 0 ? offset : 0;
        p1.adjustAllX(offset);
        p2.adjustAllX(offset);
        p3.adjustAllX(offset);
        p4.adjustAllX(offset);
        mPath.moveTo(p1.x, p1.y);
        mPath.cubicTo(p1.right.x, p1.right.y, p2.bottom.x, p2.bottom.y, p2.x, p2.y);
        mPath.cubicTo(p2.top.x, p2.top.y, p3.right.x, p3.right.y, p3.x, p3.y);
        mPath.cubicTo(p3.left.x, p3.left.y, p4.top.x, p4.top.y, p4.x, p4.y);
        mPath.cubicTo(p4.bottom.x, p4.bottom.y, p1.left.x, p1.left.y, p1.x, p1.y);

        canvas.drawPath(mPath, mFillCirclePaint);

        canvas.drawText("p1-left", p1.left.x, p1.left.y, textPaint);
        canvas.drawText("p1", p1.x, p1.y, textPaint);
        canvas.drawText("p1-right", p1.right.x, p1.right.y, textPaint);
        canvas.drawText("p3-left", p3.left.x, p3.left.y, textPaint);
        canvas.drawText("p3", p3.x, p3.y, textPaint);
        canvas.drawText("p3-right", p3.right.x, p3.right.y, textPaint);
        canvas.drawText("p2-top", p2.top.x, p2.top.y, textPaint);
        canvas.drawText("p2", p2.x, p2.y, textPaint);
        canvas.drawText("p4-top", p4.top.x, p4.top.y, textPaint);
        canvas.drawText("p2-bottom", p2.bottom.x, p2.bottom.y, textPaint);
        canvas.drawText("p4-bottom", p4.bottom.x, p4.bottom.y, textPaint);
        canvas.drawText("p4", p4.x, p4.y, textPaint);


    }

    /**
     * 初始化，最开始状态下的圆时的data和control点
     */
    private void model0() {
        //p1和p3是上下的点，他们的一旦确定 就不动了
        p1.setY(radius);
        p3.setY(-radius);

        p3.x = p1.x = 0;
        p3.left.x = p1.left.x = -c;
        p3.right.x = p1.right.x = c;

        p2.setX(radius);
        p4.setX(-radius);
        p2.y = p4.y = 0;
        p2.top.y = p4.top.y = -c;
        p2.bottom.y = p4.bottom.y = c;
    }


    /**
     * 第一阶段，只有p2的data和control的x逐渐增大 在0.2秒内，让右边的点到达2*radius处
     * @param time
     */
    private void model1(float time) {//0~0.2
        model0();
        p2.setX(radius+ stretchDistance * time * 5 );
    }

    /**
     * model1状态时，p1 p3在r处，最右边是2r，这一阶段要移动到中间，1.5r
     * @param time
     */
    private void model2(float time) {//0.2~0.5
        model1(0.2f);
        time = (time - 0.2f) * (10f / 3);
        p1.adjustAllX(stretchDistance / 2 * time);
        p3.adjustAllX(stretchDistance / 2 * time);
        p2.adjustY(cDistance * time);
        p4.adjustY(cDistance * time);
        Log.e("MagicCircle","model2"+stretchDistance / 2 * time);
        Log.e("MagicCircle","model2"+p1.x);
    }

    /**
     * 上一阶段，p1 p3移动到了1.5处，这一阶段移动到2处，也就是再移动0.5
     * @param time
     */
    private void model3(float time) {//0.5~0.8
        model2(0.5f);
        time = (time - 0.5f) * (10f / 3);
        p1.adjustAllX(stretchDistance / 2 * time);
        p3.adjustAllX(stretchDistance / 2 * time);
        p2.adjustY(-cDistance * time);
        p4.adjustY(-cDistance * time);

        p4.adjustAllX(stretchDistance / 2 * time);

    }
    /**
     * 这一阶段，p1,p3不再动了  p4右移0.25
     * @param time
     */
    private void model4(float time) {//0.8~0.9
        model3(0.8f);
        time = (time - 0.8f) * 10;
        p4.adjustAllX(stretchDistance / 2 * time);
    }
    /**
     * p4左移0.5
     * @param time
     */
    private void model5(float time) {
        model4(0.9f);
        time = time - 0.9f;
        p4.adjustAllX((float) (Math.sin(Math.PI * time * 10f) * (0.2f * stretchDistance)));

    }


    /**
     * 圆的左右data点（有上下两个control点）
     */
    class VPoint {
        //data点
        public float x;
        public float y;
        public PointF top = new PointF();
        public PointF bottom = new PointF();

        /**
         * 在变化过程中，左右两个data点上下的control点的x是跟data一样的
         * @param x
         */
        public void setX(float x) {
            this.x = x;
            top.x = x;
            bottom.x = x;
        }

        /**
         * 在变化过程中，左右两个data点上下的control点的y是需要在原来的基础上偏移的
         * @param offset
         */
        public void adjustY(float offset) {
            top.y -= offset;
            bottom.y += offset;
        }

        /**
         * 让x偏移一定的量
         * @param offset
         */
        public void adjustAllX(float offset) {
            this.x += offset;
            top.x += offset;
            bottom.x += offset;
        }
    }

    /**
     * 圆的上下data点（有左右两个control点）
     * y始终不变，x整体移动
     */
    class HPoint {
        public float x;
        public float y;
        public PointF left = new PointF();
        public PointF right = new PointF();

        public void setY(float y) {
            this.y = y;
            left.y = y;
            right.y = y;
        }

        public void adjustAllX(float offset) {
            this.x += offset;
            left.x += offset;
            right.x += offset;
        }
    }

    private class MoveAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            mInterpolatedTime = interpolatedTime;
            invalidate();
        }
    }

    public void startAnimation() {
        mPath.reset();
        mInterpolatedTime = 0;
        MoveAnimation move = new MoveAnimation();
        move.setDuration(20000);
        move.setInterpolator(new AccelerateDecelerateInterpolator());
        //move.setRepeatCount(Animation.INFINITE);
        //move.setRepeatMode(Animation.REVERSE);
        startAnimation(move);

        move.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                MagicCircle.this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startAnimation();
                    }
                }, 3000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
