package com.yilan.lib.playerlib.global;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class AppManager {

    public static AppManager singleton;

    public static AppManager getInstance() {
        if (singleton == null) {
            synchronized (AppManager.class) {
                if (singleton == null) {
                    singleton = new AppManager();
                }
            }
        }
        return singleton;
    }


    /**
     * 跳转app中登录
     * @param manager
     */
    public void goLogin(FragmentManager manager){
        DialogFragment dialog = (DialogFragment) ARouter.getInstance()
                .build(RouterConstant.ROUTER_LOGIN_DIALOG).navigation();
        dialog.show(manager, "module_login");
    }


    /**
     * 退出APP调用，目的是释放资源等
     */
    public void exitApp() {
        ActivityCollector.removeAllActivity();
    }
}
