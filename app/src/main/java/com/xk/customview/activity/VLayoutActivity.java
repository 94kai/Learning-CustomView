package com.xk.customview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xk.customview.R;
import com.xk.customview.custom.vlayout.BaseDelegateAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xuekai
 * @date 2019/4/22
 */
public class VLayoutActivity extends Activity {

    private static final int ITEM_TYPE_GRID = 2;
    private RecyclerView recyclerView;
    private int ITEM_TYPE_BANNER = 1;
    private List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vlayout_activity);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        initView();
    }

    private void initView() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
        recyclerView.setLayoutManager(virtualLayoutManager);
        //如果一屏内相同类型的item较多，可以设置合适的回收池大小，防止来回滚动重复创建。
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(recycledViewPool);
        //设置type为0的item最多缓存10条。举个例子：如果所有的类型都是0，那上下滑动的时候，其实近似于每次只会回收一条，
        //然后立即使用这条，相当于不需要缓存。但是如果有其他类型，如果设置缓存5，可能现在屏幕有10条一样的，然后往下滑全部展示其他类型，
        //类型为0的就会被缓存起来，最多只能缓存到5条，但是往回滑的时候，需要展示10条，这样还有5条需要重新创建，就很耗时了。
        recycledViewPool.setMaxRecycledViews(0, 10);
        //第二个参数？？？
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);

        recyclerView.setAdapter(delegateAdapter);


        //banner
        final BaseDelegateAdapter bannerDelegateAdapter = new BaseDelegateAdapter(new SingleLayoutHelper(), 1, R.layout.vlayout_banner, this, ITEM_TYPE_BANNER) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                Button childAt = (Button) ((ViewGroup) holder.itemView).get ChildAt(0);
                childAt.setText("banner");
                Log.i("VLayoutActivity", "onBindViewHolder-->banner1");

            }
        };

        //网格
        BaseDelegateAdapter baseDelegateAdapter = new BaseDelegateAdapter(new GridLayoutHelper(5, 10), 10, R.layout.vlayout_banner, this, ITEM_TYPE_GRID) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                Button childAt = (Button) ((ViewGroup) holder.itemView).getChildAt(0);
                childAt.setText("grid:" + position);
                Log.i("VLayoutActivity", "onBindViewHolder-->grid");
            }
        };
        //网格
        final BaseDelegateAdapter baseDelegateAdapter1 = new BaseDelegateAdapter(new GridLayoutHelper(5, 10), 10, R.layout.vlayout_banner, this, ITEM_TYPE_GRID) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                Button childAt = (Button) ((ViewGroup) holder.itemView).getChildAt(0);
                childAt.setText("grid:" + position);
                Log.i("VLayoutActivity", "onBindViewHolder-->grid2");

            }
        };

        //banner
        BaseDelegateAdapter bannerDelegateAdapter1 = new BaseDelegateAdapter(new SingleLayoutHelper(), 1, R.layout.vlayout_banner, this, ITEM_TYPE_BANNER) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                Button childAt = (Button) ((ViewGroup) holder.itemView).getChildAt(0);
                childAt.setText("banner");
                Log.i("VLayoutActivity", "onBindViewHolder-->banner2");
                childAt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        baseDelegateAdapter1.setmCount(20);
                        baseDelegateAdapter1.notifyDataSetChanged();
                    }
                });

            }
        };

        adapters.add(bannerDelegateAdapter);
        adapters.add(baseDelegateAdapter);
        adapters.add(bannerDelegateAdapter1);
        adapters.add(baseDelegateAdapter1);
        delegateAdapter.setAdapters(adapters);
    }
}
