package com.yilan.lib.playerlib.activity.home.ui;

import com.yilan.lib.playerlib.data.GameInfo;
import com.yilan.lib.playerlib.data.InviteCode;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public interface IHomeView {

    void updateInviteCode(InviteCode inviteCode);

    void updateGameInfo(GameInfo gameInfo, int displayBonus, String displayUnit);

    void liveReady(String adImage);

    void liveOpen();

}
