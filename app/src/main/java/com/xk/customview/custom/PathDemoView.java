package com.xk.customview.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.xk.customview.R;

import static android.R.attr.bitmap;
import static android.R.attr.path;


public class PathDemoView extends View {
    private static final String TAG = "PathDemoView";
    public PathDemoView(Context context) {
        super(context);
    }

    public PathDemoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathDemoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.lineTo(100,100);
        path.lineTo(100,200);
        Log.e(TAG,"onDraw");
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(0xff00ff00);

        canvas.drawPath(path,paint);
    }
}