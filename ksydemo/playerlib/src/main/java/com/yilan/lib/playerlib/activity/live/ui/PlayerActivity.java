package com.yilan.lib.playerlib.activity.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.data.GameInfo;
import com.yilan.lib.playerlib.event.EBus;
import com.yilan.lib.playerlib.event.LiveEvent;
import com.yilan.lib.playerlib.global.BaseActivity;
import com.yilan.lib.playerlib.utils.HideUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by chenshaolong on 2018/1/15.
 */

public class PlayerActivity extends BaseActivity{

    private static final String KEY_URL = "liveUrl";
    private static final String KEY_GAME_INFO = "game_info";


    public static void startActivity(Context context, GameInfo info, String url) {
        Intent mIntent = new Intent(context, PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URL, url);
        bundle.putSerializable(KEY_GAME_INFO, info);
        mIntent.putExtras(bundle);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
    }

    @Override
    public int getLayout() {
        return R.layout.act_lib_player;
    }

    @Override
    public void initView() {

    }


    @Override
    public void onCreate() {
        HideUtil.init(this);
        EBus.register(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String liveUrl = getIntent().getStringExtra(KEY_URL);
        GameInfo gameInfo = (GameInfo) getIntent().getSerializableExtra(KEY_GAME_INFO);
        FragmentTransaction mTrx = getSupportFragmentManager().beginTransaction();
        PlayerKitFragment fragment = getPlayerFragment(liveUrl);
        mTrx.add(R.id.lib_player_flmain, fragment);
        mTrx.show(fragment);
        mTrx.commitAllowingStateLoss();

        PlayerLogicDialogFragment.newInstance(gameInfo)
                .show(getSupportFragmentManager(), "PlayerLogicDialogFragment");
    }


    private PlayerKitFragment getPlayerFragment(String url) {
        PlayerKitFragment mPlayerFragment = new PlayerKitFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URL, url);
        mPlayerFragment.setArguments(bundle);
        return mPlayerFragment;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LiveEvent e){
        if(e.getType() == LiveEvent.EVENT_LIVE_FINISH){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EBus.unregister(this);
    }
}
