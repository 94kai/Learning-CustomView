package com.xk.customview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xk.customview.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends ViewBaseActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<String> datas;
    private HashMap<String, Class<? extends Activity>> stringClassHashMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list);

        datas = new ArrayList<>();
        stringClassHashMap = new HashMap<>();
        stringClassHashMap.put("0.Demo", DemoActivityView.class);
        stringClassHashMap.put("1.叶子进度条", LeafProgressActivityView.class);
        stringClassHashMap.put("2.弹性球", MagicCircleActivityView.class);
        stringClassHashMap.put("3.蜘蛛网（游戏属性图）", SpiderActivityView.class);
        stringClassHashMap.put("4.贝塞尔曲线", QQBubbleActivityView.class);
        stringClassHashMap.put("5.刻度表", InstrumentActivityView.class);
        stringClassHashMap.put("6.圆->心", CircleAndHeartActivityView.class);
        stringClassHashMap.put("7.旋转箭头", RoationActivityView.class);
        stringClassHashMap.put("8.搜索放大镜", SearchActivityView.class);
        stringClassHashMap.put("9.模仿可缩放的imageview（鸿洋）", ZoomImageViewPagerActivityView.class);
        stringClassHashMap.put("10.模仿折叠的layout（鸿洋）", FoldingLayoutActivityView.class);
        stringClassHashMap.put("11.事件分发", EventDispatchActivityView.class);
        stringClassHashMap.put("12.3D画廊", GalleryActivityView.class);
        stringClassHashMap.put("13.仿华为应用商店 自动换行标签", HotLabelActivityView.class);
        stringClassHashMap.put("14.Drawable实现滚动颜色变化", RevealDrawableActivityView.class);
        stringClassHashMap.put("15.策略模式属性动画", StrategyAnimationActivity.class);
        stringClassHashMap.put("16.动画框架", AnimatorFrameActivity.class);
        Set<String> strings = stringClassHashMap.keySet();

        for (String string : strings) {
            datas.add(string);
        }
        Collections.sort(datas, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int o11 = getIndex(o1);
                int o22 = getIndex(o2);
                return o11 - o22;
            }
        });
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemclick(parent, view, position, id);
            }
        });
    }

    public void itemclick(AdapterView<?> parent, View view, int position, long id) {
        Class<? extends Activity> aClass = stringClassHashMap.get(datas.get(position));
        Intent intent = new Intent(MainActivity.this, aClass);
        intent.putExtra("title", datas.get(position));
        startActivity(intent);

    }

    /**
     * 获取字符串的index
     *
     * @param o1
     * @return
     */
    private int getIndex(String o1) {
        String index = "";
        for (char c : o1.toCharArray()) {
            if (c <= '9' && c >= '0') {
                index += c;
            } else {
                break;
            }
        }
        return Integer.parseInt(index);
    }


}
