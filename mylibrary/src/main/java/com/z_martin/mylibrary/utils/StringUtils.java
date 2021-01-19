package com.z_martin.mylibrary.utils;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtils {
    
    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        try {
            return !(value != null && !"".equalsIgnoreCase(value.trim())
                    && !"null".equalsIgnoreCase(value.trim()));
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEquals(String value1, String value2) {
        try {
            if (isEmpty(value1) && isEmpty(value2)) {
                return true;
            } else if (value1 != null && value2 != null) {
                return value1.equals(value2);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断字符串是否是邮箱
     *
     * @param email email
     * @return 字符串是否是邮箱
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(" +
                "([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断手机号字符串是否合法
     *
     * @param phoneNumber 手机号字符串
     * @return 手机号字符串是否合法
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "^1[3|4|5|6|7|8]\\d{9}$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 验证密码
     * --长度为6-18个字符 
     * --需要包含英文字母
     * --需要包含数字
     * @param password
     * @return
     */
    public static boolean isPasswordValid(String password) {
        if (!password.matches(".*\\d+.*")){
            return false;
        }
        if (!password.matches(".*[a-zA-Z]+.*")){
            return false;
        }
        if (password.length() > 18 || password.length() < 6) {
            return false;
        }
        return true;
    }
    

    /**
     * 判断字符串是否为纯数字
     *
     * @param str 字符串
     * @return 是否纯数字
     */
    public static boolean isNumber(String str) {
        try {
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * double转String,保留小数点后两位
     *
     * @param num 数据
     * @return
     */
    public static String double22String(double num) {
        try {
            //使用0.00不足位补0，#.##仅保留有效位
            return new DecimalFormat("0.00").format(num);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
    /**
     * double转String,保留小数点后一位
     *
     * @param num 数据
     * @return
     */
    public static String double21String(double num) {
        try {
            //使用0.00不足位补0，#.#仅保留有效位
            return new DecimalFormat("0.0").format(num);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 格式化单位
     *
     * @param size size 
     * @return size
     */
    public static String getFormatSize(Double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size.toString() + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     */
    public static Double getFolderSize(File file){
        Double size = 0.0;
        try {
            File[] fileList = file.listFiles();
            assert fileList != null;
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 银行卡加密
     *
     * @param bankCard 银行卡
     * @return 银行卡加密
     */
    public static String bankCard(String bankCard) {
        if (isEmpty(bankCard)) {
            return "";
        }
        int length = bankCard.length();
        int beforeLength = 4;
        int afterLength = 4;
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<length; i++) {
            if(i < beforeLength || i >= (length - afterLength)) {
                sb.append(bankCard.charAt(i));
            } else {
                sb.append(replaceSymbol);
            }
        }
        return sb.toString();
    }
}
