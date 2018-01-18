package com.yilan.lib.playerlib.activity.live.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yilan.lib.playerlib.activity.live.model.IPlayerModel;
import com.yilan.lib.playerlib.activity.live.model.PlayerModelImpl;
import com.yilan.lib.playerlib.activity.live.ui.IPlayerView;
import com.yilan.lib.playerlib.data.GameInfo;
import com.yilan.lib.playerlib.data.LiveEnterInfo;
import com.yilan.lib.playerlib.http.OkGoHttp;
import com.yilan.lib.playerlib.listener.OnChatRoomStatusCallback;
import com.yilan.lib.playerlib.listener.ResponseCallback;
import com.yilan.lib.playerlib.mvp.MVPBasePresenter;
import com.yilan.lib.playerlib.utils.CalculateUtils;
import com.yilan.lib.playerlib.utils.LibToast;

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

    public void getGameLiveInfo(){

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
            if(gameInfo.getStatus() == 0){        //开放
                mPlayerView.updateGameInfo(gameInfo,
                        CalculateUtils.formatBonus(gameInfo.getBonus()),
                        CalculateUtils.formatBonusUnit(gameInfo.getBonus()));
            } else if(gameInfo.getStatus() == 1){ //答题中
                mPlayerView.onAnswerStatus();
            }
        } catch (Exception e) {

        }

//        mPlayerModel.getGameInfo(new ResponseCallback() {
//            @Override
//            public void onSuccess(String s) {
//                try {
//                    GameInfo gameInfo = JSON.parseObject(s, GameInfo.class);
//                    if(gameInfo.getStatus() == 0){        //开放
//                        mPlayerView.updateGameInfo(gameInfo,
//                                CalculateUtils.formatBonus(gameInfo.getBonus()),
//                                CalculateUtils.formatBonusUnit(gameInfo.getBonus()));
//                    } else if(gameInfo.getStatus() == 1){ //答题中
//                        mPlayerView.onAnswerStatus();
//                    }
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


    public void gameLiveEnter(String uid, String liveId) {

        mPlayerModel.gameLiveEnter(uid, liveId, new ResponseCallback() {
            @Override
            public void onSuccess(String s) {
                try {
                    LiveEnterInfo info = JSON.parseObject(s, LiveEnterInfo.class);
                    if(info.success()){
                        if(info.getIn_play() == 0){  //0 比赛结束需退出直播间； 1 开放中
                            mPlayerView.playIsFinish();
                        }
                        mPlayerView.setLiveEnterInfo(info);
                    } else {
                        // error
                    }
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


    public void gameLiveExit(String uid, String liveId) {

        mPlayerModel.gameLiveExit(uid, liveId, new ResponseCallback() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFinish() {

            }
        });

    }

    public void sendCMTToServer(String uid, String nickname, String comment){
        mPlayerModel.sendCommentToServer(uid, nickname, comment, new ResponseCallback() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }


    public void sendAnswer(String uid, int questionId, int answerId){
        mPlayerModel.sendAnswer(uid, String.valueOf(questionId), String.valueOf(answerId), new ResponseCallback() {
            @Override
            public void onSuccess(String s) {
                try {
                    JSONObject object = JSON.parseObject(s);
                    if (object.getInteger("code") != 0) {
                        mPlayerView.setWatchingStatus();
                        mPlayerView.showErrorMsg(object.getString("message"));
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onError(int code, String msg) {
                if (code != 200) {
                    mPlayerView.setWatchingStatus();
                    mPlayerView.showErrorMsg(msg);
                }
            }

            @Override
            public void onFinish() {

            }
        });
    }


    public void joinChatRoom(String liveId){
        mPlayerModel.joinChatRoom(liveId);
    }

    public void quitChatRoom(String liveId){
        mPlayerModel.quitChatRoom(liveId);
    }

    public void chatRoomStatusListener(){
        mPlayerModel.chatRoomStatusListener(new OnChatRoomStatusCallback() {
            @Override
            public void onJoining(String s) {

            }

            @Override
            public void onJoined(String s) {

            }

            @Override
            public void onQuited(String s) {

            }

            @Override
            public void onStatusError() {

            }
        });
    }

}
