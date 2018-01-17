package com.yilan.lib.playerlib.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by chenshaolong on 2018/1/16.
 */

public class EBus {

    public static void send(Object obj){
        EventBus.getDefault().post(obj);
    }


    public static void register(Object obj){
        EventBus.getDefault().register(obj);
    }


    public static void unregister(Object obj){
        EventBus.getDefault().unregister(obj);
    }

}
