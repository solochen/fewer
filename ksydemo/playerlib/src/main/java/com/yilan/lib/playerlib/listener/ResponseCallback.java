package com.yilan.lib.playerlib.listener;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public interface ResponseCallback {

    void onSuccess(String s);
    void onError(int code, String msg);
    void onFinish();
}
