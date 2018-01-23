package com.yilan.lib;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yilan.lib.playerlib.RongCloud.RcSingleton;
import com.yilan.lib.playerlib.Test;
import com.yilan.lib.playerlib.activity.home.ui.HomeActivity;
import com.yilan.lib.playerlib.activity.live.dialog.WinDialog;
import com.yilan.lib.playerlib.activity.live.ui.PlayerActivity;
import com.yilan.lib.playerlib.global.AnimHelper;
import com.yilan.lib.playerlib.global.SPConstant;
import com.yilan.lib.playerlib.global.UserManager;
import com.yilan.lib.playerlib.utils.SPUtils;
import com.yilan.lib.playerlib.utils.explosion.ExplosionField;


@Route(path = "/player/playeractivity")
public class MainActivity extends AppCompatActivity {

    private ExplosionField mExplosionField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mExplosionField = ExplosionField.attach2Window(this);

        //连接融云 param rctoke
        //5330362952
        String RcMeiZu = "U8I+R9DoT544ff8hWlgXqqHp1KiFvYM5y36wPsV8IH+uLS96VUqeQIcFq+fF0qw1j5GBqqxNT1zpMSNN1XSB4A==";

        //5330362954
        String RcMI5 = "t7tF3aT5kC8h0fhIAw0MkHLnANZQjsMnW79ngAp3Qey5UJgUv3TO6Nqeop3AoiO/YplsYPe1WG720YF52cNBOg==";

        //5330362953
        String RcTokenHonner8 = "oqrxoLkUEZBx6isjirJciaHp1KiFvYM5y36wPsV8IH9lSLvOgu8Y4N/yfnGSEcD3Q+bxxBamFWXPGUp9Kw2oCe8zriRFJEdl";
        RcSingleton.getInstance().connect(RcMI5);


    }

    public void onBtnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_player:
                HomeActivity.startActivity(this);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        PlayerActivity.startActivity(MainActivity.this, null, "http://livetest01.any2cn.com/live/s001.flv");
//                    }
//                }, 500);

                break;

            case R.id.btn_init_user_data:
                UserManager.getInstance().updateUserInfo(this, SPConstant.testUserInfoJson);
                break;
            case R.id.btn_logout:
                SPUtils.clear(this);

                break;

            case R.id.btn_anim_test:
                WinDialog.newInstance(1).show(getSupportFragmentManager(), "");
                mExplosionField.explode(v);
                v.setOnClickListener(null);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
