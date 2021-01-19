package com.z_martin.mylibrary.common.language;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.LocaleList;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.RequiresApi;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2018/9/27 9:42
 * @ version: 1.0
 */
public class SupportLanguageUtil {

    private static Map<String, Locale> mSupportLanguages = new HashMap<String, Locale>(7) {{
        put(LanguageConfig.ENGLISH, Locale.ENGLISH);
        put(LanguageConfig.SIMPLIFIED_CHINESE, Locale.SIMPLIFIED_CHINESE);
//        put(LanguageConfig.TRADITIONAL_CHINESE, Locale.TRADITIONAL_CHINESE);
//        put(LanguageConfig.FRANCE, Locale.FRANCE);
//        put(LanguageConfig.GERMAN, Locale.GERMANY);
//        put(LanguageConfig.ITALIAN, Locale.ITALY);
//        put(LanguageConfig.JAPAN, Locale.JAPAN);
    }};

    /**
     * 是否支持此语言
     *
     * @param language language
     * @return true:支持 false:不支持
     */
    public static boolean isSupportLanguage(String language) {
        return mSupportLanguages.containsKey(language);
    }

    /**
     * 获取支持语言
     *
     * @param language language
     * @return 支持返回支持语言，不支持返回系统首选语言
     */
    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSupportLanguage(String language) {
        if (isSupportLanguage(language)) {
            return mSupportLanguages.get(language);
        }
        return getSystemPreferredLanguage();
    }

    /**
     * 获取系统首选语言
     *
     * @return Locale
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Locale getSystemPreferredLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }
}
