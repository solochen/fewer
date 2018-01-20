package com.yilan.lib.playerlib.global;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ksydemo.demo.sharelib.ShareDialog;
import com.yilan.lib.playerlib.utils.SPUtils;

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
     * 跳转app中分享
     * @param manager
     */
    public void goShare(FragmentManager manager){
        ShareDialog.newInstance().show(manager, "share_dialog");
//        DialogFragment dialog = (DialogFragment) ARouter.getInstance()
//                .build(RouterConstant.ROUTER_SHARE_DIALOG).navigation();
//        dialog.show(manager, "module_share");
    }


    /**
     * 退出APP调用，目的是释放资源等
     */
    public void exitApp() {
        ActivityCollector.removeAllActivity();
    }
}
