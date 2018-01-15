package com.yilan.lib.playerlib.activity.live.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.activity.live.presenter.PlayerPresenter;
import com.yilan.lib.playerlib.data.GameInfo;
import com.yilan.lib.playerlib.data.InviteCode;
import com.yilan.lib.playerlib.mvp.MVPBaseFragment;

/**
 * Created by chenshaolong on 2018/1/15.
 */

public class PlayerLogicTopFragment extends MVPBaseFragment<IPlayerView, PlayerPresenter> implements
IPlayerView{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frmt_lib_player_logic_toplayer, container, false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    protected PlayerPresenter createPresenter() {
        return new PlayerPresenter(this);
    }

    @Override
    public void updateInviteCode(InviteCode inviteCode) {

    }

    @Override
    public void updateGameInfo(GameInfo gameInfo, int displayBonus, String displayUnit) {

    }

    @Override
    public void liveReady(String adImage) {

    }

    @Override
    public void liveOpen() {

    }
}
