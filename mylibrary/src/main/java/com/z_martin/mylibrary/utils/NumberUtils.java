package com.z_martin.mylibrary.utils;

import java.text.DecimalFormat;

/**
 * @ describe: 
 * @ author: Martin
 * @ createTime: 2018/9/3 17:56
 * @ version: 1.0
 */
public class NumberUtils {
    
    /**
     *  获取6位随机字符串
     * @return
     */
    public static String get6RandomNumber(){
        return (int)((Math.random()*9+1)*100000) + "";
    }


    /**
     *  string 转 int
     * @param str
     * @return
     */
    public static String string2Number(String str) {
        try {
            int nub = Integer.parseInt(str);
            return nub + "";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "-1";
        }
    }


    /**
     *  去除加钱后为0的小数
     * @param str
     * @return
     */
    public static String money2Number(String str) {
        try {
            if(str.substring(str.indexOf(".") + 1, str.length()).equals("00")) {
                return str.substring(0, str.length() - 3);
            } else if (str.substring(str.indexOf(".") + 1, str.length()).equals("0")) {
                return str.substring(0, str.length() - 2);
            } else {
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
    
    /**
     *  字符串转换成钱并去除加钱后为0的小数
     * @param strs
     * @return
     */
    public static String str2Double(double strs) {
        try {
//            ViseLog.e(strs);
            DecimalFormat df = new DecimalFormat("##.00");
            String str = df.format(strs);
            if(str.substring(0, 1).equals(".")) {
                str = "0" + str;
            }
            if (str.substring(str.indexOf(".") + 1, str.length()).equals("00")) {
                return str.substring(0, str.length() - 3);
            } else if (str.substring(str.indexOf(".") + 2, str.length()).equals("0")) {
                return str.substring(0, str.length() - 1);
            } else {
//                ViseLog.e(str);
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    public static String dayOrMonth2Str(int day){
        try {
            if(day < 10) {
                return "0" + day;
            } else {
                return day+"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "01";
        }
    }
}
