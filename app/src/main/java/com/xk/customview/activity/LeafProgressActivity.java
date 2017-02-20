package com.xk.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xk.customview.R;
import com.xk.customview.custom.CircleAndHeartView;
import com.xk.customview.custom.LeafProgressBar;

/**
 * Created by xuekai on 2017/2/20.
 */

public class LeafProgressActivity extends AppCompatActivity {
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaf_progress);
        final LeafProgressBar progressBar = (LeafProgressBar) findViewById(R.id.progress);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (progress==100) {
                        break;
                    }
                    progress += 1;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setCurrentProgress(progress);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
