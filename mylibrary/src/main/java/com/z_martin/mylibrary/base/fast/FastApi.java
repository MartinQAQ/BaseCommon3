package com.z_martin.mylibrary.base.fast;


import com.z_martin.mylibrary.utils.CJSON;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2018/10/22 10:10
 * @ version: 1.0
 */
public interface FastApi {

    /**
     * 获取数据
     *
     * @param url urlPath
     * @param params 参数
     */
    @GET
    Observable<CJSON> get(@Url String url, @QueryMap Map<String, Object> params);
    /**
     * 获取数据
     *
     * @param url urlPath
     * @param params 参数
     */
    @POST
    Observable<CJSON> post(@Url String url, @Body RequestBody params);
    
    /**
     *  上传文件
     */
    @Multipart
    @POST
    Observable<CJSON> uploadFile(@Url String url, @Part MultipartBody.Part file);
    
    /**
     *  上传文件
     */
    @Multipart
    @POST
    Observable<CJSON> uploadFile(@Url String url, @Part MultipartBody.Part file, @PartMap Map<String, Object> params);

    @POST
    Call<ResponseBody> location(@Url String url, @Body RequestBody params);
    
    @POST
    Call<ResponseBody> file(@Url String url);

    /**
     *  上传文件
     */
    @Multipart
    @POST
    Observable<CJSON> uploadFile(@Url String url, @Part MultipartBody.Part file1, @Part MultipartBody.Part file2, @Part MultipartBody.Part file3, @Part MultipartBody.Part file4, @Part MultipartBody.Part file5, @Part MultipartBody.Part file6, @Body RequestBody params);

    /**
     *  上传文件
     */
    @Multipart
    @POST
    Observable<CJSON> uploadFile(@Url String url, @Part MultipartBody.Part file, @Part MultipartBody.Part file2, @Body RequestBody params);
}
