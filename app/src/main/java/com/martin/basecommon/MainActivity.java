package com.martin.basecommon;


import android.os.Bundle;

import com.vise.log.ViseLog;
import com.z_martin.mylibrary.annotations.ContentView;
import com.z_martin.mylibrary.base.activity.BaseActivity;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    protected void initView(Bundle savedInstanceState) {
        ViseLog.e("asas");
    }
}
