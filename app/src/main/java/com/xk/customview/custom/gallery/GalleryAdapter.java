package com.xk.customview.custom.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xk.customview.R;
import com.xk.customview.activity.GalleryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuekai on 2017/2/27.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MViewHolder> {
    private Context context;
    private SparseArray<Integer> datas = new SparseArray<Integer>();

    public GalleryAdapter(Context context, SparseArray<Integer> datas) {
        this.context = context;
        this.datas = datas;
    }


    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_gallery, null);
        int recyclerViewHeight = parent.getHeight();
        int recyclerViewWidth = parent.getWidth();
        itemView.setLayoutParams(new RecyclerView.LayoutParams(recyclerViewWidth/3,recyclerViewHeight));
        return new MViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {
        holder.imageView.setImageResource(datas.get(position % 5));
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    class MViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
