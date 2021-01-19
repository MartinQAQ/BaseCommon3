package com.z_martin.mylibrary.widgets;

import android.content.Context;
import android.os.Bundle;

import com.z_martin.mylibrary.R;
import com.z_martin.mylibrary.base.fast.FastContract;
import com.z_martin.mylibrary.base.fast.FastModel;
import com.z_martin.mylibrary.base.fast.FastPresenter;

import androidx.annotation.NonNull;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2018/11/23 11:32
 * @ version: 1.0
 */
public abstract class BaseMvpDialog extends BaseDialog implements FastContract.IView  {
    /** 上下文 */
    protected Context mContext;

    public FastPresenter mPresenter;
    public FastModel     mModel;

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPresenter != null) {
            mPresenter.detachMV();
        }
    }
    
    public BaseMvpDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    /**
     * 构造函数
     * @param context 上下文
     * @param themeResId 主题 默认主题为-1
     */
    public BaseMvpDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId != -1 ? themeResId : R.style.add_address_dialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initPresenter();
        super.onCreate(savedInstanceState);
    }

    private void initPresenter() {
        mPresenter = new FastPresenter();
        mModel = new FastModel();
        if (mPresenter != null) {
            mPresenter.mContext = mContext;
        }
        mPresenter.setVM(mModel, this);
    }
}
