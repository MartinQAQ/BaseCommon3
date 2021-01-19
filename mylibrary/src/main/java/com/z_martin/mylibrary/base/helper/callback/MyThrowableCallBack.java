package com.z_martin.mylibrary.base.helper.callback;

/**
 * @ describe:请求数据异常处理callBack
 * @ author: Martin
 * @ createTime: 2018/10/21 0:22
 * @ version: 1.0
 */
public interface MyThrowableCallBack {
    /**
     *  请求结果集异常
     * @param throwable 异常信息
     */
    void throwable(Throwable throwable);
}
