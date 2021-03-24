package com.z_martin.mylibrary.base.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.aries.ui.view.title.TitleBarView;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import com.z_martin.mylibrary.R;
import com.z_martin.mylibrary.base.app.AppConfig;
import com.z_martin.mylibrary.base.app.AppManager;
import com.z_martin.mylibrary.base.app.AppStatus;
import com.z_martin.mylibrary.base.app.AppStatusManager;
import com.z_martin.mylibrary.base.app.BaseApp;
import com.z_martin.mylibrary.base.app.InjectManager;
import com.z_martin.mylibrary.common.language.MultiLanguageUtil;
import com.z_martin.mylibrary.entity.NetworkChangeEvent;
import com.z_martin.mylibrary.utils.AppUtils;
import com.z_martin.mylibrary.utils.LoadingDialog;
import com.z_martin.mylibrary.utils.ScreenUtil;
import com.z_martin.mylibrary.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @ describe: BaseActivity
 * @ author: Martin
 * @ createTime: 2018/9/1 16:52
 * @ version: 1.0
 */
public abstract class BaseActivity extends SupportActivity implements LifecycleProvider<ActivityEvent>, IBaseActivity {
    protected BaseApp               mApplication;
    private LoadingDialog dialog;
    private   LoadingDialog.Builder builder;
    protected Context               mContext;//全局上下文对象
    protected boolean               isTransAnim;
    protected Unbinder              unbinder;

    /** 网络管理 */
    protected boolean mCheckNetWork = true; //默认检查网络状态
    View mTipView;
    WindowManager mWindowManager;
    WindowManager.LayoutParams mLayoutParams;
    /** 沉浸式管理器  */
    protected ImmersionBar mImmersionBar;


    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    static {
        //5.0以下兼容vector
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        onBeforeBind();
        init(savedInstanceState);
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        lifecycleSubject.onNext(ActivityEvent.RESUME);
        super.onResume();
        //在无网络情况下打开APP时，系统不会发送网络状况变更的Intent，需要自己手动检查
//        hasNetWork(NetManagerUtils.isOpenNetwork(mContext));
//        MobclickAgent.onResume(this);
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
        hideDialog();
//        MobclickAgent.onPause(this);
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    /**
     *  自动装配MVP - 绑定数据
     */
    protected void onBeforeBind() {}

    private boolean isSetScreen = true;
    public void setSetScreen(){
        this.isSetScreen = false;
    }
    
    public void setSetScreen(boolean isSetScreen){
        this.isSetScreen = isSetScreen;
    }
    
    private boolean initImmersionBar = true;
    public void setImmersionBar(){
        initImmersionBar = false;
    }

    private void init(Bundle savedInstanceState) {
        // 设置8.0下透明主题
//        setTheme(R.style.AppTheme_base);
        if (AppConfig.isOpen() && AppStatusManager.getInstance().getAppStatus() == AppStatus.STATUS_RECYCLE){
            //被回收，跳转到启动页面
            Intent intent = new Intent(this, AppConfig.getSplashActivity());
            startActivity(intent);
            AppManager.getAppManager().finishAllActivity();
            return;
        }
        // 今日头条适配方案
        if (isSetScreen) {
            ScreenUtil.resetDensity(this);
        }
        InjectManager.inject(this);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this);
        if (initImmersionBar) {
            initImmersionBar();
        }
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        builder = new LoadingDialog.Builder(this);
        initData();
        initTipView();//初始化提示View
        initView(savedInstanceState);
        setCheckNetWork(false);
        AppManager.getAppManager().addActivity(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(final NetworkChangeEvent event) {
        hasNetWork(event.isConnected);
    }
    //
    private void hasNetWork(boolean has) {
        if (isCheckNetWork()) {
            if (has) {
                if (mTipView != null && mTipView.getParent() != null) {
                    mWindowManager.removeView(mTipView);
                }
            } else {
                if (mTipView.getParent() == null) {
                    mWindowManager.addView(mTipView, mLayoutParams);
                }
            }
        }
    }

    public void setCheckNetWork(boolean checkNetWork) {
        mCheckNetWork = checkNetWork;
    }

    public boolean isCheckNetWork() {
        return mCheckNetWork;
    }

    private void initTipView() {
        LayoutInflater inflater = getLayoutInflater();
        mTipView = inflater.inflate(R.layout.layout_network_tip, null); //提示View布局
        mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        //使用非CENTER时，可以通过设置XY的值来改变View的位置
        mLayoutParams.gravity = Gravity.TOP;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
        mTipView.findViewById(R.id.error_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("打开网络设置");
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                if (isTransAnim)
                    overridePendingTransition(R.anim.activity_start_trans_in, R.anim
                            .activity_start_trans_out);
            }
        });
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.hideBar(BarHide.FLAG_SHOW_BAR).init();
//        mImmersionBar
//                .navigationBarColor(R.color.white)
//                .navigationBarColorTransform(R.color.white).init();
//        mImmersionBar.hideBar(BarHide.FLAG_SHOW_BAR).init();
//        mImmersionBar.hideBar(BarHide.FLAG_SHOW_BAR).init();
        // 设置状态栏字体颜色为深色
//        if (ImmersionBar.isSupportStatusBarDarkFont())
//            mImmersionBar.statusBarDarkFont(true).init();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(newBase));
    }

    /**
     * 初始化view
     * <p>
     * 子类实现 控件绑定、视图初始化等内容
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化数据
     * <p>
     * 子类可以复写此方法初始化子类数据
     */
    protected void initData() {
        mContext = this;
        mApplication = (BaseApp) getApplication();
        isTransAnim = true;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                //调用方法判断是否需要隐藏键盘
                AppUtils.hideKeyboard(ev, view, this);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * [页面跳转]
     *
     * @param clz 要跳转的Activity
     */
    @Override
    public void startNewActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
        if (isTransAnim)
            overridePendingTransition(R.anim.activity_start_trans_in, R.anim
                    .activity_start_trans_out);
    }

    /**
     * [页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param intent intent
     */
    @Override
    public void startNewActivity(Class<?> clz, Intent intent) {
        intent.setClass(this, clz);
        startActivity(intent);
        if (isTransAnim)
            overridePendingTransition(R.anim.activity_start_trans_in, R.anim
                    .activity_start_trans_out);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param bundle bundle数据
     */
    @Override
    public void startNewActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (isTransAnim)
            overridePendingTransition(R.anim.activity_start_trans_in, R.anim
                    .activity_start_trans_out);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     * @param clz         要跳转的Activity
     * @param bundle      bundle数据
     * @param requestCode requestCode
     */
    public void startNewActivityForResult(Class<?> clz, Bundle bundle,
                                          int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        if (isTransAnim)
            overridePendingTransition(R.anim.activity_start_trans_in, R.anim
                    .activity_start_trans_out);
    }

    /**
     * 显示提示框
     *
     * @param msg 提示框内容字符串
     */
    @Override
    public void showDialog(String msg) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = builder.create(msg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /**
     * 显示提示框
     */
    @Override
    public void showDialog() {
        showDialog(null);
    }

    /**
     * 隐藏提示框
     */
    @Override
    public void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void showToast(int id) {
        ToastUtils.showToast(mContext, id);
    }

    @Override
    public void hideKeyboard() {
        View view = getCurrentFocus();
        assert view != null;
        //调用方法判断是否需要隐藏键盘
        AppUtils.hideSoftInput(view);
    }

    @Override
    public void back() {
        super.onBackPressedSupport();
    }

    @Override
    public void showNetworkError() {

    }
    @Override
    public void showNetworkError(String msg) {
//        if (msg.contains("500")) {
//            showToast("连接不上服务器");
//        } else if (msg.contains("401")) {
//            Intent intent = new Intent();
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            Bundle bundle1 = new Bundle();
//            setIsTransAnim(false);
//            intent.setClass(this, AppConfig.getTokenErrorActivity());
//            intent.putExtras(bundle1);
//            startActivity(intent);
//            AppConfig.setAuthorization("");
//            SpUtils.remove(AppUtils.getContext(), "user_info");
////            AppUtils.getContext().startActivity(new Intent(AppUtils.getContext(), AppConfig.getTokenErrorActivity()));
//            AppManager.getAppManager().finishAllActivity();
//            finish();
////            if(!AppManager.getAppManager().isOpenActivity(AppConfig.getTokenErrorActivity())) {
////                AppConfig.setAuthorization("");
////                SpUtils.remove(AppUtils.getContext(), "user_info");
////                AppUtils.getContext().startActivity(new Intent(AppUtils.getContext(), AppConfig.getTokenErrorActivity()));
////                AppManager.getAppManager().finishAllActivity();
////                finish();
////            }
//        }
    }
    
    @Override
    public void showNetworkError(int msg) {

    }

    @Override
    public void finish() {
        super.finish();
        //当提示View被动态添加后直接关闭页面会导致该View内存溢出，所以需要在finish时移除
        if (mTipView != null && mTipView.getParent() != null) {
            mWindowManager.removeView(mTipView);
        }
        if (isTransAnim)
            overridePendingTransition(R.anim.activity_finish_trans_in, R.anim
                    .activity_finish_trans_out);
    }

    /**
     * 隐藏键盘
     *
     * @return 隐藏键盘结果
     * <p>
     * true:隐藏成功
     * <p>
     * false:隐藏失败
     */
    protected boolean hiddenKeyboard() {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService
                (INPUT_METHOD_SERVICE);
        return mInputMethodManager.hideSoftInputFromWindow(this
                .getCurrentFocus().getWindowToken(), 0);
    }

    /**
     *  带返回按钮的TitleBarView
     * @param toolbar TitleBarView id
     * @param title 标题id
     */
    protected void initTitleBarView(TitleBarView toolbar, int title) {
        toolbar.setTitleMainText(title).setLeftTextDrawable(R.mipmap.back_white).setOnLeftTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
    }

    /**
     *  带返回按钮的TitleBarView
     * @param toolbar TitleBarView id
     * @param title 标题 字符串
     */
    protected void initTitleBarView(TitleBarView toolbar, String title) {
        toolbar.setLeftTextPadding(40,0, 40,0).setTitleMainText(title).setLeftTextDrawable(R.mipmap.back_white).setOnLeftTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
    }


    /**
     * 是否使用overridePendingTransition过度动画
     * @return 是否使用overridePendingTransition过度动画，默认使用
     */
    protected boolean isTransAnim() {
        return isTransAnim;
    }

    /**
     * 设置是否使用overridePendingTransition过度动画
     */
    public void setIsTransAnim(boolean b) {
        isTransAnim = b;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //屏幕旋转时会使一些参数初始化，所以也需要在此重置一下
        ScreenUtil.resetDensity(this);
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
        if (unbinder != null)  unbinder.unbind();
//        if (mImmersionBar != null) mImmersionBar.destroy();  //在BaseActivity里销毁
        EventBus.getDefault().unregister(this);
        AppManager.getAppManager().finishActivity(this);
        System.gc();
        System.runFinalization();
//        RefWatcher refWatcher = BaseApp.getRefWatcher(this);//1
//        refWatcher.watch(this);
    }

    /**
     * 兼容androidX在部分手机切换语言失败问题
     * @param overrideConfiguration 覆盖配置
     */
    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        if (overrideConfiguration !=null) {
            int uiMode = overrideConfiguration.uiMode;
            overrideConfiguration.setTo(getBaseContext().getResources().getConfiguration());
            overrideConfiguration.uiMode = uiMode;
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }
}
