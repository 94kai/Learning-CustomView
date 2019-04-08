package com.xk.customview.custom.recyclerview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xuekai on 2019/4/6.
 */
public interface Adapter {
    /**
     * 获取数目
     *
     * @return
     */
    int getCount();

    /**
     * 获取指定position的类型
     *
     * @param position
     * @return
     */
    int getItemType(int position);

    /**
     * 获取类型数目
     *
     * @return
     */
    int getViewTypeCount();

    View getView(int position, View convertView, ViewGroup parent);

    int getHeight();
}
