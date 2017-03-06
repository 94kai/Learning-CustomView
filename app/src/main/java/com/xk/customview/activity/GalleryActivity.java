package com.xk.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.xk.customview.R;
import com.xk.customview.custom.gallery.GalleryAdapter;
import com.xk.customview.custom.gallery.GalleryView;

import java.util.ArrayList;

/**
 * 方法：创建一个GalleryView， setDatas设置图片id(目前只支持本地资源图片，网络可以直接修改代码) ，last、next表示前后翻页，  start、stop表示开启、停止动画
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


        galleryView.setDatas(datas);





        findViewById(R.id.last).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryView.last();
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryView.start();

            }
        });
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
            case KeyEvent.KEYCODE_DPAD_UP:
                galleryView.start();
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                galleryView.stop();

                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
