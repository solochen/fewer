package com.yilan.lib.playerlib.activity.live.model;

import com.yilan.lib.playerlib.http.ApiLive;
import com.yilan.lib.playerlib.listener.OnChatRoomStatusCallback;
import com.yilan.lib.playerlib.listener.ResponseCallback;

import io.rong.imlib.RongIMClient;


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

    @Override
    public void joinChatRoom(String liveId){
        RongIMClient.getInstance().joinChatRoom(liveId, -1, new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });
    }

    @Override
    public void quitChatRoom(String liveId) {
        RongIMClient.getInstance().quitChatRoom(liveId, new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });
    }

    @Override
    public void chatRoomStatusListener(final OnChatRoomStatusCallback callback) {
        RongIMClient.getInstance().setChatRoomActionListener(new RongIMClient.ChatRoomActionListener() {
            @Override
            public void onJoining(String s) {
                callback.onJoining(s);
            }

            @Override
            public void onJoined(String s) {
                callback.onJoined(s);
            }

            @Override
            public void onQuited(String s) {
                callback.onQuited(s);
            }

            @Override
            public void onError(String s, RongIMClient.ErrorCode errorCode) {
                callback.onStatusError();
            }
        });
    }

}
