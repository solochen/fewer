package com.yilan.lib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yilan.lib.playerlib.global.RouterHelper;

@Route(path = "/player/playeractivity")
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onBtnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_player:
                ARouter.getInstance().build(RouterHelper.HOME_REDIRECT_PATH)
                        .withString(RouterHelper.HOME_REDIRECT_PARAMS_TOKEN, "chenshaolong123")
                        .navigation();


//                LivePlayerTestActivity.startActivity(this);
//                AnswerDialog.newInstance().show(getSupportFragmentManager(), "answerdialog");
//                String playerUrl = "http://xdj-hdl.8686c.com/xdj-live/5798bf4c2eaae555342860b0.flv";
//                PlayerActivity.startActivity(this, playerUrl);
                break;
        }
    }
}
