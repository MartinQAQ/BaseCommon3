package com.martin.basecommon;

import com.z_martin.mylibrary.base.app.AppConfig;
import com.z_martin.mylibrary.base.app.BaseApp;

/**
 * @description:
 * @project name: BaseCommon3.0
 * @author: xiaoming723@126.com
 * @createTime: 2020/6/17 1:35
 * @version: 1.0
 */
public class App extends BaseApp {
    @Override
    public void onCreate() {
        AppConfig.init(MainActivity.class, MainActivity.class, "http://www.baidu.com", "", 0, 0, 0);

        super.onCreate();
    }
}
