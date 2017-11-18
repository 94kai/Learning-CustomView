package com.xk.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.xk.customview.R;
import com.xk.customview.custom.zoomImageViewPager.MAdapter;

import java.util.ArrayList;

/**
 * Created by xuekai on 2017/2/20.
 */

public class ZoomImageViewPagerActivityView extends ViewBaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_viewpager);

        ViewPager pager= (ViewPager) findViewById(R.id.pager);

        ArrayList<Integer> ids= new ArrayList<>();

        ids.add(R.mipmap.da);
        ids.add(R.mipmap.xiao);
        ids.add(R.mipmap.dada);
        ids.add(R.mipmap.kuandagao);
        ids.add(R.mipmap.gaodakuan);
        pager.setAdapter(new MAdapter(ids,this));
    }
}
