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
import com.yilan.lib.playerlib.global.AnimHelper;
import com.yilan.lib.playerlib.global.SPConstant;
import com.yilan.lib.playerlib.global.UserManager;
import com.yilan.lib.playerlib.utils.SPUtils;


@Route(path = "/player/playeractivity")
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //连接融云 param rctoken
        String RcToen = "U8I+R9DoT544ff8hWlgXqqHp1KiFvYM5y36wPsV8IH+uLS96VUqeQIcFq+fF0qw1j5GBqqxNT1zpMSNN1XSB4A==";
        String RcToken2 = "t7tF3aT5kC8h0fhIAw0MkHLnANZQjsMnW79ngAp3Qey5UJgUv3TO6Nqeop3AoiO/YplsYPe1WG720YF52cNBOg==";
        String RcTokenHonner8 = "oqrxoLkUEZBx6isjirJciaHp1KiFvYM5y36wPsV8IH9lSLvOgu8Y4N/yfnGSEcD3Q+bxxBamFWXPGUp9Kw2oCe8zriRFJEdl";
        RcSingleton.getInstance().connect(RcTokenHonner8);


    }

    public void onBtnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_player:
                Test.getInstance().setListenr(new Test.Listener() {
                    @Override
                    public void share() {
                        LoginDialog.newInstance().show(getSupportFragmentManager(), "");
                    }
                });

                HomeActivity.startActivity(this);
                break;

            case R.id.btn_init_user_data:
                UserManager.getInstance().updateUserInfo(this, SPConstant.testUserInfoJson);
                break;
            case R.id.btn_logout:
                SPUtils.clear(this);

                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
