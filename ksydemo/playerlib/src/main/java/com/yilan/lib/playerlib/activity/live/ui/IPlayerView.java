package com.yilan.lib.playerlib.activity.live.ui;


import com.yilan.lib.playerlib.data.GameInfo;
import com.yilan.lib.playerlib.data.LiveEnterInfo;
import com.yilan.lib.playerlib.data.WinnerInfo;

import java.util.List;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public interface IPlayerView {

    void updateGameInfo(GameInfo gameInfo, int displayBonus, String displayUnit);

    void onAnswerStatus();

    void playIsFinish();

    void setLiveEnterInfo(LiveEnterInfo info);

    void showErrorMsg(String msg);

    void setWatchingStatus();

    void setWinnerList(WinnerInfo info);
}
