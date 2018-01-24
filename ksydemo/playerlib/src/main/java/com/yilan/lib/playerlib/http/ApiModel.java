package com.yilan.lib.playerlib.http;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yilan.lib.playerlib.listener.ResponseCallback;

/**
 * Created by chenshaolong on 2018/1/15.
 */

public class ApiModel {

    /**
     * 获得邀请码和复活卡信息
     * @param uid
     * @param callback
     */
    public static void getInviteInfo(String uid, final ResponseCallback callback) {
        OkGo.<String>get(Urls.INVITE_CODE)
                .params("uid", uid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.onSuccess(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        callback.onError(response.code(), response.message());
                    }

                    @Override
                    public void onFinish() {
                        callback.onFinish();
                    }
                });
    }


    /**
     * 获得答题直播间信息
     * @param callback
     */
    public static void getGameInfo(final ResponseCallback callback) {
        OkGo.<String>get(Urls.GAME_INFO)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.onSuccess(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        callback.onError(response.code(), response.message());
                    }

                    @Override
                    public void onFinish() {
                        callback.onFinish();
                    }
                });
    }

    /**
     * 使用邀请码
     * @param uid
     * @param code
     * @param callback
     */
    public static void useInviteCode(String uid, String code, final ResponseCallback callback) {
        OkGo.<String>post(Urls.USE_INVITE_CODE)
                .params("uid", uid)
                .params("invite_code", code)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.onSuccess(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        callback.onError(response.code(), response.message());
                    }

                    @Override
                    public void onFinish() {
                        callback.onFinish();
                    }
                });
    }


    /**
     * 进入答题直播间
     * @param uid
     * @param liveId
     * @param callback
     */
    public static void gameLiveEnter(String uid, String liveId, final ResponseCallback callback) {
        OkGo.<String>post(Urls.GAME_LIVE_ENTER)
                .params("uid", uid)
                .params("live_id", liveId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.onSuccess(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        callback.onError(response.code(), response.message());
                    }

                    @Override
                    public void onFinish() {
                        callback.onFinish();
                    }
                });
    }


    /**
     * 退出答题直播间
     * @param uid
     * @param liveId
     * @param callback
     */
    public static void gameLiveExit(String uid, String liveId, final ResponseCallback callback) {
        OkGo.<String>post(Urls.GAME_LIVE_EXIT)
                .params("uid", uid)
                .params("live_id", liveId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.onSuccess(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        callback.onError(response.code(), response.message());
                    }

                    @Override
                    public void onFinish() {
                        callback.onFinish();
                    }
                });
    }


    /**
     * 获得答题比赛结果
     * @param uid
     * @param callback
     */
    public static void getGameLiveResult(String uid, final ResponseCallback callback) {
        OkGo.<String>get(Urls.GET_GAME_LIVE_RESULT)
                .params("uid", uid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.onSuccess(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        callback.onError(response.code(), response.message());
                    }

                    @Override
                    public void onFinish() {
                        callback.onFinish();
                    }
                });
    }


    /**
     * 提交答案
     * @param uid
     * @param questionId
     * @param answer
     * @param callback
     */
    public static void answer(String uid, String questionId, String answer, final ResponseCallback callback) {
        OkGo.<String>post(Urls.ANSWER)
                .params("uid", uid)
                .params("question_id", questionId)
                .params("answer", answer)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.onSuccess(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        callback.onError(response.code(), response.message());
                    }

                    @Override
                    public void onFinish() {
                        callback.onFinish();
                    }
                });
    }


    /**
     * 提交弹幕
     * @param uid
     * @param nickname
     * @param comment
     * @param callback
     */
    public static void comment(String uid, String nickname, String comment, final ResponseCallback callback) {
        OkGo.<String>post(Urls.COMMENT)
                .params("uid", uid)
                .params("nickname", nickname)
                .params("comment", comment)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.onSuccess(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        callback.onError(response.code(), response.message());
                    }

                    @Override
                    public void onFinish() {
                        callback.onFinish();
                    }
                });
    }
}
