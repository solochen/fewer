package com.yilan.lib.playerlib.data;

import java.io.Serializable;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public abstract class BaseData implements Serializable{

    private int code = -1;
    private String message = "";

    public boolean success(){
        return code == 0 ? true : false;
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
}
