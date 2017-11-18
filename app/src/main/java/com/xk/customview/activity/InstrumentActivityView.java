package com.xk.customview.activity;

import android.os.Bundle;
import android.widget.SeekBar;

import com.xk.customview.R;
import com.xk.customview.custom.InstrumentView;

/**
 * 刻度表
 * Created by xuekai on 2016/10/28.
 */

public class InstrumentActivityView extends ViewBaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument);

        final InstrumentView viewById = (InstrumentView) findViewById(R.id.instrumentView);
        SeekBar viewById1 = (SeekBar) findViewById(R.id.sb);

        viewById1.setMax(100);
        viewById1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewById.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
