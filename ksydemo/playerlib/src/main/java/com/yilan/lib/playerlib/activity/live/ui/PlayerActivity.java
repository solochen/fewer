package com.yilan.lib.playerlib.activity.live.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.global.BaseActivity;

/**
 * Created by chenshaolong on 2018/1/15.
 */

public class PlayerActivity extends BaseActivity{

    private static final String KEY_URL = "liveUrl";

    @Override
    public int getLayout() {
        return R.layout.act_lib_player;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onCreate() {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String url = getIntent().getStringExtra(KEY_URL);

        FragmentTransaction mTrx = getSupportFragmentManager().beginTransaction();
        PlayerKitFragment fragment = getPlayerFragment(url);
        mTrx.add(R.id.lib_player_flmain, fragment);
        mTrx.show(fragment);
        mTrx.commitAllowingStateLoss();

        PlayerLogicDialogFragment.newInstance(url).show(getSupportFragmentManager(), "PlayerLogicDialogFragment");

    }


    private PlayerKitFragment getPlayerFragment(String url) {
        PlayerKitFragment mPlayerFragment = new PlayerKitFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URL, url);
        mPlayerFragment.setArguments(bundle);
        return mPlayerFragment;
    }
}
