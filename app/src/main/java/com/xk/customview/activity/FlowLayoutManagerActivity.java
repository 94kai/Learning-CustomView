package com.xk.customview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.xk.customview.R;
import com.xk.customview.custom.flowlayoutmanager.FlowAdapter;
import com.xk.customview.custom.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author xuekai
 * @date 2019/4/22
 */
public class FlowLayoutManagerActivity extends Activity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flowlayoutmanager_activity);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        initView();
    }

    private void initView() {
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        recyclerView.setLayoutManager(flowLayoutManager);
        ArrayList<String> data = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int i1 = random.nextInt(10)+1;
            StringBuffer stringBuffer = new StringBuffer(i+"");
            for (int j = 0; j < i1; j++) {
                stringBuffer.append("x");
            }
            data.add(stringBuffer.toString());
        }
        recyclerView.setAdapter(new FlowAdapter<>(data));
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        //由于流失布局不规整，所以这里一定要设置缓冲池的大小，否则默认的不够用，经常会导致一直创建。
        recycledViewPool.setMaxRecycledViews(0,10);
        recyclerView.setRecycledViewPool(recycledViewPool);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i("FlowLayoutManagerActivity","onScrolled-->"+recyclerView.getChildCount());
            }
        });
    }
}
