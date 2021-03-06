package com.yilan.lib.playerlib.activity.home.model;

import com.yilan.lib.playerlib.listener.ResponseCallback;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public interface IHomeModel {

    void getInviteInfo(String uid, ResponseCallback callback);

    void getGameInfo(final ResponseCallback callback);

    void useInviteCode(String uid, String code, ResponseCallback callback);
}
