package com.z_martin.mylibrary.base.helper.callback;

import android.content.Intent;

import com.vise.log.ViseLog;
import com.z_martin.mylibrary.base.app.AppConfig;
import com.z_martin.mylibrary.utils.AppUtils;
import com.z_martin.mylibrary.utils.CJSON;
import com.z_martin.mylibrary.utils.SpUtils;

import io.reactivex.functions.Consumer;

/**
 * @ describe: 自定义Consumer
 * @ author: Martin
 * @ createTime: 2018/10/21 0:19
 * @ version: 1.0
 */
public class MyConsumer<T> implements Consumer<T> {
    /** 回调 */
    private MyConsumerCallBack<T> mConsumer;
    /** 请求标识 */
    private String mFlag;

    public MyConsumer(MyConsumerCallBack<T> consumer, String flag) {
        this.mConsumer = consumer;
        this.mFlag = flag;
    }

    public MyConsumer(MyConsumerCallBack<T> myConsumerCallBack) {
    }

    @Override
    public void accept(T t) throws Exception {
//            ViseLog.d(t);
            if(CJSON.getStatus(t.toString()) == AppConfig.getTokenErrorStatus() || CJSON.getStatus(t.toString()) == AppConfig.getTokenErrorStatus2()) {
//                ToastUtils.showToast(CJSON.getMessage(t.toString()));
                if(!mFlag.equals("userAccount/thirdLoginOut")) {
                    ViseLog.e(mFlag);
                    AppConfig.setAuthorization("");
                    SpUtils.remove(AppUtils.getContext(), "user_info");
                    mConsumer.onError(CJSON.getMessage(t.toString()), mFlag);
                    AppUtils.getContext().startActivity(new Intent(AppUtils.getContext(), AppConfig.getTokenErrorActivity()));
                }else {
                    mConsumer.onSuccess(t, mFlag);
                }
            }else if(CJSON.getStatus(t.toString()) == AppConfig.getRequestSuccessStatus()) {
                mConsumer.onSuccess(t, mFlag);
            } else {
                mConsumer.onError(CJSON.getMessage(t.toString()), mFlag);
            }
    }
}
