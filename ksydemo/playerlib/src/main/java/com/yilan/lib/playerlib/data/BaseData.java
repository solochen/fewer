package com.yilan.lib.playerlib.data;

import java.io.Serializable;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public abstract class BaseData implements Serializable{

    private int code;
    private String message;

    public boolean success(){
        return code == 0 ? true : false;
    }

}
