package com.xk.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xk.customview.R;
import com.xk.customview.custom.LeafProgressBar;
import com.xk.customview.custom.MagicCircle;

/**
 * Created by xuekai on 2017/2/20.
 */

public class MagicCircleActivity extends AppCompatActivity {
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_circle);
         MagicCircle magicCircle = (MagicCircle) findViewById(R.id.magic);
        magicCircle.startAnimation();

    }
}
