package com.z_martin.mylibrary.base.activity;

import android.view.View;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.ViewSkeletonScreen;
import com.z_martin.mylibrary.base.app.IBaseView;
import com.z_martin.mylibrary.base.fast.BaseModel;
import com.z_martin.mylibrary.base.fast.BasePresenter;
import com.z_martin.mylibrary.utils.TUtil;

import androidx.annotation.LayoutRes;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2018/9/1 18:35
 * @ version: 1.0
 */
public abstract class MvpBaseActivity<T extends BasePresenter, E extends BaseModel> extends BaseActivity implements IBaseView {
    protected T mPresenter;
    protected E mModel;
    /** 骨架屏 */
    protected ViewSkeletonScreen mSkeletonView;

    /**
     * 绑定presenter 和 View
     */
    @Override
    protected void onBeforeBind() {
        super.onBeforeBind();
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;
            initPresenter();
        }
    }


    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    protected abstract void initPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachMV();
    }

    /**
     *  设置骨架屏View
     * @param layoutId 布局id
     * @param skeletonView 绑定的View
     */
    protected void setSkeletonView(@LayoutRes int layoutId, View skeletonView){
        mSkeletonView = Skeleton.bind(skeletonView)
                //设置加载列表适配器 ，并且开启动画 设置光晕动画角度等 最后显示
//                .shimmer(true)
                .duration(1000)
//                .angle(20) // 倾斜角度
                .load(layoutId)
                .show();
    }

    protected void hideSkeleton() {
        if (mSkeletonView != null) {
            mSkeletonView.hide();
        }
    }
}
