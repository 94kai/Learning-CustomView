package com.xk.customview.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xk.customview.R;
import com.xk.customview.custom.recyclerview.Adapter;
import com.xk.customview.custom.recyclerview.RecyclerView;

/**
 * Created by xuekai on 2019/3/31.
 */

public class RecyclerViewActivity extends ViewBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.root);

        recyclerView.setAdapter(new Adapter() {
            @Override
            public int getCount() {
                return 17;
            }

            @Override
            public int getItemType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Button button = new Button(parent.getContext());
                button.setText("  " + position);
                return button;
            }

            @Override
            public int getHeight() {
                return 150;
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int childCount = recyclerView.getChildCount();
                System.out.println("a=="+childCount);
            }
        },1000);
    }
}
