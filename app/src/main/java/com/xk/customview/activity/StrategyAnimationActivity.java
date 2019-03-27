package com.xk.customview.activity;

import android.os.Bundle;
import android.os.Handler;

import com.xk.customview.R;
import com.xk.customview.custom.strategyanimation.StrategyAnimationView;

/**
 * 策略模式 属性动画
 * Created by xuekai on 2019/3/27.
 */

public class StrategyAnimationActivity extends ViewBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((StrategyAnimationView) findViewById(R.id.animation)).overAnimation();
            }
        },1800);
    }
}
