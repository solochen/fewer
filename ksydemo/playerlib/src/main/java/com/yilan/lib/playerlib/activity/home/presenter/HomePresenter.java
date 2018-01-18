package com.yilan.lib.playerlib.activity.home.presenter;

import com.alibaba.fastjson.JSON;
import com.yilan.lib.playerlib.data.GameInfo;
import com.yilan.lib.playerlib.data.InviteCode;
import com.yilan.lib.playerlib.activity.home.model.HomeModelImpl;
import com.yilan.lib.playerlib.activity.home.model.IHomeModel;
import com.yilan.lib.playerlib.activity.home.ui.IHomeView;
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
                    mHomeView.updateInviteCode(inviteCode);
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


    public void getGameInfo() {
        String infoStr = "{\n" +
                "  \"status\" : 0,\n" +
                "  \"bonus\" : 100000,\n" +
                "  \"game_date\" : \"浠婂ぉ\",\n" +
                "  \"game_time\" : \"12:30\",\n" +
                "  \"ad_image\" : \"http://p1.pstatp.com/thumb/2bd50007c29a7cc54e09\",\n" +
                "  \"live\" : {\n" +
                "    \"live_id\" : \"1231\",\n" +
                "    \"live_stream\" : {\n" +
                "      \"ha_stream\" : \"13241358\",\n" +
                "      \"default_res\" : \"high\",\n" +
                "      \"default_buffer_ms\" : 2000,\n" +
                "      \"avformat\" : \"FLV\",\n" +
                "      \"main_list\" : {\n" +
                "        \"high\" : \"http://pull-l3-spe.ixigua.com/live/13241358.flv\",\n" +
                "        \"medium\" : \"http://pull-l3-spe.ixigua.com/live/13241358_480p.flv\",\n" +
                "        \"low\" : \"http://pull-l3-spe.ixigua.com/live/13241358_360p.flv\",\n" +
                "        \"onlyaudio\" : \"http://pull-l3-spe.ixigua.com/live/13241358.flv?onlyaudio=1\"\n" +
                "      },\n" +
                "      \"backup_list\" : { }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        try {
            GameInfo gameInfo = JSON.parseObject(infoStr, GameInfo.class);

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

        } catch (Exception e) {

        }

//        mHomeModel.getGameInfo(new ResponseCallback() {
//            @Override
//            public void onSuccess(String s) {
//                try {
//                    GameInfo gameInfo = JSON.parseObject(s, GameInfo.class);
//
//                    mHomeView.updateGameInfo(gameInfo,
//                            CalculateUtils.formatBonus(gameInfo.getBonus()),
//                            CalculateUtils.formatBonusUnit(gameInfo.getBonus()));
//
//                    switch (gameInfo.getStatus()) {
//                        case -1:  //准备
//                            mHomeView.liveReady(gameInfo.getAd_image());
//                            break;
//                        case 0: //开放
//                        case 1: //答题中
//                            mHomeView.liveOpen();
//                            break;
//                        default :
//                            break;
//                    }
//
//                } catch (Exception e) {
//
//                }
//            }
//
//            @Override
//            public void onError(int code, String msg) {
//                if (code == OkGoHttp.CODE_TOKEN_VALID) {
//
//                }
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });
    }


}
