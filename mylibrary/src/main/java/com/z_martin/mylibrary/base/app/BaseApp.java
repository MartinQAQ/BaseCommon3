package com.z_martin.mylibrary.base.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.vise.log.ViseLog;
import com.vise.log.inner.LogcatTree;
import com.z_martin.mylibrary.common.language.MultiLanguageUtil;

import androidx.annotation.NonNull;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.CameraXConfig;
import androidx.multidex.MultiDex;

public class BaseApp extends Application implements CameraXConfig.Provider {
    protected static Context context;
    protected static Handler handler;
    protected static int mainThreadId;
    private static BaseApp mApp;

//    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        MultiLanguageUtil.init(this);
//        BGASwipeBackHelper.init(this, null);
//        // 初始化打印日志
        initViseLog();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();
//        NetworkConnectChangedReceiver networkChangedReceiver = new NetworkConnectChangedReceiver();
//        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(networkChangedReceiver, intentFilter);
    }

    /**
     * 初始化打印日志模块
     */
    private void initViseLog() {
        ViseLog.getLogConfig()
//                .configAllowLog(BuildConfig.IS_SHOW_LOG)//是否输出日志
                .configAllowLog(AppConfig.isShowLog())//是否输出日志
                .configShowBorders(true)//是否排版显示
                .configTagPrefix("ystn_log")//设置标签前缀
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")//个性化设置标签，默认显示包名
                .configLevel(Log.VERBOSE);//设置日志最小输出级别，默认Log.VERBOSE
        ViseLog.plant(new LogcatTree());//添加打印日志信息到Logcat的树
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 65535 处理
        MultiDex.install(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }


//    private RefWatcher setupLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return RefWatcher.DISABLED;
//        }
//        return LeakCanary.install(this);
//    }
//
//    public static RefWatcher getRefWatcher(Context context) {
//        BaseApp leakApplication = (BaseApp) context.getApplicationContext();
//        return leakApplication.refWatcher;
//    }

    /**
     * 获取上下文对象
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取全局handler
     *
     * @return 全局handler
     */
    public static Handler getHandler() {
        return handler;
    }

    /**
     * 获取主线程id
     *
     * @return 主线程id
     */
    public static int getMainThreadId() {
        return mainThreadId;
    }

    @NonNull
    @Override
    public CameraXConfig getCameraXConfig() {
        return Camera2Config.defaultConfig();
    }
}
