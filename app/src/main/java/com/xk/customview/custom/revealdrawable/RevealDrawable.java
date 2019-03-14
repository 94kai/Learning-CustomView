package com.xk.customview.custom.revealdrawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;

/**
 * Created by xuekai on 2019/3/13.
 */
public class RevealDrawable extends Drawable {

    Drawable iv1;
    Drawable iv2;
    private boolean revert;

    public RevealDrawable(Drawable iv1, Drawable iv2) {
        this.iv1 = iv1;
        this.iv2 = iv2;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        // 定好两个Drawable图片的宽高---边界bounds
        iv1.setBounds(bounds);
        iv2.setBounds(bounds);
    }


    public void setRevert(boolean isRevert) {
        this.revert = isRevert;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        Rect rect = new Rect();

//        level = 0, 左边裁剪全部
//        level = 10000， 右边裁剪全部
        int clipLeft = 0;
        clipLeft = (int) (bounds.width() - (bounds.width()) * ((mLevel) / 10000f));
        Drawable iv11;
        Drawable iv22;
        if (!revert) {
            iv11 = iv1;
            iv22 = iv2;
        } else {
            iv11 = iv2;
            iv22 = iv1;
        }
        int clipRight = bounds.width() - clipLeft;
        Gravity.apply(Gravity.LEFT
                , clipLeft, bounds.height()
                , getBounds(), rect);
        canvas.save();
        canvas.clipRect(rect);
        iv11.draw(canvas);
        canvas.restore();
        Gravity.apply(Gravity.RIGHT
                , clipRight, bounds.height()
                , getBounds(), rect);
        canvas.clipRect(rect);
        iv22.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    private int mLevel = 0;

    @Override
    protected boolean onLevelChange(int level) {
        if (mLevel != level) {
            mLevel = level;
            invalidateSelf();
            return true;
        }
        return false;
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
