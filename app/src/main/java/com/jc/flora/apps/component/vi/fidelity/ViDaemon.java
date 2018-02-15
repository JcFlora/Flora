package com.jc.flora.apps.component.vi.fidelity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 组件守护器.
 */
final class ViDaemon {

    /** 当前上下文，用于注册与反注册广播 */
    private Context mContext;
    /** 是否注册广播的标记 */
    private boolean mHasRegistered;

    /**
     * 组件守护器
     * @param context 当前上下文
     */
    ViDaemon(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 注册广播，监听系统配置变化
     */
    void registerReceiver() {
        if(mContext == null || mHasRegistered){
            return;
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
        mContext.registerReceiver(mReceiver, filter);
        mHasRegistered = true;
    }

    /**
     * 监听系统配置变化的广播接收器
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Fidelity.init(mContext);
        }
    };

    /**
     * 反注册广播
     */
    void unregisterReceiver() {
        if(mContext == null || !mHasRegistered){
            return;
        }
        mContext.unregisterReceiver(mReceiver);
        mHasRegistered = false;
    }

}