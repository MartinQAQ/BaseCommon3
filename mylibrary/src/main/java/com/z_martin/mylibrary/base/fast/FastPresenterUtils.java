package com.z_martin.mylibrary.base.fast;

import com.z_martin.mylibrary.base.helper.RxManager;
import com.z_martin.mylibrary.base.helper.callback.MyConsumer;
import com.z_martin.mylibrary.base.helper.callback.MyConsumerCallBack;
import com.z_martin.mylibrary.base.helper.callback.MyThrowable;
import com.z_martin.mylibrary.base.helper.callback.MyThrowableCallBack;
import com.z_martin.mylibrary.utils.CJSON;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2018/10/22 10:40
 * @ version: 1.0
 */
public class FastPresenterUtils {

    public static void getData(final FastContract.IView mIView, FastContract.IModel mIModel, RxManager mRxManager, Map<String, Object> params, String path, final boolean isShowLoading) {
        if (mIView == null || mIModel == null) return;
        if(isShowLoading) mIView.showDialog();
        mRxManager.register(mIModel.get(params, path).subscribe(new MyConsumer<>(new MyConsumerCallBack<CJSON>() {
            @Override
            public void onSuccess(CJSON request, String code) {
                if (isShowLoading) mIView.hideDialog();
//                ViseLog.e(request);
                mIView.onSuccess(request, code);
            }

            @Override
            public void onError(String msg, String code) {
                if (isShowLoading) mIView.hideDialog();
                mIView.onError(msg, code);
            }
        }, path), new MyThrowable(new MyThrowableCallBack() {
            @Override
            public void throwable(Throwable throwable) {
                if (isShowLoading) mIView.hideDialog();
                mIView.showNetworkError(throwable.getMessage());
            }
        })));
    }

    public static void postData(final FastContract.IView mIView, FastContract.IModel mIModel, RxManager mRxManager, RequestBody params, String path, final boolean isShowLoading) {
        if (mIView == null || mIModel == null) return;
        if(isShowLoading) mIView.showDialog();
        mRxManager.register(mIModel.post(params, path).subscribe(new MyConsumer<>(new MyConsumerCallBack<CJSON>() {
            @Override
            public void onSuccess(CJSON request, String code) {
                if (isShowLoading) mIView.hideDialog();
//                ViseLog.e(request);
                mIView.onSuccess(request, code);
            }

            @Override
            public void onError(String msg, String code) {
                if (isShowLoading) mIView.hideDialog();
                mIView.onError(msg, code);
            }
        }, path), new MyThrowable(new MyThrowableCallBack() {
            @Override
            public void throwable(Throwable throwable) {
                if (isShowLoading) mIView.hideDialog();
                mIView.showNetworkError(throwable.getMessage());
            }
        })));
    }
}
