package com.yilan.lib.playerlib.data;

/**
 * Created by chenshaolong on 2018/1/16.
 */

public class UseInviteCode {

    private int code;
    private String message;
    private int revive_count;

    public boolean success(){
        return (code == 200) ? true : false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRevive_count() {
        return revive_count;
    }

    public void setRevive_count(int revive_count) {
        this.revive_count = revive_count;
    }
}
