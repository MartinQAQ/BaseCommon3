package com.z_martin.mylibrary.base.helper;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 管理RxJava  
 * 注册订阅和取消订阅
 */

public class RxManager {
    /** 管理订阅者 */ 
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     *  添加订阅
     */
    public void register(Disposable d) {
        mCompositeDisposable.add(d);
    }

    /**
     *  取消订阅
     */
    public void unSubscribe() {
        mCompositeDisposable.dispose();// 取消订阅
    }
}
