package com.yilan.lib.playerlib.event;

/**
 * Created by chenshaolong on 2018/1/15.
 */

public class LoginEvent {

    public static final int EVENT_LOGIN_RESULT = 1;

    private int type = -1;

    public LoginEvent(int eventType){
        this.type = eventType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
