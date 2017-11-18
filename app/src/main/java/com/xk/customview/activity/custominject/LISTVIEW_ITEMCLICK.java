package com.xk.customview.activity.custominject;

import android.widget.AdapterView;

import com.xk.ioclibrary.annotations.event.BaseEvent;
import com.xk.ioclibrary.annotations.event.eventtype.EventType;

/**
 * Created by xuekai on 2017/11/18.
 */
@BaseEvent(setListenerMethodName = "setOnItemClickListener", listener = AdapterView.OnItemClickListener.class, callBackMethodName = "onItemClick")
public class LISTVIEW_ITEMCLICK extends EventType {
}
