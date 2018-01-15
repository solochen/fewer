package com.yilan.lib.playerlib.activity.home.model;

import com.yilan.lib.playerlib.http.ApiLive;
import com.yilan.lib.playerlib.listener.ResponseCallback;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class HomeModelImpl implements IHomeModel {


    @Override
    public void getInviteInfo(String uid, final ResponseCallback callback) {
        ApiLive.getInviteInfo(uid, callback);
    }


    @Override
    public void getGameInfo(final ResponseCallback callback) {
        ApiLive.getGameInfo(callback);
    }

    @Override
    public void useInviteCode(String uid, String code, final ResponseCallback callback) {
        ApiLive.useInviteCode(uid, code, callback);
    }

}
