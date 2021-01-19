package com.z_martin.mylibrary.base.activity;

import android.content.Intent;
import android.os.Bundle;

import com.z_martin.mylibrary.base.app.IBaseView;

import androidx.annotation.NonNull;

/**
 * BaseActivity接口
 */
public interface IBaseActivity extends IBaseView {
    /**
     * 跳往新的Activity
     * @param clz 要跳往的Activity
     */
    void startNewActivity(@NonNull Class<?> clz);

    /**
     * 跳往新的Activity
     * @param clz 要跳往的Activity
     * @param intent 意图
     */
    void startNewActivity(@NonNull Class<?> clz, Intent intent);

    /**
     * 跳往新的Activity
     * @param clz    要跳往的Activity
     * @param bundle 携带的bundle数据
     */
    void startNewActivity(@NonNull Class<?> clz, Bundle bundle);

    /**
     * 跳往新的Activity
     * @param clz         要跳转的Activity
     * @param bundle      携带的bundle数据
     * @param requestCode requestCode
     */
    void startNewActivityForResult(@NonNull Class<?> clz, Bundle bundle, int requestCode);
}
