package com.z_martin.mylibrary.base.activity;


import com.z_martin.mylibrary.base.fast.BaseModel;
import com.z_martin.mylibrary.base.fast.BasePresenter;

/**
 * @description: 快速构建mvp
 * @project name: BaseCommon2.0
 * @author: xiaoming723@126.com
 * @createTime: 2020/6/13 23:44
 * @version: 1.0
 */
public abstract class FastActivity<T extends BasePresenter, E extends BaseModel> extends MvpBaseActivity<T, E> {
    @Override
    protected void initPresenter() {
        mPresenter.setVM(mModel, this);
    }
}
