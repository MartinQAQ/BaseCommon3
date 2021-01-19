package com.z_martin.mylibrary.base.fast;

import com.z_martin.mylibrary.base.helper.callback.MyConsumer;
import com.z_martin.mylibrary.base.helper.callback.MyConsumerCallBack;
import com.z_martin.mylibrary.base.helper.callback.MyThrowable;
import com.z_martin.mylibrary.base.helper.callback.MyThrowableCallBack;
import com.z_martin.mylibrary.utils.CJSON;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2018/10/22 10:40
 * @ version: 1.0
 */
public class FastPresenter extends FastContract.Presenter {

    @Override
    public void get(Map<String, Object> params, final String path) {
       get(params, path, true);
    }

    @Override
    public void get(Map<String, Object> params, String path, final boolean isShowLoading) {
        FastPresenterUtils.getData(mIView, mIModel, mRxManager, params, path, isShowLoading);
    }


    @Override
    public void post(RequestBody params, String path) {
        post(params, path, true);
    }

    @Override
    public void post(RequestBody params, String path, boolean isShowLoading) {
        FastPresenterUtils.postData(mIView, mIModel, mRxManager, params, path, isShowLoading);
    }

    @Override
    public void uploadFile(String path, String filePath, Map<String, Object> map, String fileName, boolean isCompress) {
        if (isCompress) {
            Luban.with(mContext)
                    .load(filePath)                     //传人要压缩的图片
                    .ignoreBy(100) // 不压缩的阈值，单位为K
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                        }
                        @Override
                        public void onSuccess(File file) {
                            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                            MultipartBody.Part body = MultipartBody.Part.createFormData(fileName, file.getName(), requestFile);
                            RequestBody body2 = new MultipartBody.Builder()
                                    .addFormDataPart("fileName", fileName).build();
                            if (mIView == null || mIModel == null) return;
                            mIView.showDialog();
                            mRxManager.register(mIModel.uploadFile(path, body, map).subscribe(new MyConsumer<>(new MyConsumerCallBack<CJSON>() {
                                @Override
                                public void onSuccess(CJSON request, String code) {
                                    if (mIView == null) return;
                                    mIView.hideDialog();
                                    mIView.onSuccess(request, code);
                                }

                                @Override
                                public void onError(String msg, String code) {
                                    if (mIView == null) return;
                                    mIView.hideDialog();
                                    mIView.onError(msg, code);
                                }
                            }, path), new MyThrowable(new MyThrowableCallBack() {
                                @Override
                                public void throwable(Throwable throwable) {
                                    if (mIView == null) return;
                                    mIView.hideDialog();
                                    mIView.showNetworkError(throwable.getMessage());
                                }
                            })));
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mIView == null) return;
                            mIView.onError("上传失败", path);
                        }
                    }).launch();    //启动压缩
        } else {
            File file = new File(filePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData(fileName, file.getName(), requestFile);
            if (mIView == null || mIModel == null) return;
            mIView.showDialog();
            mRxManager.register(mIModel.uploadFile(path, body, map).subscribe(new MyConsumer<>(new MyConsumerCallBack<CJSON>() {
                @Override
                public void onSuccess(CJSON request, String code) {
                    if (mIView == null) return;
                    mIView.hideDialog();
                    mIView.onSuccess(request, code);
                }

                @Override
                public void onError(String msg, String code) {
                    if (mIView == null) return;
                    mIView.hideDialog();
                    mIView.onError(msg, code);
                }
            }, path), new MyThrowable(new MyThrowableCallBack() {
                @Override
                public void throwable(Throwable throwable) {
                    if (mIView == null) return;
                    mIView.hideDialog();
                    mIView.showNetworkError(throwable.getMessage());
                }
            })));
        }
    }


    @Override
    public void uploadFile(String path, String filePath, String fileName, boolean isCompress) {
        uploadFile(path, filePath, fileName, isCompress, true);
    }

    @Override
    public void uploadFile(String path, String filePath, String fileName, boolean isCompress, boolean isShowLoading) {
        if (isCompress) {
            Luban.with(mContext)
                    .load(filePath)                     //传人要压缩的图片
                    .ignoreBy(100) // 不压缩的阈值，单位为K
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                        }
                        @Override
                        public void onSuccess(File file) {
                            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                            MultipartBody.Part body = MultipartBody.Part.createFormData(fileName, file.getName(), requestFile);
                            if (mIView == null || mIModel == null) return;
                            if (isShowLoading) mIView.showDialog();
                            mRxManager.register(mIModel.uploadFile(path, body).subscribe(new MyConsumer<>(new MyConsumerCallBack<CJSON>() {
                                @Override
                                public void onSuccess(CJSON request, String code) {
                                    if (mIView == null) return;
                                    if (isShowLoading) mIView.hideDialog();
                                    mIView.onSuccess(request, code);
                                }

                                @Override
                                public void onError(String msg, String code) {
                                    if (mIView == null) return;
                                    if (isShowLoading) mIView.hideDialog();
                                    mIView.onError(msg, code);
                                }
                            }, path), new MyThrowable(new MyThrowableCallBack() {
                                @Override
                                public void throwable(Throwable throwable) {
                                    if (mIView == null) return;
                                    if (isShowLoading) mIView.hideDialog();
                                    mIView.showNetworkError(throwable.getMessage());
                                }
                            })));
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mIView == null) return;
                            mIView.onError("上传失败", path);
                        }
                    }).launch();    //启动压缩
        } else {
            File file = new File(filePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData(fileName, file.getName(), requestFile);
            if (mIView == null || mIModel == null) return;
            mIView.showDialog();
            mRxManager.register(mIModel.uploadFile(path, body).subscribe(new MyConsumer<>(new MyConsumerCallBack<CJSON>() {
                @Override
                public void onSuccess(CJSON request, String code) {
                    if (mIView == null) return;
                    if (isShowLoading) mIView.hideDialog();
                    mIView.onSuccess(request, code);
                }

                @Override
                public void onError(String msg, String code) {
                    if (mIView == null) return;
                    if (isShowLoading) mIView.hideDialog();
                    mIView.onError(msg, code);
                }
            }, path), new MyThrowable(new MyThrowableCallBack() {
                @Override
                public void throwable(Throwable throwable) {
                    if (mIView == null) return;
                    if (isShowLoading) mIView.hideDialog();
                    mIView.showNetworkError(throwable.getMessage());
                }
            })));
        }
    }
}
