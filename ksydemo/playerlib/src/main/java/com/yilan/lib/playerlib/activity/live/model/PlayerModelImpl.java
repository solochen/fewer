package com.yilan.lib.playerlib.activity.live.model;

import com.yilan.lib.playerlib.http.ApiLive;
import com.yilan.lib.playerlib.listener.ResponseCallback;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class PlayerModelImpl implements IPlayerModel {


    @Override
    public void gameLiveEnter(String uid, String liveId, final ResponseCallback callback) {
        ApiLive.gameLiveEnter(uid, liveId, callback);
    }


    @Override
    public void gameLiveExit(String uid, String liveId, final ResponseCallback callback) {
        ApiLive.gameLiveExit(uid, liveId, callback);
    }



}
