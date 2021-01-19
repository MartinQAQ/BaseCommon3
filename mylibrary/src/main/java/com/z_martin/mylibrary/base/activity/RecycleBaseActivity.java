package com.z_martin.mylibrary.base.activity;

import android.view.View;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.z_martin.mylibrary.R;
import com.z_martin.mylibrary.base.fast.BaseModel;
import com.z_martin.mylibrary.base.fast.BasePresenter;
import com.z_martin.mylibrary.base.fast.FastBaseAdapter;
import com.z_martin.mylibrary.utils.CollectionUtils;
import com.z_martin.mylibrary.widgets.statusLayout.OnStatusChildClickListener;
import com.z_martin.mylibrary.widgets.statusLayout.StatusLayoutManager;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @ describe: 带有页面异常处理
 * @ author: Martin
 * @ createTime: 2018/9/1 22:30
 * @ version: 1.0
 */
public abstract class RecycleBaseActivity<T extends BasePresenter, E extends BaseModel, B> extends MvpBaseActivity<T, E> {
    protected RecyclerView               mRecyclerView;
    protected SpringView                 mSpringView;
    /** 页码 */
    protected int                        pageNum = 1;
    /** 快速适配器 */
    protected FastBaseAdapter            mFastAdapter;
    /** 替换布局管理器 */
    protected StatusLayoutManager mStatusLayoutManager;
    /** 骨架屏 */
    protected RecyclerViewSkeletonScreen mSkeletonAdapter;


    @Override
    protected void initData() {
        super.initData();
        mRecyclerView = findViewById(R.id.recycler_view);
        mSpringView = findViewById(R.id.spring_view);
        mSpringView.setHeader(new DefaultHeader(this));
        mSpringView.setFooter(new DefaultFooter(this));
        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                queryInitData();
            }
            @Override
            public void onLoadmore() {
                pageNum ++;
                queryInitData();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFastAdapter = initAdapter();
        mRecyclerView.setAdapter(mFastAdapter);
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

    /**
     * 初始化适配器
     * */
    protected abstract FastBaseAdapter initAdapter();

    @Override
    public void showNetworkError(int msg) {
//        showToast(msg);
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

    public void onError(String msg, String code) {
        mStatusLayoutManager.showErrorLayout();
    }

    /**
     *  设置数据
     * @param list
     */
    protected void setData(List<B> list){
        if (!CollectionUtils.isNullOrEmpty(list)) {
            if (pageNum == 1) {
                mFastAdapter.setNewData(list);
                mStatusLayoutManager.showSuccessLayout();
                hideSkeletonAdapter();
            } else {
                mFastAdapter.addData(list);
            }
        } else {
            if (pageNum == 1) {
                mStatusLayoutManager.showEmptyLayout();
            } else {
                showToast(R.string.no_more_data);
            }
        }
        finishFreshAndLoad();
    }

    /**
     *  设置骨架屏View
     * @param layoutId 布局id
     */
    protected void setSkeletonView(@LayoutRes int layoutId){
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

    /**
     * 关闭加载提示
     */
    private void finishFreshAndLoad() {
        mSpringView.onFinishFreshAndLoad();
    }

}
