package com.yilan.lib.playerlib.utils;

import android.content.Context;
import android.text.Html;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class StringsUtils {

    /**
     * string.xml 字符串格式化
     * @param context
     * @param res
     * @param val
     * @return
     */
    public static CharSequence formateStr(Context context, int res, Object... val) {
        String resultsTextFormat = context.getResources().getString(res);
        String resultsText = String.format(resultsTextFormat,   val);
        return Html.fromHtml(resultsText);
    }

}
