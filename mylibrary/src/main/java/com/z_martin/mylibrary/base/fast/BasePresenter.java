package com.z_martin.mylibrary.base.fast;

import android.content.Context;

import com.z_martin.mylibrary.base.helper.RxManager;


/**
 * @ describe: 基类presenter
 * @ author: Martin
 * @ createTime: 2018/9/1 18:15
 * @ version: 1.0
 */
public abstract class BasePresenter<M,V>{
    /** 上下文 */
    public Context   mContext;
    public M         mIModel;
    public V         mIView;
    /** 管理RxJava  注册订阅和取消订阅 */
    public RxManager mRxManager = new RxManager();

    /**
     *  绑定IModel和IView
     * @param m model
     * @param v View
     */
    public void setVM(M m, V v) {
        this.mIModel = m;
        this.mIView = v;
        this.onStart();
    }

    /**
     * 解绑IModel和IView
     */
    public void detachMV() {
        if (mRxManager != null)  mRxManager.unSubscribe();
        mIModel = null;
        mIView = null;
    }

    /**
     * IView和IModel绑定完成立即执行
     * <p>
     * 实现类实现绑定完成后的逻辑,例如数据初始化等,界面初始化, 更新等
     */
    public void onStart(){
    }
    
}
