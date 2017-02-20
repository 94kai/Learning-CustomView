package com.xk.customview.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xk.customview.R;
import com.xk.customview.custom.InstrumentView;
import com.xk.customview.custom.MagicCircle;
import com.xk.customview.custom.SpiderView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] datas = {"0.Demo", "1.叶子进度条", "2.弹性球", "3.蜘蛛网（游戏属性图）", "4.qq未读消息气泡", "5.刻度表", "6.圆->心"};

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, DemoActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, LeafProgressActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, MagicCircleActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, SpiderActivity.class));
                    case 4:
                        startActivity(new Intent(MainActivity.this, QQBubbleActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this, InstrumentActivity.class));
                        break;

                    case 6:
                        startActivity(new Intent(MainActivity.this, CircleAndHeartActivity.class));
                        break;
                }
            }
        });
    }


}
