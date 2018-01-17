package com.yilan.lib.playerlib.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class LibToast {

    public static void showToast(Context context, CharSequence sequence){
        Toast.makeText(context, sequence, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int resId){
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }


    public static void showLongToast(Context context, CharSequence sequence){
        Toast.makeText(context, sequence, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(Context context, int resId){
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }

}
