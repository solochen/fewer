package com.yilan.lib.playerlib.activity.live.model;

import com.yilan.lib.playerlib.listener.ResponseCallback;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public interface IPlayerModel {


    void gameLiveEnter(String uid, String liveId, ResponseCallback callback);

    void gameLiveExit(String uid, String liveId, ResponseCallback callback);
}
