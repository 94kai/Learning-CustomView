package com.xk.customview;

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
    }
}
