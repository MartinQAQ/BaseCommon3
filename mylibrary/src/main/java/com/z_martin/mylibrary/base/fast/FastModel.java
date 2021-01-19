package com.z_martin.mylibrary.base.fast;


import com.z_martin.mylibrary.base.app.AppConfig;
import com.z_martin.mylibrary.base.helper.RetrofitCreateHelper;
import com.z_martin.mylibrary.base.helper.RxHelper;
import com.z_martin.mylibrary.utils.CJSON;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2018/10/22 10:08
 * @ version: 1.0
 */
public class FastModel implements FastContract.IModel {

    @Override
    public Observable<CJSON> get(Map<String, Object> map, String path) {
        return RetrofitCreateHelper.createApi(FastApi.class, AppConfig.getBaseUrl()).get(path, map).compose(RxHelper.<CJSON>rxSchedulerHelper());
    }

    @Override
    public Observable<CJSON> post(RequestBody map, String path) {
        return RetrofitCreateHelper.createApi(FastApi.class, AppConfig.getBaseUrl()).post(path, map).compose(RxHelper.<CJSON>rxSchedulerHelper());
    }

    /**
     *  上传文件
     *  RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
     MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
     * @param path url
     * @param file 文件
     */
    @Override
    public Observable<CJSON> uploadFile(String path, MultipartBody.Part file) {
        return RetrofitCreateHelper.createApi(FastApi.class, AppConfig.getBaseUrl()).uploadFile(path, file).compose(RxHelper.<CJSON>rxSchedulerHelper());
    }

    /**
     *  上传文件
     *  RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
     MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
     * @param path url
     * @param file 文件
     */
    @Override
    public Observable<CJSON> uploadFile(String path, MultipartBody.Part file, Map<String, Object> map) {
        return RetrofitCreateHelper.createApi(FastApi.class, AppConfig.getBaseUrl()).uploadFile(path, file, map).compose(RxHelper.<CJSON>rxSchedulerHelper());
    }
}
