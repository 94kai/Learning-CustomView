package com.xk.customview.custom.recyclerview;

import android.view.View;

import java.util.Stack;

/**
 * 回收池
 * Created by xuekai on 2019/4/6.
 */
public class Recycler {
    //栈的数组。itemType的数量就是数组的长度。每个itemType对应一个数组。
    private Stack<View>[] pool;


    public Recycler(int itemTypeCount) {
        pool = new Stack[itemTypeCount];
        for (int i = 0; i < pool.length; i++) {
            pool[i] = new Stack<>();
        }
    }
    public static void main(String[] args) {
        Recycler recycler = new Recycler(10);
    }

    /**
     * 通过itemType从回收池中获取一个view，可能为空
     */
    View getViewByItemType(int itemType) {
        try {
            return pool[itemType].pop();
        }catch (Exception e){
            return null;
        }
    }

    void saveView(View view,int type){
        pool[type].push(view);
    }
}

