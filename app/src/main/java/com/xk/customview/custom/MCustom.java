package com.xk.customview.custom;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.ArrayList;

import static android.R.attr.start;

/**
 * Created by xuekai on 2017/2/14.
 */

public class MCustom extends View {
        // 颜色表(注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
        private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
                0xFFE6B800, 0xFF7CFC00};
        // 饼状图初始绘制角度
        private float mStartAngle = 0;
        // 数据
        private ArrayList<PieData> mData;
        // 宽高
        private int mWidth, mHeight;
        // 画笔
        private Paint mPaint = new Paint();

        public MCustom(Context context) {
            this(context, null);
        }

        public MCustom(Context context, AttributeSet attrs) {
            super(context, attrs);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float r = 100;  // 饼状图半径
            if (pic != null) {
                canvas.drawPicture(pic,new RectF(0,0,100,30));
            }
            canvas.save();
            canvas.translate(100,100);                // 将画布坐标原点移动到中心位置
            RectF rect = new RectF(0, 0, r, r);                     // 饼状图绘制区域

            canvas.drawCircle(0,0,10,mPaint);
            canvas.restore();

            canvas.drawRect(rect,mPaint);

//            for (int i = 0; i < mData.size(); i++) {
//                PieData pie = mData.get(i);
//                mPaint.setColor(pie.getColor());
//                canvas.drawArc(rect, currentStartAngle, pie.getAngle(), true, mPaint);
//                currentStartAngle += pie.getAngle();
//            }

        }

        // 设置起始角度
        public void setStartAngle(int mStartAngle) {
            this.mStartAngle = mStartAngle;
            invalidate();   // 刷新
        }

        // 设置数据
        public void setData(ArrayList<PieData> mData) {
            this.mData = mData;
            initData(mData);
            invalidate();   // 刷新
        }

        // 初始化数据
        private void initData(ArrayList<PieData> mData) {
            if (null == mData || mData.size() == 0)   // 数据有问题 直接返回
                return;

            float sumValue = 0;
            for (int i = 0; i < mData.size(); i++) {
                PieData pie = mData.get(i);

                sumValue += pie.getValue();       //计算数值和

                int j = i % mColors.length;       //设置颜色
                pie.setColor(mColors[j]);
            }

            float sumAngle = 0;
            for (int i = 0; i < mData.size(); i++) {
                PieData pie = mData.get(i);

                float percentage = pie.getValue() / sumValue;   // 百分比
                float angle = percentage * 360;                 // 对应的角度

                pie.setPercentage(percentage);                  // 记录百分比
                pie.setAngle(angle);                            // 记录角度大小
                sumAngle += angle;

                Log.i("angle", "" + pie.getAngle());
            }
        }
    private Picture pic;
    public void setPic(Picture pic){
        this.pic=pic;
        invalidate();
    }
}
