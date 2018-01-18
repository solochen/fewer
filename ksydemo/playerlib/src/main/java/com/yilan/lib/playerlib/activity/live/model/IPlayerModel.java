package com.yilan.lib.playerlib.activity.live.model;

import com.yilan.lib.playerlib.listener.OnChatRoomStatusCallback;
import com.yilan.lib.playerlib.listener.ResponseCallback;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public interface IPlayerModel {

    void gameLiveEnter(String uid, String liveId, ResponseCallback callback);

    void gameLiveExit(String uid, String liveId, ResponseCallback callback);

    void getGameInfo(ResponseCallback callback);

    void sendCommentToServer(String uid, String nickname, String comment,
                        ResponseCallback callback);

    void sendAnswer(String uid, String questionId, String answerId, ResponseCallback callback);

    void joinChatRoom(String liveId);

    void quitChatRoom(String liveId);

    void chatRoomStatusListener(OnChatRoomStatusCallback callback);
}
