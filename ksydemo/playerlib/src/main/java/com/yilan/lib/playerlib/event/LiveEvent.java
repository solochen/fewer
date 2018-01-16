package com.yilan.lib.playerlib.event;


/**
 * Created by chenshaolong on 2018/1/15.
 */

public class LiveEvent {

    public static final int EVENT_LIVE_FINISH = -1;

    private int type;

    public LiveEvent(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
