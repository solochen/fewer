package com.yilan.lib.playerlib.utils;


import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

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


    /**
     * 截取小数点后N位数， 不四舍五入
     * @param d
     * @param maxDigit
     * @return
     */
    public static double formatDecimal(double d, int maxDigit) {
        DecimalFormatSymbols formatSymbols = DecimalFormatSymbols.getInstance(Locale.US);
        formatSymbols.setDecimalSeparator('.');
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(maxDigit);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        formater.setDecimalFormatSymbols(formatSymbols);
        return Double.valueOf(formater.format(d));
    }


}
