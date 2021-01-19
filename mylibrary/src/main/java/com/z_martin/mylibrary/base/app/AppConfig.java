package com.z_martin.mylibrary.base.app;

import android.app.Activity;

import com.z_martin.mylibrary.utils.AppUtils;
import com.z_martin.mylibrary.utils.SpUtils;


/**
 * @ describe: 统一设置请求工具类
 * @ author: Martin
 * @ createTime: 2018/10/20 22:26
 * @ version: 1.0
 */
public class AppConfig {
    /** 是否初始化 */
    private static boolean isInit = false;
    /** baseUrl */
    private static String mBaseUrl;
    /** 图片baseUrl */
    private static String mPhotoUrl;
    /** 默认的启动页面 */
    private static Class<? extends Activity> mSplashActivity;
    /** token验证失败跳转的页面 */
    private static Class<? extends Activity> mTokenErrorActivity;
    /** 请求数据成功状态码 */
    private static int mTokenErrorStatus;
    /** 请求数据成功状态码 */
    private static int mTokenErrorStatus2;
    /** token错误状态码 */
    private static int mRequestSuccessStatus;

    public static final String AUTHORIZATION = "authorization";

    public static final String AUTHORIZATION_FINAL = "authorization_final";
    private static int authorizationType = 0;
    
    private static boolean mIsOpen = false;
    
    /** 是否显示log */
    private static boolean mIsShowLog = false;
    
    private AppConfig(){
    }
    

    /**
     *  初始化BaseCommon工具类
     * @param tokenErrorActivity token验证失败跳转的页面
     * @param baseUrl 请求baseUrl
     * @param requestSuccessStatus 请求数据成功状态码
     * @param tokenErrorStatus token错误状态码
     */
    public static void init(Class<? extends Activity> splashActivity, Class<? extends Activity> tokenErrorActivity, 
                            String baseUrl, String photoUrl, int requestSuccessStatus, int tokenErrorStatus, int tokenErrorStatus2) {
        init(false, splashActivity, tokenErrorActivity, baseUrl, photoUrl, requestSuccessStatus, tokenErrorStatus, tokenErrorStatus2);
    }
    
    /**
     *  初始化BaseCommon工具类
     * @param tokenErrorActivity token验证失败跳转的页面
     * @param baseUrl 请求baseUrl
     * @param requestSuccessStatus 请求数据成功状态码
     * @param tokenErrorStatus token错误状态码
     */
    public static void init(boolean isOpen, Class<? extends Activity> splashActivity, Class<? extends Activity> tokenErrorActivity, 
                            String baseUrl, String photoUrl, int requestSuccessStatus, int tokenErrorStatus, int tokenErrorStatus2) {
        init(isOpen, false, splashActivity, tokenErrorActivity, baseUrl, photoUrl, requestSuccessStatus, tokenErrorStatus, tokenErrorStatus2);
    }
    /**
     *  初始化BaseCommon工具类
     * @param tokenErrorActivity token验证失败跳转的页面
     * @param baseUrl 请求baseUrl
     * @param requestSuccessStatus 请求数据成功状态码
     * @param tokenErrorStatus token错误状态码
     */
    public static void init(boolean isOpen, boolean isShowLog, Class<? extends Activity> splashActivity, Class<? extends Activity> tokenErrorActivity, 
                            String baseUrl, String photoUrl, int requestSuccessStatus, int tokenErrorStatus, int tokenErrorStatus2) {
        mIsOpen = isOpen;
        mIsShowLog = isShowLog;
        isInit = true;
        mSplashActivity = splashActivity;
        mTokenErrorActivity = tokenErrorActivity;
        mBaseUrl = baseUrl;
        mPhotoUrl = photoUrl;
        mRequestSuccessStatus = requestSuccessStatus;
        mTokenErrorStatus = tokenErrorStatus;
        mTokenErrorStatus2 = tokenErrorStatus2;
    }

    public static void setUrl(String baseUrl, String photoUrl) {
        mBaseUrl = baseUrl;
        mPhotoUrl = photoUrl;
    }

    public static void setAuthorization(String authorization) {
        SpUtils.putString(AppUtils.getContext(), AUTHORIZATION, authorization);
    }

    public static String getAuthorization() {
        return SpUtils.getString(AppUtils.getContext(), AUTHORIZATION, "");
    }


    public static void setAuthorizationFinal(String authorizationFinal) {
        SpUtils.putString(AppUtils.getContext(), AUTHORIZATION_FINAL, authorizationFinal);
    }

    public static String getAuthorizationFinal() {
        return SpUtils.getString(AppUtils.getContext(), AUTHORIZATION_FINAL, "");
    }

    /**
     *  获取token验证失败跳转的页面
     */
    public static boolean isOpen() {
        if(!isInit)
            throw new RuntimeException("AppConfig没有初始化");
        return mIsOpen;
    }
    
    /**
     *  获取token验证失败跳转的页面
     */
    public static boolean isShowLog() {
        if(!isInit)
            throw new RuntimeException("AppConfig没有初始化");
        return mIsShowLog;
    }
    
    /**
     *  获取token验证失败跳转的页面
     */
    public static Class getSplashActivity() {
        if(!isInit)
            throw new RuntimeException("AppConfig没有初始化");
        return mSplashActivity;
    }
    
    /**
     *  获取token验证失败跳转的页面
     */
    public static Class getTokenErrorActivity() {
        if(!isInit)
            throw new RuntimeException("AppConfig没有初始化");
        return mTokenErrorActivity;
    }

    /**
     *  获取 baseUrl
     */
    public static String getBaseUrl() {
        if(!isInit)
            throw new RuntimeException("AppConfig没有初始化");
        return mBaseUrl;
    }

    /**
     *  获取 baseUrl
     */
    public static String getPhotoUrl() {
        if(!isInit)
            throw new RuntimeException("AppConfig没有初始化");
        return mPhotoUrl;
    }

    /**
     *  获取token错误状态码
     */
    public static int getTokenErrorStatus() {
        if(!isInit)
            throw new RuntimeException("AppConfig没有初始化");
        return mTokenErrorStatus;
    }
    /**
     *  获取token错误状态码
     */
    public static int getTokenErrorStatus2() {
        if(!isInit)
            throw new RuntimeException("AppConfig没有初始化");
        return mTokenErrorStatus2;
    }

    /**
     *  获取请求数据成功状态码
     */
    public static int getRequestSuccessStatus() {
        if(!isInit)
            throw new RuntimeException("AppConfig没有初始化");
        return mRequestSuccessStatus;
    }

    public static int getAuthorizationType() {
        return authorizationType;
    }

    public static void setAuthorizationType(int authorizationType) {
        AppConfig.authorizationType = authorizationType;
    }
}
