package com.yilan.lib.playerlib.event;

import io.rong.imlib.model.Message;

/**
 * Created by chenshaolong on 2018/1/15.
 */

public class RongEvent {

    private Message msg;

    public RongEvent(Message msg){
        this.msg = msg;
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }
}
