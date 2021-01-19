package com.z_martin.mylibrary.base.fast;


import com.z_martin.mylibrary.base.app.IBaseView;
import com.z_martin.mylibrary.utils.CJSON;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @ describe: 快速建立
 * @ author: Martin
 * @ createTime: 2018/10/22 9:59
 * @ version: 1.0
 */
public interface FastContract {
    
       abstract class Presenter extends BasePresenter<IModel, IView> {
        
        /**
         *  获取请求结果
         * @param params 请求参数
         * @param path 请求url路径
         */
        public abstract void get(Map<String, Object> params, String path);
        
        /**
         *  获取请求结果
         * @param params 请求参数
         * @param path 请求url路径
         * @param isShowLoading 是否显示加载框
         */
        public abstract void get(Map<String, Object> params, String path, boolean isShowLoading);
        
        /**
         *  获取请求结果
         * @param params 请求参数
         * @param path 请求url路径
         */
        public abstract void post(RequestBody params, String path);
        
        /**
         *  获取请求结果
         * @param params 请求参数
         * @param path 请求url路径
         * @param isShowLoading 是否显示加载框
         */
        public abstract void post(RequestBody params, String path, boolean isShowLoading);

       /**
        *  上传文件
        *  RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
           MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        * @param path url
        * @param filePath 文件路径
        */
        public abstract void uploadFile(String path, String filePath, String fileName, boolean isCompress);
       /**
        *  上传文件
        *  RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
           MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        * @param path url
        * @param filePath 文件路径
        * @param isShowLoading 是否显示加载框
        */
        public abstract void uploadFile(String path, String filePath, String fileName, boolean isCompress, boolean isShowLoading);
       /**
        *  上传文件
        *  RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
           MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        * @param path url
        * @param filePath 文件路径
        * @param map 请求参数
        * @param fileName 文件名称
        */
        public abstract void uploadFile(String path, String filePath, Map<String, Object> map, String fileName, boolean isCompress);

    }

    interface IModel extends BaseModel {


        /**
         *  获取请求结果
         * @param map 请求参数
         * @param path 请求url路径
         * @return 结果集
         */
        Observable<CJSON> get(Map<String, Object> map, String path);


        /**
         *  获取请求结果
         * @param map 请求参数
         * @param path 请求url路径
         * @return 结果集
         */
        Observable<CJSON> post(RequestBody map, String path);

        /**
         *  上传文件
         *  RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
         MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
         * @param path url
         * @param file 文件
         */
        Observable<CJSON> uploadFile(String path, MultipartBody.Part file);
        
        /**
         *  上传文件
         *  RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
         MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
         * @param path url
         * @param file 文件
         * @param map 请求参数
         */
        Observable<CJSON> uploadFile(String path, MultipartBody.Part file, Map<String, Object> map);
        
    }

    interface IView extends IBaseView {
        /**
         * 获取信息成功
         * @param json 请求返回数据
         * @param code 请求code
         */
        void onSuccess(CJSON json, String code);

        /**
         * 获取信息失败
         * @param msg 错误消息
         * @param code 请求code
         */
        void onError(String msg, String code);
    }
}
