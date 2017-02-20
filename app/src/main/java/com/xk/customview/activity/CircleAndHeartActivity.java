package com.xk.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xk.customview.R;
import com.xk.customview.custom.CircleAndHeartView;

/**
 * Created by xuekai on 2017/2/20.
 */

public class CircleAndHeartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_heart);
        CircleAndHeartView circle = (CircleAndHeartView) findViewById(R.id.circleAndHeartView);
        circle.startAnimation();
    }
}
