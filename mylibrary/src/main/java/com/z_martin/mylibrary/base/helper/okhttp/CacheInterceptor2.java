package com.z_martin.mylibrary.base.helper.okhttp;


import com.z_martin.mylibrary.base.app.AppConfig;
import com.z_martin.mylibrary.common.language.MultiLanguageUtil;
import com.z_martin.mylibrary.utils.AppUtils;
import com.z_martin.mylibrary.utils.HttpUtils;
import com.z_martin.mylibrary.utils.NetworkConnectionUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 *  缓存拦截器
 */
public class CacheInterceptor2 implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkConnectionUtils.isNetworkConnected(AppUtils.getContext())) {
            // 有网络时, 缓存10秒
            int maxAge = 10;
            Request.Builder builder =  request.newBuilder()
                    .removeHeader("User-Agent");
            builder.header("Authorization", AppConfig.getAuthorizationFinal());
            // 中文0   英文1
            builder.header("Language", MultiLanguageUtil.getInstance().getLanguageType().equals("zh") ? "0" : "1");
            request = builder.build();

            Response response = chain.proceed(request);

            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            // 无网络时，缓存为4周
            int maxStale = 60 * 60 * 24 * 28;
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .removeHeader("User-Agent")
                    .header("User-Agent", HttpUtils.getUserAgent())
                    .header("Authorization", AppConfig.getAuthorization())
                    .build();

            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }

        //        Request request = chain.request();
        //        if (!NetworkConnectionUtils.isConnected(AppUtils.getContext())) {
        //            request = request.newBuilder()
        //                    .cacheControl(CacheControl.FORCE_CACHE)
        //                    .build();
        //        }
        //        Response response = chain.proceed(request);
        //        if (NetworkConnectionUtils.isConnected(AppUtils.getContext())) {
        //            int maxAge = 0;
        //            // 有网络时, 不缓存, 最大保存时长为0
        //            response.newBuilder()
        //                    .header("Cache-Control", "public, max-age=" + maxAge)
        //                    .removeHeader("Pragma")
        //                    .build();
        //        } else {
        //            // 无网络时，设置超时为4周
        //            int maxStale = 60 * 60 * 24 * 28;
        //            response.newBuilder()
        //                    .header("Cache-Control", "public, only-if-cached, max-stale=" +
        // maxStale)
        //                    .removeHeader("Pragma")
        //                    .build();
        //        }
        //        return response;
    }
}
