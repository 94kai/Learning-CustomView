package com.xk.customview.custom.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author xuekai
 * @date 2019/4/26
 */
public class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {
    protected List<T> data;

    public BaseAdapter() {
    }

    public BaseAdapter(List<T> data) {
        this.data = data;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewHolderLayoutId = getViewHolderLayoutId(viewType);
        View itemView;
        if (viewHolderLayoutId != -1) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(viewHolderLayoutId, parent, false);
        } else {
            itemView = getViewHolderItemView(parent.getContext(), viewType);
        }
        return new BaseViewHolder(itemView == null ? new View(parent.getContext()) : itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    protected int getViewHolderLayoutId(int viewType) {
        return -1;
    }

    protected View getViewHolderItemView(@NonNull Context context, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }
}
