package com.yilan.lib.playerlib.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.widget.Toast;

/**
 * Created by chenshaolong on 2018/1/13.
 */

public class AppUtils {

    private static Boolean isDebug = null;

    public static boolean isDebug() {
        return isDebug == null ? false : isDebug.booleanValue();
    }

    public static void syncIsDebug(Context context) {
        if (isDebug == null) {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            isDebug = applicationInfo != null &&
                    (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }


}
