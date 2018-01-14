package com.yilan.lib.playerlib.utils;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class CalculateUtils {

    public static int formatBonus(int bonus){
        return bonus >= 100000 ? bonus / 10000 : bonus;
    }

    public static String formatBonusUnit(int bonus){
        return bonus >= 100000 ? "万" : "元";
    }
}
