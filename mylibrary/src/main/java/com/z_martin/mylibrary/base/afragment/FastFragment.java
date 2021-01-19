package com.z_martin.mylibrary.base.afragment;


import com.z_martin.mylibrary.base.fast.BaseModel;
import com.z_martin.mylibrary.base.fast.BasePresenter;

/**
 * @description: 快速mvp
 * @project name: BaseCommon2.0
 * @author: xiaoming723@126.com
 * @createTime: 2020/6/13 23:47
 * @version: 1.0
 */
public abstract class FastFragment<T extends BasePresenter, E extends BaseModel> extends MvpBaseFragment<T, E> {

    @Override
    protected void initPresenter() {
        mPresenter.setVM(mModel, this);
    }
}
