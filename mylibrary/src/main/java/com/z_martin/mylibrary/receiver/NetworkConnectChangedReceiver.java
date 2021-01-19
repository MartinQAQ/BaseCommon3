package com.z_martin.mylibrary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.z_martin.mylibrary.entity.NetworkChangeEvent;
import com.z_martin.mylibrary.utils.NetManagerUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2018/11/28 11:22
 * @ version: 1.0
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkConnectChanged";
    private boolean isConnected;
    
    @Override
    public void onReceive(Context context, Intent intent) {
        //**判断当前的网络连接状态是否可用*/
        isConnected = NetManagerUtils.isOpenNetwork(context);
        Log.d(TAG, "onReceive: 当前网络 " + isConnected);
        EventBus.getDefault().post(new NetworkChangeEvent(isConnected));
    }
}
