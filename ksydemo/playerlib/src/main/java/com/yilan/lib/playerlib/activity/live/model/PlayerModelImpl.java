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

    @Override
    public void getGameInfo(final ResponseCallback callback) {
        ApiLive.getGameInfo(callback);
    }


    @Override
    public void sendCommentToServer(String uid, String nickname, String comment,
                                    ResponseCallback callback){
        ApiLive.comment(uid, nickname, comment, callback);
    }

    @Override
    public void sendAnswer(String uid, String questionId, String answerId, ResponseCallback callback) {
        ApiLive.answer(uid, questionId, answerId, callback);
    }

}
