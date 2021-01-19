package com.z_martin.mylibrary.base.request;


import com.z_martin.mylibrary.base.app.AppConfig;

import java.io.Serializable;
import java.util.List;

/**
 * 数组请求结果集
 */
public class BaseListRequest<T> implements Serializable{

    /** 状态码 */
    public int status;
    
    /** 状态描述 */
    public String message;

    /** 结果集 */
    public List<T> results;

    /**
     * 请求是否成功
     * 状态码status==1为请求成功
     * @return 请求成功true,失败false
     */
    public boolean isSuccess() {
        return status == AppConfig.getRequestSuccessStatus();
    }
}
