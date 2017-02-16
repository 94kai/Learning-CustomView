package com.xk.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.xk.customview.custom.CustomProgressBar;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private CustomProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (progressBar.getCurrentProgress() == 0) {
                    return true;
                }
                progressBar.setCurrentProgress(progressBar.getCurrentProgress()-1);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (progressBar.getCurrentProgress() == 100) {
                    return true;
                }
                progressBar.setCurrentProgress(progressBar.getCurrentProgress()+1);
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}
