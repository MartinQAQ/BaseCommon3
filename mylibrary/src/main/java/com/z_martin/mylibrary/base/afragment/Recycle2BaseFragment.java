package com.z_martin.mylibrary.base.afragment;

import android.os.Bundle;
import android.view.View;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.z_martin.mylibrary.base.fast.BaseModel;
import com.z_martin.mylibrary.base.fast.BasePresenter;
import com.z_martin.mylibrary.base.fast.FastBaseAdapter;
import com.z_martin.mylibrary.widgets.statusLayout.OnStatusChildClickListener;
import com.z_martin.mylibrary.widgets.statusLayout.StatusLayoutManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @ describe: 带懒加载的RecycleFragment
 * @ author: Martin
 * @ createTime: 2018/9/1 22:20
 * @ version: 1.0
 */
public abstract class Recycle2BaseFragment<T extends BasePresenter, E extends BaseModel> extends MvpBaseFragment<T, E> {

    /** 替换布局管理器 */
    protected StatusLayoutManager mStatusLayoutManager;
    /** 骨架屏 */
    protected RecyclerViewSkeletonScreen mSkeletonAdapter;
    
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        showLoading();
    }

    @Override
    public void initData(View view) {
        mStatusLayoutManager = new StatusLayoutManager.Builder(getBindView()).setOnStatusChildClickListener(new OnStatusChildClickListener() {
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
        super.initData(view);
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
    protected void onEmptyViewClick(View view){
        queryInitData();
    }

    /**
     * 显示加载中view，由子类实现
     */
    protected void showLoading(){
        mStatusLayoutManager.showLoadingLayout();
    }

    @Override
    public void showNetworkError() {
        mStatusLayoutManager.showErrorLayout();
    }
    
    @Override
    public void showNetworkError(String msg) {
        mStatusLayoutManager.setDefaultEmptyText(msg);
    }

    /**
     *  获取绑定的View
     */
    protected abstract View getBindView();

    /**
     *  请求初始数据
     */
    protected abstract void queryInitData();


    public void onError(String msg, String code) {
        mStatusLayoutManager.showErrorLayout();
    }

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
