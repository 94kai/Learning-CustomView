package com.xk.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.KeyEvent;

import com.xk.customview.R;
import com.xk.customview.custom.MagicCircle;
import com.xk.customview.custom.gallery.GalleryAdapter;
import com.xk.customview.custom.gallery.GalleryView;

import java.util.ArrayList;

/**
 * Created by xuekai on 2017/2/20.
 */

public class GalleryActivity extends AppCompatActivity {

    private GalleryView galleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        galleryView = (GalleryView) findViewById(R.id.recycler);

        SparseArray<Integer> datas = new SparseArray<Integer>();
        datas.append(0, R.mipmap.aaaa);
        datas.append(1, R.mipmap.bbbbb);
        datas.append(2, R.mipmap.ccccc);
        datas.append(3, R.mipmap.dddd);
        datas.append(4, R.mipmap.eeeee);

        GalleryAdapter galleryAdapter = new GalleryAdapter(this, datas);

        galleryView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        galleryView.setAdapter(galleryAdapter);

        galleryView.scrollToPosition(50);
        galleryView.requestFocus();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                galleryView.last();
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                galleryView.next();

                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
