package com.xk.customview.custom.zoomImageViewPager;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import static android.R.attr.data;

/**
 * Created by X.Sation on 2017/2/22.
 */

public class MAdapter extends PagerAdapter {
    private List<Integer> data;
    private Context context;

    public MAdapter(List<Integer> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ZoomImageView zoomImageView = new ZoomImageView(context);
        zoomImageView.setImageResource(data.get(position));
        container.addView(zoomImageView);
        return zoomImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
