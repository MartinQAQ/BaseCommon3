package com.z_martin.mylibrary.entity;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2018/11/28 11:21
 * @ version: 1.0
 */
public class NetworkChangeEvent {
    /** 是否存在网络 */
    public boolean isConnected;

    public NetworkChangeEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }
}
