package com.z_martin.mylibrary.base.helper.callback;


import com.vise.log.ViseLog;

import io.reactivex.functions.Consumer;

/**
 * @ describe: 自定义异常消息处理Consumer
 * @ author: Martin
 * @ createTime: 2018/10/21 0:24
 * @ version: 1.0
 */
public class MyThrowable implements Consumer<Throwable> {
    
    private MyThrowableCallBack mThrowableCallBack;
    
    public MyThrowable(MyThrowableCallBack throwableCallBack) {
        this.mThrowableCallBack = throwableCallBack;
    }
    
    @Override
    public void accept(Throwable throwable) throws Exception {
        ViseLog.e(throwable.getMessage());
        ViseLog.e(throwable.getLocalizedMessage());
        ViseLog.e(throwable.toString());
        mThrowableCallBack.throwable(throwable);
    }
}
