package com.z_martin.mylibrary.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;

import com.z_martin.mylibrary.R;
import com.z_martin.mylibrary.base.app.IBaseView;
import com.z_martin.mylibrary.base.app.InjectManager;
import com.z_martin.mylibrary.utils.LoadingDialog;
import com.z_martin.mylibrary.utils.ScreenUtil;
import com.z_martin.mylibrary.utils.ToastUtils;

import java.util.List;

import androidx.annotation.NonNull;
import butterknife.ButterKnife;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2018/11/23 11:32
 * @ version: 1.0
 */
public abstract class BaseDialog<T> extends Dialog implements IBaseView {
    /** 上下文 */
    protected Context mContext;

    /** 数据集合 */
    private List<T> mList;

    /** 设置位置 */
    private int mGravity = Gravity.TOP;

    /** 设置宽度的比例 */
    private double mWidthRatio = 1.0;

    /** 设置高度的比例 */
    private double mHeightRatio = 1.0;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.add_address_dialog);
        mContext = context;
    }

    /**
     * 构造函数
     * @param context 上下文
     * @param themeResId 主题 默认主题为-1
     */
    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId != -1 ? themeResId : R.style.add_address_dialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = new LoadingDialog.Builder(mContext);
        ScreenUtil.resetDensity(mContext);
        InjectManager.inject(this, mContext);
        ButterKnife.bind(this);
        initView(savedInstanceState);
    }


    /**
     *  设置数据
     * @param list
     * @return
     */
    public BaseDialog setData(List<T> list){
        mList = list;
        return this;
    }

    /**
     * 初始化view
     * <p>
     * 子类实现 控件绑定、视图初始化等内容
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    private LoadingDialog dialog;
    private LoadingDialog.Builder builder;

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void showToast(int msg) {
        ToastUtils.showToast(mContext, msg);
    }

    @Override
    public void showDialog(String msg) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = builder.create(msg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void showDialog(int msg) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = builder.create(msg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void showDialog() {
        showDialog(null);
    }

    @Override
    public void hideDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void hideKeyboard() {
    }

    @Override
    public void back() {

    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void showNetworkError(String msg) {

    }

    @Override
    public void showNetworkError(int msg) {

    }

}
