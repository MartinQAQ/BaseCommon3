package com.z_martin.mylibrary.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.text.format.DateFormat;

import com.z_martin.mylibrary.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间工具类
 */
public class DateUtils {

    public static final long ONE_SECOND_MILLIONS = 1000;
    public static final long ONE_MINUTE_MILLIONS = 60 * ONE_SECOND_MILLIONS;
    public static final long ONE_HOUR_MILLIONS = 60 * ONE_MINUTE_MILLIONS;
    public static final long ONE_DAY_MILLIONS = 24 * ONE_HOUR_MILLIONS;
    public static final int DAY_OF_YEAR = 365;

    // 日期格式为 2016-02-03 17:04:58
    public static final String PATTERN_DATE = "yyyy年MM月dd日";
    public static final String PATTERN_DATE4 = "yyyy年MM月";
    public static final String PATTERN_DATE3 = "yyyy-MM-dd";
    public static final String PATTERN_DATE5 = "yyyy-MM";
    public static final String PATTERN_DATE2 = "MM-dd";
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final String PATTERN_TIME2 = "HH:mm";
    public static final String PATTERN_SPLIT = " ";
    public static final String PATTERN = PATTERN_DATE + PATTERN_SPLIT + PATTERN_TIME;
    public static final String PATTERN2 = PATTERN_DATE3 + PATTERN_SPLIT + PATTERN_TIME;

    public static String getShortTime(long dateLong) {
        String str;
        try {
            Date date = new Date(dateLong);
            Date curDate = new Date();

            long durTime = curDate.getTime() - date.getTime();
            int dayDiff = calculateDayDiff(date, curDate);

            if (durTime <= 10 * ONE_MINUTE_MILLIONS) {
                str = AppUtils.getContext().getString(R.string.just_now);
            } else if (durTime < ONE_HOUR_MILLIONS) {
                str = durTime / ONE_MINUTE_MILLIONS + AppUtils.getContext().getString(R.string.minutes_ago);
            } else if (dayDiff == 0) {
                str = durTime / ONE_HOUR_MILLIONS + AppUtils.getContext().getString(R.string.hours_before);
            } else if (dayDiff == -1) {
                str = AppUtils.getContext().getString(R.string.yesterday) + DateFormat.format("HH:mm", date);
            } else if (isSameYear(date, curDate) && dayDiff < -1) {
                str = DateFormat.format("MM-dd", date).toString();
            } else {
                str = DateFormat.format("yyyy-MM", date).toString();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            str = "";
        }
        return str;
    }



    /**
     * 获取日期 PATTERN_DATE 部分
     */
    public static String getDate(String date) {
        if (TextUtils.isEmpty(date) || !date.contains(PATTERN_SPLIT)) {
            return "";
        }
        return date.split(PATTERN_SPLIT)[0];
    }

    /**
     * 原有日期上累加月
     *
     * @return 累加后的日期 PATTERN_DATE 部分
     */
    public static String addMonth(String date, int moonCount) {
        //如果date为空 就用当前时间
        if (TextUtils.isEmpty(date)) {
            SimpleDateFormat df = new SimpleDateFormat(PATTERN_DATE + PATTERN_SPLIT + PATTERN_TIME);
            date = df.format(new Date());
        }
        Calendar calendar = str2calendar(date);
        calendar.add(Calendar.MONTH, moonCount);
        return getDate(calendar2str(calendar));
    }

    /**
     * 计算天数差
     */
    public static int calculateDayDiff(Date targetTime, Date compareTime) {
        boolean sameYear = isSameYear(targetTime, compareTime);
        if (sameYear) {
            return calculateDayDiffOfSameYear(targetTime, compareTime);
        } else {
            int dayDiff = 0;

            // 累计年数差的整年天数
            int yearDiff = calculateYearDiff(targetTime, compareTime);
            dayDiff += yearDiff * DAY_OF_YEAR;

            // 累计同一年内的天数
            dayDiff += calculateDayDiffOfSameYear(targetTime, compareTime);

            return dayDiff;
        }
    }

    /**
     * 计算同一年内的天数差
     */
    public static int calculateDayDiffOfSameYear(Date targetTime, Date compareTime) {
        if (targetTime == null || compareTime == null) {
            return 0;
        }

        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarDayOfYear = tarCalendar.get(Calendar.DAY_OF_YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comDayOfYear = compareCalendar.get(Calendar.DAY_OF_YEAR);

        return tarDayOfYear - comDayOfYear;
    }

    /**
     * 计算年龄
     */
    public static int calculateYearDiff(Date targetTime, Date compareTime) {
        if (targetTime == null || compareTime == null) {
            return 0;
        }

        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarYear = tarCalendar.get(Calendar.YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comYear = compareCalendar.get(Calendar.YEAR);

        return tarYear - comYear;
    }
    
    /**
     * 计算年数差
     */
    public static int calculateYearDiff(String targetTimeL) {
        if (targetTimeL == null|| targetTimeL.equals("0") || targetTimeL.equals("null") || StringUtils.isEmpty(targetTimeL)) {
            return 0;
        }
        int age = 0;
        Date targetTime = str2date(targetTimeL, PATTERN_DATE3);
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarYear = tarCalendar.get(Calendar.YEAR);

        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(targetTime);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }
    
    /**
     * 计算年数差
     */
    public static int calculateYearDiff(Date targetTime) {
        if (targetTime == null) {
            return 0;
        }
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarYear = tarCalendar.get(Calendar.YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        int comYear = compareCalendar.get(Calendar.YEAR);

        return comYear - tarYear;
    }

    /**
     * 计算月数差
     *
     * @param targetTime
     * @param compareTime
     * @return
     */
    public static int calculateMonthDiff(String targetTime, String compareTime) {
        return calculateMonthDiff(str2date(targetTime, PATTERN_DATE),
                str2date(compareTime, PATTERN_DATE));
    }

    /**
     * 计算月数差
     *
     * @param targetTime
     * @param compareTime
     * @return
     */
    public static int calculateMonthDiff(Date targetTime, Date compareTime) {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarYear = tarCalendar.get(Calendar.YEAR);
        int tarMonth = tarCalendar.get(Calendar.MONTH);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comYear = compareCalendar.get(Calendar.YEAR);
        int comMonth = compareCalendar.get(Calendar.MONTH);
        return ((tarYear - comYear) * 12 + tarMonth - comMonth);

    }

    /**
     * 是否为同一年
     */
    public static boolean isSameYear(Date targetTime, Date compareTime) {
        if (targetTime == null || compareTime == null) {
            return false;
        }

        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarYear = tarCalendar.get(Calendar.YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comYear = compareCalendar.get(Calendar.YEAR);

        return tarYear == comYear;
    }

    public static Date str2date(String str, String format) {
        Date date = null;
        try {
            if (str != null) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                date = sdf.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date str2date(String str) {
        return str2date(str, PATTERN);
    }

    public static String date2str(Date date) {
        return date2str(date, PATTERN);
    }

    public static String date2str(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(date);
    }

    public static Calendar str2calendar(String str) {
        Calendar calendar = null;
        Date date = str2date(str);
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        }
        return calendar;
    }


    public static Calendar str2calendar(String str, String format) {
        Calendar calendar = null;
        Date date = str2date(str, format);
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        }
        return calendar;
    }

    public static String calendar2str(Calendar calendar) {
        return date2str(calendar.getTime());
    }

    public static String calendar2str(Calendar calendar, String format) {
        return date2str(calendar.getTime(), format);
    }

    /**
     * 时间戳转出成 MM-dd
     *
     * @param date 时间戳
     * @return
     */
    public static String dataToMD(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(PATTERN_DATE2);
            Long time = new Long(date);
            return format.format(new Date(time * 1000));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 时间戳转出成 yyyy-mm-dd
     *
     * @param date 时间戳
     * @return
     */
    public static String dataToDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Long time = new Long(date);
            return format.format(new Date(time * 1000));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 时间戳转出成 yyyy-mm-dd
     *
     * @param date 时间戳
     * @return
     */
    public static String dataToTime(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(PATTERN_TIME2);
            Long time = new Long(date);
            return format.format(new Date(time * 1000));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * yyyy-mm-dd 转出成 date
     *
     * @param date 时间戳
     * @return
     */
    public static Date timeToData(String date) {
        try {
            java.text.DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * string 转 Calendar
     *
     * @param dateStr 时间
     * @return
     */
    public static Calendar stringToCalendar2(String dateStr) {
        try {
            java.text.DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Long lon = new Long(dateStr);
            String d = sdf.format(lon* 1000);
            Date dates = sdf.parse(d);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dates);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            Calendar calendar = Calendar.getInstance();
            return calendar;
        }
    }
    /**
     * string 转 Calendar
     *
     * @param date 时间
     * @return
     */
    public static Calendar dateToCalendar(Date date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            Calendar calendar = Calendar.getInstance();
            return calendar;
        }
    }

    /**
     * 时间转换
     *
     * @param date 时间
     * @return
     */
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    
    /**
     * 时间转换成时间戳--秒
     *
     * @param date 时间
     * @return
     */
    public static long date2Timestamp(Date date) {
        try {
            return date.getTime()/1000;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 时间转换成时间戳--秒
     *
     * @param date 时间
     * @return
     */
    public static long string2long(String date) {
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");

            Date birthday = sdf.parse(date);
            return birthday.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 时间转换成时间戳--毫秒
     * @param date 时间
     * @return
     */
    public static long date2Timestampmill(Date date) {
        try {
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**  */
    public static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }


    /**
     * 时间转换
     *
     * @param date 时间
     * @return
     */
    public static String date2Time(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }
    
    /**
     * 时间转换
     *
     * @param date 时间
     * @return
     */
    public static String date2Time(Calendar date) {//可根据需要自行截取数据显示
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_TIME2);
            return sdf.format(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 时间转换
     *
     * @param date 时间
     * @return
     */
    public static String date2String(Calendar date) {//可根据需要自行截取数据显示
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE3);
            return sdf.format(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 时间转换  Calendar转 yyyy年MM月dd日
     *
     * @param date 时间
     * @return
     */
    public static String date2String2(Calendar date) {//可根据需要自行截取数据显示
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE);
            return sdf.format(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 时间转换 Calendar转 yyyy年MM月
     *
     * @param date 时间
     * @return
     */
    public static String date2String3(Calendar date) {//可根据需要自行截取数据显示
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE4);
            return sdf.format(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 时间转换 Calendar转 yyyy-MM
     *
     * @param date 时间
     * @return
     */
    public static String date2String4(Calendar date) {//可根据需要自行截取数据显示
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE5);
            return sdf.format(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 时间转换String
     *
     * @param 
     * @return
     */
    public static String long2String(String s) {//可根据需要自行截取数据显示
        try {
          
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            long lt = new Long(s) * 1000;
            Date date = new Date(lt);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * String 转化Calendar
     *
     * @param str 时间
     * @return
     */
    public static Calendar str2Calendar(String str) {//可根据需要自行截取数据显示
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("MM-dd");
            Date date =sdf.parse(str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            return Calendar.getInstance();
        }
    }
    /**
     * String 转化Calendar
     *
     * @param str 时间
     * @return
     */
    public static Calendar str2Calendar2(String str) {//可根据需要自行截取数据显示
        try {
            SimpleDateFormat sdf= new SimpleDateFormat(PATTERN_DATE3);
            Date date =sdf.parse(str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            return Calendar.getInstance();
        }
    }
    /**
     * 获取今天的时间 yyyy-MM-dd
     * @return
     */
    public static String getToDay() {//可根据需要自行截取数据显示
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE3);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 获取今天的时间 yyyy-MM-dd
     * @return
     */
    public static String getTomorrow() {//可根据需要自行截取数据显示
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE3);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 获取今天的时间 yyyy年MM月dd日
     * @return
     */
    public static String getToDay2() {//可根据需要自行截取数据显示
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前月的时间 yyyy-MM
     * @return
     */
    public static String getToMonth() {//可根据需要自行截取数据显示
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE5);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 时间转换
     *
     * @param str 时间
     * @return
     */
    public static long string2Date2(String str) {//可根据需要自行截取数据显示
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
            Date birthday = sdf.parse(str);
            return birthday.getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 时间转换
     *
     * @param str 时间
     * @return
     */
    public static long string2Date1(String str) {//可根据需要自行截取数据显示
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = sdf.parse(str);
            return birthday.getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 时间转换
     *
     * @param date 时间
     * @return
     */
    public static String date2Time(String date) {//可根据需要自行截取数据显示
        try {
            java.text.DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 时时间戳转换--yyyy-MM-dd HH:mm:ss
     *
     * @param date 时间
     * @return
     */
    public static String date2String(long date) {//可根据需要自行截取数据显示
        try {
            java.text.DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(new Date(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 时时间戳转换--yyyy-MM-dd HH:mm
     *
     * @param date 时间
     * @return
     */
    public static String date2String6(long date) {//可根据需要自行截取数据显示
        try {
            java.text.DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(new Date(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 时时间戳转换--MM/dd
     *
     * @param date 时间
     * @return
     */
    public static String date2String3(long date) {//可根据需要自行截取数据显示
        try {
            java.text.DateFormat sdf = new SimpleDateFormat("MM/dd");
            return sdf.format(new Date(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 时时间戳转换--yyyy/MM/dd
     *
     * @param date 时间
     * @return
     */
    public static String date2String4(long date) {//可根据需要自行截取数据显示
        try {
            java.text.DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            return sdf.format(new Date(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 时间戳转换--yyyy年MM月dd日
     *
     * @param date 时间
     * @return
     */
    public static String date2String5(long date) {//可根据需要自行截取数据显示
        try {
            java.text.DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            return sdf.format(new Date(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 时时间戳转换--yyyy-MM-dd
     *
     * @param date 时间
     * @return
     */
    public static String date2String2(long date) {//可根据需要自行截取数据显示
        try {
            java.text.DateFormat sdf = new SimpleDateFormat(PATTERN_DATE3);
            return sdf.format(new Date(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 时间转换--yyyy-MM-dd HH:mm:ss
     *
     * @param date 时间
     * @return
     */
    public static Date stamp2Date(String date) {//可根据需要自行截取数据显示
        try {
            java.text.DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long time = new Long(date);
            String d = sdf.format(time* 1000);
            return sdf.parse(d);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }
    /**
     * 时间转换--yyyy-MM-dd HH:mm
     *
     * @param date 时间
     * @return
     */
    public static String date2String2(String date) {//可根据需要自行截取数据显示
        try {
            if(date.equals("0")) {
                return "";
            }
            java.text.DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Long time = new Long(date);
            return sdf.format(new Date(time * 1000));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static long getFormerlyDate(int age) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.YEAR, 0 - age);
            Date y = c.getTime();
            return y.getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     *  获取xx年前的字符串
     * @param age
     * @return
     */
    public static String getFormerlyDateStr(int age) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.YEAR, 0 - age);
            Date y = c.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(y);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *  根据年月日,获取正确的年月日
     * @param yearMonth 年月
     * @param dayStr 日
     * @return
     */
    public static String getDayByYearMonth(String yearMonth, String dayStr) {
        String yearStr = yearMonth.split("-")[0];
        String monthStr = yearMonth.split("-")[1];
        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);
        int day = Integer.parseInt(dayStr);
        if(day <= 0) {
            return "";
        } else if (month == 2 && day > 28) {
            if(year % 4 == 0) {
                return "29";
            } else {
                return "28";
            }
        } else if(day >= 31) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    return "31";
                case 2:
                    if(year % 4 == 0) {
                        return "29";
                    } else {
                        return "28";
                    }
                default:
                    return "30";
            }
        } else {
            return dayStr;
        }
    }
    
    /**
     *  根据年月日,获取正确的年月日
     * @param yearMonth 年月
     * @param dayStr 日
     * @return
     */
    public static String geYMDByYearMonth(String yearMonth, String dayStr) {
        String yearStr = yearMonth.split("-")[0];
        String monthStr = yearMonth.split("-")[1];
        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);
        int day = Integer.parseInt(dayStr);
        if(day <= 0) {
            return "";
        } else if (month == 2 && day > 28) {
            if(year % 4 == 0) {
                return yearMonth + "-29";
            } else {
                return yearMonth + "-28";
            }
        } else if(day >= 31) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    return yearMonth + "-31";
                case 2:
                    if(year % 4 == 0) {
                        return yearMonth + "-29";
                    } else {
                        return yearMonth + "-28";
                    }
                default:
                    return yearMonth + "-30";
            }
        } else {
            if(dayStr.length() == 1) {
                return yearMonth + "-0" + dayStr;
            } else {
                return yearMonth + "-" + dayStr;
            }
        }
    }

    /**
     *  date转换yyyy-MM-dd HH:mm
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String date2string(Date date) {
        try {
             
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public static Calendar get1960() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1960);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return  calendar;
    }

    /**
     * 根据秒获取分钟秒
     * @param second
     * @return
     */
    public static String getMinute(int second) {
        try {
            if (second <  0) {
                return "0:00";
            }
            int minute = second / 60;
            String minuteStr;
            if (minute < 10) {
                minuteStr = "0" + minute;
            } else {
                minuteStr = minute + "";
            }
            int s = second % 60;
            String sStr;
            if (s < 10) {
                sStr = "0" + s;
            } else {
                sStr = s + "";
            }
            return minuteStr + ":" + sStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "0:00";
        }
    }

    /**
     *  yyyy-MM-dd HH:mm 转换成 yyyy-MM-dd
     * @param time 时间
     * @return
     */
    public static String time2time(String time) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN2);
        try {
            return date2String2(format.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     *  yyyy-MM-dd HH:mm 转换成 yyyy年MM月dd日
     * @param time 时间
     * @return
     */
    public static String time2time2(String time) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN_DATE);
        try {
            return date2String2(format.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     *  yyyy-MM-dd HH:mm 转换成 yyyy-MM-dd
     * @param time 时间
     * @return
     */
    public static long getTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN2);
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     *  yyyy-MM-dd HH:mm 转换成 yyyy-MM-dd
     * @param time 时间
     * @return
     */
    public static Calendar getTime2(String time) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN2);
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(format.parse(time).getTime());
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            return Calendar.getInstance();
        }
    }
    

    public static String getTime2() {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN2);
        try {
            Calendar calendar = Calendar.getInstance();
            return format.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String str2Str(String str) {//可根据需要自行截取数据显示
        try {
            SimpleDateFormat sdf= new SimpleDateFormat(PATTERN_DATE3);
            Date date = sdf.parse(str);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            return formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
