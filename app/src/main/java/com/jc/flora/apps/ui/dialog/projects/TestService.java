package com.jc.flora.apps.ui.dialog.projects;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jc.flora.apps.ui.dialog.delegate.SystemDialogDelegate;

/**
 *
 * 需要权限：<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
 *
 * Created by shijincheng on 2017/3/8.
 */
public class TestService extends Service {

    private Handler mHandler = new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 模拟处理耗时任务完毕后，弹出对话框
        new Handler().postDelayed(mShowSystemDialogTask, 3000);
        return super.onStartCommand(intent, flags, startId);
    }

    private Runnable mShowSystemDialogTask = new Runnable() {
        @Override
        public void run() {
            SystemDialogDelegate.showDialog(TestService.this);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

}