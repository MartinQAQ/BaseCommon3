package com.z_martin.mylibrary.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.z_martin.mylibrary.R;
import com.z_martin.mylibrary.base.fast.FastContract;
import com.z_martin.mylibrary.base.fast.FastModel;
import com.z_martin.mylibrary.base.fast.FastPresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ describe: 实现沉浸式的基类
 * @ author: Martin
 * @ createTime: 2018/12/24 14:23
 * @ version: 1.0
 */
public abstract class BaseMvpDialogFragment extends BaseDialogFragment implements FastContract.IView {
    protected Activity mActivity;
    protected Dialog dialog;
    protected View mRootView;
    protected Window mWindow;
    protected Context mContext;

    public FastPresenter mPresenter;
    public FastModel     mModel;

    /**
     * 屏幕宽度
     */
    protected int mWidth;
    /**
     * 屏幕高度
     */
    protected int mHeight;
    private Unbinder unbinder;

    protected ImmersionBar mImmersionBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = getDialog();
        //点击外部消失
        dialog.setCanceledOnTouchOutside(true);
        mWindow = dialog.getWindow();
        //测量宽高
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(dm);
            mWidth = dm.widthPixels;
            mHeight = dm.heightPixels;
        } else {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            mWidth = metrics.widthPixels;
            mHeight = metrics.heightPixels;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initPresenter();
        super.onViewCreated(view, savedInstanceState);
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        initData();
        initView();
        setListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
//        if(mImmersionBar != null) mImmersionBar.destroy();
    }

    /**
     * 设置布局Id
     * @return the layout id
     */
    protected abstract int setLayoutId();

    /**
     * 是否在Fragment使用沉浸式
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.hideBar(BarHide.FLAG_SHOW_BAR).init();
        // 设置状态栏字体颜色为深色
//        mImmersionBar
//                .navigationBarColor(R.color.white)
//                .navigationBarColorTransform(R.color.white).init();
    }


    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * view与数据绑定
     */
    protected void initView() {

    }

    /**
     * 设置监听
     */
    protected void setListener() {

    }

    private void initPresenter() {
        mPresenter = new FastPresenter();
        mModel = new FastModel();
        if (mPresenter != null) {
            mPresenter.mContext = mContext;
        }
        mPresenter.setVM(mModel, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachMV();
        }
    }
}
