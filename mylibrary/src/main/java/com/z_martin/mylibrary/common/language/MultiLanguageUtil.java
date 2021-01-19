package com.z_martin.mylibrary.common.language;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import com.vise.log.ViseLog;
import com.z_martin.mylibrary.R;
import com.z_martin.mylibrary.utils.SpUtils;
import com.z_martin.mylibrary.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

/**
 * @ describe: 多语言工具类
 * @ author: Martin
 * @ createTime: 2018/9/27 14:15
 * @ version: 1.0
 */
public class MultiLanguageUtil {
    @SuppressLint("StaticFieldLeak")
    private static MultiLanguageUtil instance;
    private Context mContext;
    public static final String SAVE_LANGUAGE = "save_language";

    public static void init(Context mContext) {
        if (instance == null) {
            synchronized (MultiLanguageUtil.class) {
                if (instance == null) {
                    instance = new MultiLanguageUtil(mContext);
                }
            }
        }
    }

    public static MultiLanguageUtil getInstance() {
        if (instance == null) {
            throw new IllegalStateException("You must be init MultiLanguageUtil for application");
        }
        return instance;
    }

    private MultiLanguageUtil(Context context) {
        this.mContext = context;
    }

    /**
     * 设置语言
     */
    public void setConfiguration() {
        Locale targetLocale = getLanguageLocale();
        Configuration configuration = mContext.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(targetLocale);
        } else {
            configuration.locale = targetLocale;
        }
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);//语言更换生效的代码!
    }

    /**
     * 如果不是英文、简体中文，默认返回英文
     *
     * @return
     */
    private Locale getLanguageLocale() {
        String languageType = SpUtils.getString(mContext, MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_FOLLOW_SYSTEM);
        switch (languageType) {
            case LanguageType.LANGUAGE_FOLLOW_SYSTEM:
                String test = mContext.getString(R.string.test_hint);
                ViseLog.e(test);
                if (!StringUtils.isEmpty(test) && test.equals("测试")) {
                    SpUtils.putString(mContext, MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_CHINESE_SIMPLIFIED);
                    return Locale.SIMPLIFIED_CHINESE;
                } else {
                    SpUtils.putString(mContext, MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_EN);
                    return Locale.ENGLISH;
                }
//                return getSysLocale();
//            case LanguageType.LANGUAGE_EN:
//                return Locale.ENGLISH;
            case LanguageType.LANGUAGE_CHINESE_SIMPLIFIED:
                return Locale.SIMPLIFIED_CHINESE;
            default:
//                getSystemLanguage(getSysLocale());
                ViseLog.e("getLanguageLocale" + languageType + "+++++++++++++" + languageType);
                return Locale.ENGLISH;
        }
    }

    private String getSystemLanguage(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();
    }
    @SuppressLint("NewApi")
    private String getSystemLanguage2(Locale locale) {
        return locale.toString();
    }

    //以上获取方式需要特殊处理一下
    public static Locale getSysLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * 更新语言
     *
     * @param languageType 语言类型
     */
    public void updateLanguage(String languageType) {
        SpUtils.putString(mContext, MultiLanguageUtil.SAVE_LANGUAGE, languageType);
        MultiLanguageUtil.getInstance().setConfiguration();
        EventBus.getDefault().post(new OnChangeLanguageEvent(languageType));
    }

    /**
     * 获取到用户保存的语言类型
     * @return 用户保存的语言类型
     */
    public String getLanguageType() {
        String languageType = SpUtils.getString(mContext, MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_FOLLOW_SYSTEM);
        switch (languageType) {
            case LanguageType.LANGUAGE_FOLLOW_SYSTEM:
                return getSystemLanguage(getSysLocale());
//            case LanguageType.LANGUAGE_EN:
//                return LanguageType.LANGUAGE_EN;
            case LanguageType.LANGUAGE_CHINESE_SIMPLIFIED:
                return LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
            default:
                return LanguageType.LANGUAGE_EN;
        }
    }
    /**
     * 获取到用户保存的语言类型
     * @return 用户保存的语言类型
     */
    public String getLanguageType2() {
        String languageType = SpUtils.getString(mContext, MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_FOLLOW_SYSTEM);
        switch (languageType) {
            case LanguageType.LANGUAGE_FOLLOW_SYSTEM:
                return getSystemLanguage2(getSysLocale());
//            case LanguageType.LANGUAGE_EN:
//                return LanguageType.LANGUAGE_EN;
            case LanguageType.LANGUAGE_CHINESE_SIMPLIFIED:
                return LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
            default:
                return LanguageType.LANGUAGE_EN;
        }
    }
    
    /**
     * 获取系统的语言类型--默认为英文
     * @return 系统的语言类型
     */
    private static String getSystemLanguageType() {
        Locale locale = getSysLocale();
        String languageType = locale.getLanguage();
        switch (languageType) {
            case LanguageType.LANGUAGE_CHINESE_SIMPLIFIED:
                return LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
            case LanguageType.LANGUAGE_EN:
                return LanguageType.LANGUAGE_EN;
            default:
                return LanguageType.LANGUAGE_EN;
        }
    }

    /**
     * 获取系统的语言类型--默认为英文
     * @return 系统的语言类型
     */
    public static String getSystemLanguage(){
        return getSystemLanguageType();
    }

    public static Context attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context);
        } else {
            MultiLanguageUtil.getInstance().setConfiguration();
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getInstance().getLanguageLocale();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }
}
