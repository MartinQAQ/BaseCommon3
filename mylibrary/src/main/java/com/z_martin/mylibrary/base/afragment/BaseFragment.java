package com.z_martin.mylibrary.base.afragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aries.ui.view.title.TitleBarView;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import com.z_martin.mylibrary.R;
import com.z_martin.mylibrary.base.activity.BaseActivity;
import com.z_martin.mylibrary.base.app.BaseApp;
import com.z_martin.mylibrary.base.app.InjectManager;
import com.z_martin.mylibrary.utils.AppUtils;
import com.z_martin.mylibrary.utils.LoadingDialog;
import com.z_martin.mylibrary.utils.StatusBarUtils;
import com.z_martin.mylibrary.utils.ToastUtils;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * @ describe: BaseFragment
 * @ author: Martin
 * @ createTime: 2018/9/1 22:03
 * @ version: 1.0
 */
public abstract class BaseFragment extends SwipeBackFragment implements IBaseFragment, LifecycleProvider<FragmentEvent> {
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();
    protected     Context                        mContext;
    protected     Activity                       mActivity;
    protected     BaseApp                        mApplication;
    private LoadingDialog dialog;
    private       LoadingDialog.Builder          builder;
    private       Unbinder                       unbinder;
    private       int                            mLayoutId;

    /**
     * 沉浸式管理器
     */
    protected ImmersionBar mImmersionBar;

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
//        if (getLayoutView() != null) {
//            return getLayoutView();
//        } else {
        int layoutId = InjectManager.inject(this);
        View view = inflater.inflate(layoutId, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
//        }
    }

//    @LayoutRes
//    public abstract int getLayoutId();

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        StatusBarUtils.setColor(getActivity(), R.color.black, 255);
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getBundle(getArguments());
        onBeforeBind();
        initData(view);
        initImmersionBar();
        initUI(view, savedInstanceState);
    }


    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.hideBar(BarHide.FLAG_SHOW_BAR).init();
//        mImmersionBar
//                .navigationBarColor(R.color.white)
//                .navigationBarColorTransform(R.color.white).init();
//        mImmersionBar.hideBar(BarHide.FLAG_SHOW_BAR).init();
        // 设置状态栏字体颜色为深色
//        if (ImmersionBar.isSupportStatusBarDarkFont())
//            mImmersionBar.statusBarDarkFont(true).init();

        // mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
    }


    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
        hideDialog();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    /**
     * 绑定数据
     */
    protected void onBeforeBind() {
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
//        if (mImmersionBar != null)
//            mImmersionBar.destroy();
        System.gc();
        System.runFinalization();
//        RefWatcher refWatcher = BaseApp.getRefWatcher(getActivity());//1
//        refWatcher.watch(this);
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mImmersionBar != null)
            mImmersionBar.init();
    }

//    /**
//     * 设置布局Id
//     *
//     * @param layoutId 布局Id
//     */
//    public void setLayoutId(int layoutId) {
//        this.mLayoutId = layoutId;
//    }

    public View getLayoutView() {
        return null;
    }

    /**
     * 得到Activity传进来的值
     */
    public void getBundle(Bundle bundle) {
    }

    /**
     * 初始化UI
     */
    public abstract void initUI(View view, @Nullable Bundle savedInstanceState);

    /**
     * 在监听器之前把数据准备好
     */
    public void initData(View view) {
        builder = new LoadingDialog.Builder(mActivity);
        mContext = AppUtils.getContext();
        mApplication = (BaseApp) mActivity.getApplication();
    }

    /**
     * 设置默认titleBar
     *
     * @param toolbar titleBarId
     * @param title   标题id
     */
    protected void initTitleBarView(TitleBarView toolbar, int title) {
        toolbar.setTitleMainText(title);
    }

    /**
     * 设置默认titleBar
     *
     * @param toolbar titleBarId
     * @param title   标题字符串
     */
    protected void initTitleBarView(TitleBarView toolbar, String title) {
        toolbar.setTitleMainText(title);
    }

    /**
     * 处理回退事件
     *
     * @return true 事件已消费
     * <p>
     * false 事件向上传递
     */
    @Override
    public boolean onBackPressedSupport() {
        assert getFragmentManager() != null;
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            //如果当前存在fragment>1，当前fragment出栈
            pop();
        } else {
            //已经退栈到root fragment，交由Activity处理回退事件
            return false;
        }
        return true;
    }

    @Override
    public void startNewActivity(@NonNull Class<?> clz) {
        ((BaseActivity) mActivity).startNewActivity(clz);
    }

    @Override
    public void startNewActivity(@NonNull Class<?> clz, Bundle bundle) {
        ((BaseActivity) mActivity).startNewActivity(clz, bundle);
    }

    @Override
    public void startNewActivityForResult(@NonNull Class<?> clz, Bundle bundle, int requestCode) {
        ((BaseActivity) mActivity).startNewActivityForResult(clz, bundle, requestCode);
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
    public void showDialog(String waitMsg) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = builder.create(waitMsg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void showDialog(int waitMsg) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = builder.create(waitMsg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void showDialog() {
        this.showDialog("");
    }

    @Override
    public void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void hideKeyboard() {
        hideSoftInput();
    }

    @Override
    public void back() {
        this.onBackPressedSupport();
    }

    @Override
    public void showNetworkError() {
        showToast(R.string.net_work_error);
    }

    @Override
    public void showNetworkError(String msg) {
        showToast(msg);
    }

    @Override
    public void showNetworkError(int msg) {
        showToast(msg);
    }

    @Override
    public void startNewFragment(@NonNull SupportFragment supportFragment) {
        start(supportFragment);
    }

    @Override
    public void startNewFragmentWithPop(@NonNull SupportFragment supportFragment) {
        startWithPop(supportFragment);
    }

    @Override
    public void startNewFragmentForResult(@NonNull SupportFragment supportFragment, int
            requestCode) {
        startForResult(supportFragment, requestCode);
    }

    @Override
    public void popToFragment(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        popTo(targetFragmentClass, includeTargetFragment);
    }

    @Override
    public void setOnFragmentResult(int ResultCode, Bundle data) {
        setFragmentResult(ResultCode, data);
    }

    @Override
    public boolean isVisiable() {
        return isSupportVisible();
    }

    @Override
    public Activity getBindActivity() {
        return mActivity;
    }
}
