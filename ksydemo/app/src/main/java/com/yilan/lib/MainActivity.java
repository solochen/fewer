package com.yilan.lib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yilan.lib.playerlib.RongCloud.RcSingleton;
import com.yilan.lib.playerlib.activity.home.ui.HomeActivity;
import com.yilan.lib.playerlib.global.AppManager;
import com.yilan.lib.playerlib.global.RouterConstant;
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
        RcSingleton.getInstance().connect(RcToen);
    }

    public void onBtnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_player:
//                ARouter.getInstance().build(RouterConstant.HOME_REDIRECT_PATH)
////                        .withString(RouterConstant.HOME_REDIRECT_PARAMS_TOKEN, "chenshaolong123")
//                        .navigation();
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
