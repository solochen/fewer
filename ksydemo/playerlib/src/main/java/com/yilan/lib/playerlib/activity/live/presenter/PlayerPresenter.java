package com.yilan.lib.playerlib.activity.live.presenter;

import com.alibaba.fastjson.JSON;
import com.yilan.lib.playerlib.activity.live.model.IPlayerModel;
import com.yilan.lib.playerlib.activity.live.model.PlayerModelImpl;
import com.yilan.lib.playerlib.activity.live.ui.IPlayerView;
import com.yilan.lib.playerlib.http.OkGoHttp;
import com.yilan.lib.playerlib.listener.ResponseCallback;
import com.yilan.lib.playerlib.data.GameInfo;
import com.yilan.lib.playerlib.data.InviteCode;
import com.yilan.lib.playerlib.mvp.MVPBasePresenter;
import com.yilan.lib.playerlib.utils.CalculateUtils;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class PlayerPresenter extends MVPBasePresenter<IPlayerView> {

    IPlayerView mPlayerView;
    IPlayerModel mPlayerModel;

    public PlayerPresenter(IPlayerView view) {
        this.mPlayerView = view;
        this.mPlayerModel = new PlayerModelImpl();
    }


    public void getInviteInfo(String uid) {
        mPlayerModel.getInviteInfo(uid, new ResponseCallback() {
            @Override
            public void onSuccess(String s) {
                try {
                    InviteCode inviteCode = JSON.parseObject(s, InviteCode.class);
                    mPlayerView.updateInviteCode(inviteCode);
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(int code, String msg) {
                if (code == OkGoHttp.CODE_TOKEN_VALID) {

                }
            }

            @Override
            public void onFinish() {

            }
        });
    }


    public void getGameInfo(String uid) {
        mPlayerModel.getGameInfo(uid, new ResponseCallback() {
            @Override
            public void onSuccess(String s) {
                try {
                    GameInfo gameInfo = JSON.parseObject(s, GameInfo.class);
                    switch (gameInfo.getStatus()) {
                        case -1:  //准备
                            mPlayerView.liveReady(gameInfo.getAd_image());
                            break;
                        case 0: //开放
                        case 1: //答题中
                            mPlayerView.liveOpen();
                            break;
                        default :
                            break;
                    }

                    mPlayerView.updateGameInfo(gameInfo,
                            CalculateUtils.formatBonus(gameInfo.getBonus()),
                            CalculateUtils.formatBonusUnit(gameInfo.getBonus()));

                } catch (Exception e) {

                }
            }

            @Override
            public void onError(int code, String msg) {
                if (code == OkGoHttp.CODE_TOKEN_VALID) {

                }
            }

            @Override
            public void onFinish() {

            }
        });
    }


}
