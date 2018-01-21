package com.yilan.lib.playerlib.activity.home.presenter;

import com.alibaba.fastjson.JSON;
import com.yilan.lib.playerlib.data.GameInfo;
import com.yilan.lib.playerlib.data.InviteCode;
import com.yilan.lib.playerlib.activity.home.model.HomeModelImpl;
import com.yilan.lib.playerlib.activity.home.model.IHomeModel;
import com.yilan.lib.playerlib.activity.home.ui.IHomeView;
import com.yilan.lib.playerlib.data.UseInviteCode;
import com.yilan.lib.playerlib.event.EBus;
import com.yilan.lib.playerlib.event.LoginEvent;
import com.yilan.lib.playerlib.http.OkGoHttp;
import com.yilan.lib.playerlib.listener.ResponseCallback;
import com.yilan.lib.playerlib.mvp.MVPBasePresenter;
import com.yilan.lib.playerlib.utils.CalculateUtils;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class HomePresenter extends MVPBasePresenter<IHomeView> {

    IHomeView mHomeView;
    IHomeModel mHomeModel;

    public HomePresenter(IHomeView view) {
        this.mHomeView = view;
        this.mHomeModel = new HomeModelImpl();
    }


    public void getInviteInfo(String uid) {
        mHomeModel.getInviteInfo(uid, new ResponseCallback() {
            @Override
            public void onSuccess(String s) {
                try {
                    InviteCode inviteCode = JSON.parseObject(s, InviteCode.class);
                    if(inviteCode.success()) {
                        mHomeView.updateInviteCode(inviteCode);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(int code, String msg) {
                respCommonError(code, msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }


    public void getGameInfo() {

        mHomeModel.getGameInfo(new ResponseCallback() {
            @Override
            public void onSuccess(String s) {
                try {
                    GameInfo gameInfo = JSON.parseObject(s, GameInfo.class);
                    if (gameInfo.success()) {
                        mHomeView.updateGameInfo(gameInfo,
                                CalculateUtils.formatBonus(gameInfo.getBonus()),
                                CalculateUtils.formatBonusUnit(gameInfo.getBonus()));

                        switch (gameInfo.getStatus()) {
                            case -1:  //准备
                                mHomeView.liveReady(gameInfo.getAd_image());
                                break;
                            case 0: //开放
                            case 1: //答题中
                                mHomeView.liveOpen();
                                break;
                            default:
                                break;
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(int code, String msg) {
                respCommonError(code, msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 使用邀请码
     *
     * @param uid
     * @param code
     */
    public void useInviteCode(long uid, String code) {
        mHomeModel.useInviteCode(String.valueOf(uid), code, new ResponseCallback() {
            @Override
            public void onSuccess(String s) {
                try {
                    UseInviteCode inviteCode = JSON.parseObject(s, UseInviteCode.class);
                    if (inviteCode.success()) {
                        mHomeView.updateInviteCode(inviteCode.getRevive_count());
                    } else {

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(int code, String msg) {
                respCommonError(code, msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

}
