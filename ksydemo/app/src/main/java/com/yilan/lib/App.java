package com.yilan.lib;

import android.app.Application;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yilan.lib.playerlib.http.OkGoHttp;
import com.yilan.lib.playerlib.utils.AppUtils;

/**
 * Created by chenshaolong on 2018/1/13.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppUtils.syncIsDebug(this);

        if(AppUtils.isDebug()){
            ARouter.openLog();
            ARouter.openDebug();
            Toast.makeText(this, "debug", Toast.LENGTH_LONG).show();
        }
        ARouter.init(this);

        OkGoHttp.getInstance(this);

    }
}
