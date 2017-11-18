package com.xk.customview.activity;

import android.os.Bundle;

import com.xk.customview.R;

/**
 * Created by xuekai on 2017/2/20.
 */

public class SearchActivityView extends ViewBaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle("点击左半屏开始搜索，点击右半屏，停止搜索");
//        getActionBar().setTitle("点击左半屏开始搜索，点击右半屏，停止搜索");
    }
}
