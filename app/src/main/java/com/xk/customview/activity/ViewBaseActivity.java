package com.xk.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xk.ioclibrary.BaseActivity;

/**
 * Created by xuekai on 2017/11/18.
 */

public class ViewBaseActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            String title = getIntent().getStringExtra("title");
            if (title!=null) {
                getSupportActionBar().setTitle(title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
