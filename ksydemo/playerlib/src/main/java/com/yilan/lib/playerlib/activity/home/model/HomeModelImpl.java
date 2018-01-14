package com.yilan.lib.playerlib.activity.home.model;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yilan.lib.playerlib.http.Urls;
import com.yilan.lib.playerlib.listener.ResponseCallback;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class HomeModelImpl implements IHomeModel{


    @Override
    public void getInviteInfo(String uid, final ResponseCallback callback) {
        OkGo.<String>get(Urls.INVITE_CODE)
                .tag(this)
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



    @Override
    public void getGameInfo(String uid, final ResponseCallback callback) {
        OkGo.<String>get(Urls.GAME_INFO)
                .tag(this)
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

}
