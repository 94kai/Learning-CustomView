package com.xk.customview.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xk.customview.R;
import com.xk.customview.custom.HotLabelView;
import com.xk.ioclibrary.annotations.InjectContentView;
import com.xk.ioclibrary.annotations.InjectView;

import java.util.Random;

@InjectContentView(R.layout.activity_hot_label)
public class HotLabelActivityView extends ViewBaseActivity {
    @InjectView(R.id.hotlabel)
    private HotLabelView hotLabelView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            TextView textView = new TextView(this);
            textView.setPadding(5,5,5,5);

            textView.setBackgroundColor(Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            marginLayoutParams.setMargins(5,5,5,5);
            textView.setLayoutParams(marginLayoutParams);
            String content="";
            int textCount = random.nextInt(4) + 3;
            for (int i1 = 0; i1 < textCount; i1++) {
                content+="哈";
            }
            textView.setText(content);
            hotLabelView.addView(textView);
        }


    }
}
