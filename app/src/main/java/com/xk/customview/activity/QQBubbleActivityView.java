package com.xk.customview.activity;

import android.os.Bundle;

import com.xk.customview.R;
import com.xk.customview.custom.WaveView;

/**
 * Created by xuekai on 2017/2/20.
 */

public class QQBubbleActivityView extends ViewBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq_bubble);
        WaveView viewById = (WaveView) findViewById(R.id.wave);
        viewById.startAnimation();
    }
}
