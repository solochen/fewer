package com.yilan.lib.playerlib.listener;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public interface OnChatRoomStatusCallback {

    void onJoining(String s);
    void onJoined(String s);
    void onQuited(String s);
    void onStatusError();
}
