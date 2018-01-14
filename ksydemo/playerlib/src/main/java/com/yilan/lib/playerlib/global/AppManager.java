package com.yilan.lib.playerlib.global;

import com.yilan.lib.playerlib.http.OkGoHttp;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class AppManager {

    public static AppManager singleton;

    public static AppManager getInstance() {
        if (singleton == null) {
            synchronized (OkGoHttp.class) {
                if (singleton == null) {
                    singleton = new AppManager();
                }
            }
        }
        return singleton;
    }

    /**
     * 退出APP调用，目的是释放资源等
     */
    public void exitApp(){
        ActivityCollector.removeAllActivity();
    }
}
