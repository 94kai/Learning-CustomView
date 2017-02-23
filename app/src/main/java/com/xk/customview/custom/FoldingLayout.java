package com.xk.customview.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.xk.customview.R;

/**
 * Created by xuekai on 2017/2/23.
 */

public class FoldingLayout extends View {

    private Matrix matrix;
    private BitmapFactory.Options options;
    private Bitmap bitmap;

    public FoldingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        options.outHeight = h;
        options.outWidth = w;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.dada, options);
//        float wScale = bitmap.getWidth()*1f / w;
//        float hScale = bitmap.getHeight()*1f / h;


        matrix = new Matrix();
        float[] src = {0, 0,
                bitmap.getWidth(), 0,
                0, bitmap.getHeight(),
                bitmap.getWidth(), bitmap.getHeight()
        };
        float[] dst = {0, 0,
                bitmap.getWidth(), getHeight() / 3,
                0, bitmap.getHeight(),
                bitmap.getWidth(), bitmap.getHeight() / 3 * 2
        };
        matrix.setPolyToPoly(src, 0, dst, 0,   src.length >> 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap,matrix,new Paint());

    }
}
