package com.yilan.lib.playerlib.http;

import com.yilan.lib.playerlib.utils.AppUtils;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class Urls {

    private static String SERVER;

    static {
        if(AppUtils.isDebug()) {
            SERVER = "";
        } else {
            SERVER = "";
        }
    }


    //获得邀请码和复活卡信息
    public static final String INVITE_CODE = SERVER + "/get_invite_info";

    //获得答题直播间信息
    public static final String GAME_INFO = SERVER + "/get_game_info";

}
