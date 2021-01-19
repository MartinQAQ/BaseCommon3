package com.z_martin.mylibrary.base.activity;

import android.view.View;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.z_martin.mylibrary.base.fast.BaseModel;
import com.z_martin.mylibrary.base.fast.BasePresenter;
import com.z_martin.mylibrary.base.fast.FastBaseAdapter;
import com.z_martin.mylibrary.widgets.statusLayout.OnStatusChildClickListener;
import com.z_martin.mylibrary.widgets.statusLayout.StatusLayoutManager;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @ describe: 带有页面异常处理
 * @ author: Martin
 * @ createTime: 2018/9/1 22:30
 * @ version: 1.0
 */
public abstract class Recycle2BaseActivity<T extends BasePresenter, E extends BaseModel> extends MvpBaseActivity<T, E> {

    /** 替换布局管理器 */
    protected StatusLayoutManager mStatusLayoutManager;
    /** 骨架屏 */
    protected RecyclerViewSkeletonScreen mSkeletonAdapter;

    @Override
    protected void initData() {
        super.initData();
        if(bindReplaceView() != null) {
            mStatusLayoutManager = new StatusLayoutManager.Builder(bindReplaceView()).setOnStatusChildClickListener(new OnStatusChildClickListener() {
                @Override
                public void onEmptyChildClick(View view) {
                    showLoading();
                    onErrorViewClick(view);
                }

                @Override
                public void onErrorChildClick(View view) {
                    showLoading();
                    onEmptyViewClick(view);
                }

                @Override
                public void onCustomerChildClick(View view) {
                }
            }).build();
            showLoading();
        }
    }

    @Override
    public void showNetworkError(int msg) {
        mStatusLayoutManager.showErrorLayout();
    }

    /**
     * 网络异常view被点击时触发，由子类实现
     *
     * @param view view
     */
    protected void onErrorViewClick(View view) {
        queryInitData();
    }

    /**
     * 页面无数据view被点击时触发，由子类实现
     *
     * @param view view
     */
    protected void onEmptyViewClick(View view) {
        queryInitData();
    }

    /**
     * 显示加载中view，由子类实现
     */
    protected void showLoading() {
        mStatusLayoutManager.showLoadingLayout();
    }

    /**
     *  获取绑定替换的View
     */
    protected abstract View bindReplaceView();

    /**
     *  请求初始数据
     */
    protected abstract void queryInitData();


    /**
     * 设置骨架屏View
     * @param layoutId 布局id
     * @param mRecyclerView view
     * @param mFastAdapter 适配器
     */
    protected void setSkeletonView(@LayoutRes int layoutId, RecyclerView mRecyclerView, FastBaseAdapter mFastAdapter){
        //绑定当前列表
        mSkeletonAdapter = Skeleton.bind(mRecyclerView)
                //设置加载列表适配器 ，并且开启动画 设置光晕动画角度等 最后显示
                .adapter(mFastAdapter)
                .shimmer(true)
                //                .angle(20)
                .frozen(false)
                .duration(1000)
                .count(5)
                .load(layoutId)
                .show();
    }

    /**
     * 隐藏骨架屏View
     */
    protected void hideSkeletonAdapter() {
        if (mSkeletonAdapter != null) {
            mSkeletonAdapter.hide();
        }
    }
}
