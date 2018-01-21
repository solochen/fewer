package com.yilan.lib.playerlib.http;

import com.yilan.lib.playerlib.utils.AppUtils;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class Urls {

    private static String SERVER;

    static {
        if(AppUtils.isDebug()) {
            SERVER = "http://192.168.0.26";
        } else {
            SERVER = "http://192.168.0.26";
        }
    }


    //获得邀请码和复活卡信息
    public static final String INVITE_CODE = SERVER + "/get_invite_info";

    //获得答题直播间信息
    public static final String GAME_INFO = SERVER + "/get_game_live";

    //使用邀请码
    public static final String USE_INVITE_CODE = SERVER + "/use_invite_code";

    //进入答题直播间
    public static final String GAME_LIVE_ENTER = SERVER + "/game_live_enter";

    //退出答题直播间
    public static final String GAME_LIVE_EXIT = SERVER + "/game_live_exit";

    //获得答题比赛结果
    public static final String GET_GAME_LIVE_RESULT = SERVER + "/get_game_live_result";

    //提交答案
    public static final String ANSWER = SERVER + "/answer";

    //提交弹幕
    public static final String COMMENT = SERVER + "/comment";

    //获得上期排行榜
    public static final String GET_LAST_RANK = SERVER + "/get_last_rank";

    //获得总榜
    public static final String GET_TOTAL_RANK = SERVER + "/get_total_rank";


    //获得用户信息
    public static final String GET_USER_INFO = SERVER + "/get_user_info";

    //申请提现
    public static final String WITHDRAW = SERVER + "/withdraw";



}
