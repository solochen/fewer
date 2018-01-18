package com.yilan.lib.playerlib.global;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yilan.lib.playerlib.data.Self;
import com.yilan.lib.playerlib.utils.SPUtils;

/**
 * Created by chenshaolong on 2018/1/15.
 */

public class UserManager {

    public static UserManager singleton;
    public Self self;

    public static UserManager getInstance() {
        if (singleton == null) {
            synchronized (UserManager.class) {
                if (singleton == null) {
                    singleton = new UserManager();
                }
            }
        }
        return singleton;
    }


    /**
     * 更新
     *
     * @param context
     * @return
     */
    public void updateUserInfo(Context context, String userInfoJson) {
        self = JSON.parseObject(userInfoJson, Self.class);
        SPUtils.put(context, SPConstant.KEY_USER_INFO, userInfoJson);
    }

    /**
     * 获取一览用户信息
     *
     * @return
     */
    public Self getSelf(Context context) {
        if (self == null) {
            self = getLocalSelf(context);
            if (self == null) {
                return new Self();
            }
        }
        return self;
    }

    private Self getLocalSelf(Context context) {
        String selfJson = (String) SPUtils.get(context, SPConstant.KEY_USER_INFO, "");
        if (TextUtils.isEmpty(selfJson)) {
            return null;
        }
        try {
            return JSON.parseObject(selfJson, Self.class);
        } catch (Exception e) {
            return null;
        }
    }


    public boolean isLogin(Context context) {
        if (self == null) {
            self = getLocalSelf(context);
        }
        return self == null ? false : true;
    }

}
