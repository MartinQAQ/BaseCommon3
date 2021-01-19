package com.z_martin.mylibrary.base.helper.callback;

/**
 * @ describe: 请求数据处理callBack
 * @ author: Martin
 * @ createTime: 2018/10/21 0:20
 * @ version: 1.0
 */
public  interface MyConsumerCallBack<T> {
    /**
     *  请求结果集成功
     * @param request 请求返回数据集
     * @param code 请求标识
     */
    void onSuccess(T request, String code);

    /**
     *  请求接口错误信息
     * @param msg 错误信息
     * @param code 请求标识
     */
    void onError(String msg, String code);
}
