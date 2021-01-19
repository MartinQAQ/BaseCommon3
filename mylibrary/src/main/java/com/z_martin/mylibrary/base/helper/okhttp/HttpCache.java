package com.z_martin.mylibrary.base.helper.okhttp;


import com.z_martin.mylibrary.utils.AppUtils;

import java.io.File;

import okhttp3.Cache;

/**
 * http 缓存
 */
public class HttpCache {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    /**
     *  获取缓存
     * @return
     */
    public static Cache getCache() {
        return new Cache(new File(AppUtils.getContext().getCacheDir().getAbsolutePath() + File
                .separator + "data/NetCache"),
                HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
