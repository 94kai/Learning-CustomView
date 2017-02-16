package com.xk.customview.custom;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xk.customview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.R.attr.y;

/**
 * Created by xuekai on 2017/2/16.
 */

public class CustomProgressBar extends View {

    private Random random;
    private static final String TAG = "CustomProgressBar";
    private int width = 0;
    private int height = 0;
    private int frameWidth = 0;
    private int frameHeight = 0;
    private Bitmap frameBitmap;
    private Bitmap leaf;
    private Bitmap fengshan;
    private Paint mBitmapPaint;
    private Paint mWhitePaint;
    private Paint mOrangePaint;
    // 用于控制绘制的进度条距离左／上／下的距离
    private static final int LEFT_MARGIN = 6;
    // 用于控制绘制的进度条距离右的距离
    private static final int RIGHT_MARGIN = 34;
    // 用于控制绘制的进度条距离上、下的距离
    private static final int TOP_BOTTOM_MARGIN = 5;
    // 淡白色
    private static final int WHITE_COLOR = 0xfffde399;
    // 橙色
    private static final int ORANGE_COLOR = 0xffffa800;


    private int leaf_count = 0;

    private int progress_totalWidth;

    private List<Leaf> leafs = new ArrayList<>();

    private int currentProgress = 0;
    private int fengshanHeight = 0;
    private int fengshanWidth = 0;
    private float whiteRectWidth;
    private float orangeRectWidth;

    private static final int FENGSHANSPEED_MIN = 3;
    private static final int FENGSHANSPEED_MAX = 8;

    private float fengShanSpeed = FENGSHANSPEED_MIN;
    private int fengShanRoate = 0;

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        random = new Random();
        frameBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.leaf_kuang)).getBitmap();
        leaf = ((BitmapDrawable) getResources().getDrawable(R.mipmap.leaf)).getBitmap();
        fengshan = ((BitmapDrawable) getResources().getDrawable(R.mipmap.fengshan)).getBitmap();

        frameWidth = frameBitmap.getWidth();
        frameHeight = frameBitmap.getHeight();
        fengshanWidth = fengshan.getWidth();
        fengshanHeight = fengshan.getHeight();

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);
        mBitmapPaint.setFilterBitmap(true);

        mWhitePaint = new Paint();
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setColor(WHITE_COLOR);

        mOrangePaint = new Paint();
        mOrangePaint.setAntiAlias(true);
        mOrangePaint.setColor(ORANGE_COLOR);

        progress_totalWidth = frameWidth - RIGHT_MARGIN - LEFT_MARGIN;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawProgressAndLeaf(canvas);

        drawFrame(canvas);

        drawFengShan(canvas);
        invalidate();

    }

    private void drawFengShan(Canvas canvas) {
        canvas.save();
        canvas.translate(width / 2 + frameWidth / 2 - fengshanWidth / 2 - 4, height / 2);
        fengShanRoate += (int)fengShanSpeed;
        Log.e(TAG, "drawFengShan" + fengShanSpeed);
        fengShanRoate = fengShanRoate % 360;
        canvas.rotate(fengShanRoate, 0, 0);
        canvas.drawBitmap(fengshan, -fengshanWidth / 2, -fengshanHeight / 2, mBitmapPaint);
        canvas.restore();
    }

    private void drawProgressAndLeaf(Canvas canvas) {
        canvas.save();
        canvas.translate(width / 2, height / 2);

        orangeRectWidth = progress_totalWidth / 100f * currentProgress;
        whiteRectWidth = progress_totalWidth - orangeRectWidth;


        //画白色矩形
        canvas.drawRect(-frameWidth / 2 + LEFT_MARGIN + orangeRectWidth, -frameHeight / 2 + TOP_BOTTOM_MARGIN, frameWidth / 2 - RIGHT_MARGIN, frameHeight / 2 - TOP_BOTTOM_MARGIN, mWhitePaint);

        clearAbandonLeaf();


        //最后一个叶子的x是最右边的，速度加1
        if (leafs.size()>0) {
            if (leafs.get(leafs.size()-1).x==frameWidth / 2 - RIGHT_MARGIN) {
                fenShanSpeedUp();
            }else{
                fenShanSpeedDown();
            }
        }else{
            fenShanSpeedDown();
        }
        for (Leaf leaf1 : leafs) {


            //画叶子
            canvas.save();
            canvas.rotate(leaf1.roate, leaf1.x + leaf.getWidth() / 2, leaf1.y);
            canvas.drawBitmap(leaf, leaf1.x, leaf1.y, mBitmapPaint);
            leaf1.nextX();
            leaf1.nextRoate();
            leaf1.nextY();
            canvas.restore();

        }


        //画橘色矩形
        canvas.drawRect(-frameWidth / 2 + LEFT_MARGIN, -frameHeight / 2 + TOP_BOTTOM_MARGIN, frameWidth / 2 - RIGHT_MARGIN - whiteRectWidth, frameHeight / 2 - TOP_BOTTOM_MARGIN, mOrangePaint);


        canvas.restore();
    }

    /**
     * 清除废弃的叶子
     */
    private void clearAbandonLeaf() {
        int index = -1;
        for (int i = 0; i < leafs.size(); i++) {
            if (leafs.get(i).x <= frameWidth / 2 - RIGHT_MARGIN - whiteRectWidth - leaf.getWidth() || leafs.get(i).x <= -frameWidth / 2 + LEFT_MARGIN) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            for (int i = index; i >= 0; i--) {
                leafs.remove(i);
                Log.e(TAG, "clearAbandonLeaf");
            }
        }
    }

    public void drawFrame(Canvas canvas) {
        canvas.save();
        canvas.translate(width / 2, height / 2);
        canvas.drawBitmap(frameBitmap, -frameWidth / 2, -frameHeight / 2, mBitmapPaint);
        canvas.restore();
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        if (leafs.size() > 0) {
            Leaf lastLeaf = leafs.get(leafs.size() - 1);
            if (frameWidth / 2 - RIGHT_MARGIN - lastLeaf.x > 20) {
                Leaf leaf = new Leaf();
                leafs.add(leaf);
            }
        } else {
            Leaf leaf = new Leaf();
            leafs.add(leaf);
        }


    }

    public int getCurrentProgress() {
        return currentProgress;
    }


    //进度加1，速度加2，有上限
    private void fenShanSpeedUp(){
        if (fengShanSpeed>=FENGSHANSPEED_MAX) {
            fengShanSpeed=FENGSHANSPEED_MAX;
        }else{
            fengShanSpeed+=4;
        }
    }
    //进度不动，速度减1，有下限
    private void fenShanSpeedDown(){
        if (fengShanSpeed<=FENGSHANSPEED_MIN+0.1) {
            fengShanSpeed=FENGSHANSPEED_MIN;
        }else{
            fengShanSpeed-=0.1f;
        }
    }

    class Leaf {
        public Leaf() {
            roateDirection = random.nextBoolean();
            amplitude = random.nextBoolean();
        }

        //旋转方向
        boolean roateDirection;


        //振幅方向
        boolean amplitude;


        int x = frameWidth / 2 - RIGHT_MARGIN;
        int y = 0;
        float roate = 0;

        public void nextRoate() {
            if (roateDirection) {
                roate++;

            } else {
                roate--;
            }
        }

        public void nextX() {
            x -= 1;
        }

        public void nextY() {
            if (amplitude) {
                y = (int) (-(frameHeight / 10) * Math.sin(x / 8.5));
            } else {
                y = (int) ((frameHeight / 10) * Math.sin(x / 8.5));
            }

        }
    }

}
