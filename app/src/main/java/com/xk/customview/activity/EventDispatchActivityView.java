package com.xk.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.xk.customview.R;

import static com.xk.customview.R.id.view;

/**
 * Created by xuekai on 2017/2/20.
 */

public class EventDispatchActivityView extends ViewBaseActivity {
    private static final String TAG = "EventDispatchActivityView";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dispatch);
        View viewById = findViewById(view);
        viewById.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e(TAG, "onTouch");
                return false;
            }
        });
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " );
            }
        });
        
        viewById.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e(TAG,"onLongClick");
                return false;
            }
        });
    }
}
