package com.xk.customview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xk.customview.custom.MCustom;
import com.xk.customview.custom.PieData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MCustom viewById = (MCustom) findViewById(R.id.mc);
        PieData a1 = new PieData("x1x", 10);
        PieData a2 = new PieData("x2x", 20);
        PieData a3 = new PieData("x3x", 130);
        PieData a4 = new PieData("x4x", 40);
        ArrayList<PieData> pieDatas = new ArrayList<>();
        pieDatas.add(a1);
        pieDatas.add(a2);
        pieDatas.add(a3);
        pieDatas.add(a4);
        viewById.setData(pieDatas);

        Picture picture = new Picture();
        Canvas canvas = picture.beginRecording(100, 100);

        canvas.translate(100,100);
        Paint paint = new Paint();
        paint.setColor(0xff00ff00);
        canvas.drawCircle(50,50,50,paint);
        picture.endRecording();
        viewById.setPic(picture);


    }
}
