package com.yilan.lib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yilan.lib.playerlib.global.RouterConstant;
import com.yilan.lib.playerlib.global.SPConstant;
import com.yilan.lib.playerlib.global.UserManager;

import java.util.List;


@Route(path = "/player/playeractivity")
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String str = "{ \"nickname\" : [\"康有\", \"124\", \"555\"]}";
        JSONObject sf = JSON.parseObject(str);
        List<String> array = JSON.parseArray(sf.getString("nickname"), String.class);


//        String daf = sf.get(0);
    }

    public void onBtnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_player:
                ARouter.getInstance().build(RouterConstant.HOME_REDIRECT_PATH)
//                        .withString(RouterConstant.HOME_REDIRECT_PARAMS_TOKEN, "chenshaolong123")
                        .navigation();
//                LivePlayerTestActivity.startActivity(this);
//                String playerUrl = "http://xdj-hdl.8686c.com/xdj-live/5798bf4c2eaae555342860b0.flv";
                break;

            case R.id.btn_init_user_data:
                UserManager.getInstance().updateUserInfo(this, SPConstant.testUserInfoJson);
                break;
        }
    }
}
