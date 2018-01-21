package com.yilan.lib.playerlib.event;


/**
 * Created by chenshaolong on 2018/1/15.
 */

public class LiveEvent {

    public static final int EVENT_LIVE_FINISH = -1;
    public static final int EVENT_LIVE_OPEN_CARD_START = 1;
    public static final int EVENT_LIVE_OPEN_CARD_END = 2;
    public static final int EVENT_LIVE_NOTIFY_EXIT_ALERT = 3;

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
