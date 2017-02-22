package com.xk.customview.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xk.customview.R;
import com.xk.customview.custom.MagicCircle;

/**
 * Created by xuekai on 2017/2/20.
 */

public class SearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle("点击左半屏开始搜索，点击右半屏，停止搜索");
//        getActionBar().setTitle("点击左半屏开始搜索，点击右半屏，停止搜索");
    }
}
