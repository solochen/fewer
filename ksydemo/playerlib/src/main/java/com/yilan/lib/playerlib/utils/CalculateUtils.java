package com.yilan.lib.playerlib.utils;


import android.content.Context;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class CalculateUtils {

    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;


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


    /**
     * 四舍五入格式化下取整，保留N 位
     * @param d
     * @return
     */
    public static double formatHalfDown(double d, int maxDigit) {
        DecimalFormatSymbols formatSymbols = DecimalFormatSymbols.getInstance(Locale.US);
        formatSymbols.setDecimalSeparator('.');
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(maxDigit);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.HALF_DOWN);
        formater.setDecimalFormatSymbols(formatSymbols);
        return Double.valueOf(formater.format(d));
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 加法
     * @param v1
     * @param v2
     * @return
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 减法
     * @param v1
     * @param v2
     * @return
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 乘法
     * @param v1
     * @param v2
     * @return
     */
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }


    public static double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }


    public static double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static double round(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


}
