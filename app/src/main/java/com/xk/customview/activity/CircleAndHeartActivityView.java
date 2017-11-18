package com.xk.customview.activity;

import android.os.Bundle;

import com.xk.customview.R;
import com.xk.customview.custom.CircleAndHeartView;

/**
 * Created by xuekai on 2017/2/20.
 */

public class CircleAndHeartActivityView extends ViewBaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_heart);
        CircleAndHeartView circle = (CircleAndHeartView) findViewById(R.id.circleAndHeartView);
        circle.startAnimation();
    }
}
