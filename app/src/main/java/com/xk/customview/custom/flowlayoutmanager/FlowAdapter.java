package com.xk.customview.custom.flowlayoutmanager;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xk.customview.custom.base.BaseAdapter;

import java.util.List;

/**
 * @author xuekai
 * @date 2019/4/23
 */
public class FlowAdapter<Data> extends BaseAdapter<Data> {


    public FlowAdapter(List<Data> data) {
        super(data);
    }

    @Override
    protected View getViewHolderItemView(@NonNull Context context, int viewType) {
        Log.i("FlowAdapter","getViewHolderItemView-->");
        return new TextView(context);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Log.i("FlowAdapter","onBindViewHolder-->");
        holder.itemView.setBackgroundColor(Color.RED);
        holder.itemView.setPadding(0,0,15,0);
        ((TextView) holder.itemView).setText(data.get(position) + "");
        ((TextView) holder.itemView).setTextSize(42);
    }
}
