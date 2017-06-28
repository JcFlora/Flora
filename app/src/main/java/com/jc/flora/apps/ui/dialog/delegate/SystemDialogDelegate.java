package com.jc.flora.apps.ui.dialog.delegate;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.jc.flora.apps.component.deviceinfo.DeviceUtil;

/**
 * 需要权限：uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"
 *
 * Created by shijincheng on 2017/3/8.
 */
public class SystemDialogDelegate extends Fragment{

    private static final String SYSTEM_DIALOG_POSITIVE_BUTTON_CLICK = "SYSTEM_DIALOG_POSITIVE_BUTTON_CLICK";
    private static final String SYSTEM_DIALOG_CANCEL = "SYSTEM_DIALOG_CANCEL";
    private static final int REQUEST_CODE_OVERLAY_PERMISSION = 22;

    private AppCompatActivity mActivity;
    private Class<? extends Service> mTargetService;
    private SystemDialogReceiver mSystemDialogReceiver;
    private IntentFilter mIntentFilter;

    public SystemDialogDelegate setTargetService(Class<? extends Service> targetService) {
        mTargetService = targetService;
        return this;
    }

    public SystemDialogDelegate setSystemDialogReceiver(SystemDialogReceiver receiver){
        mSystemDialogReceiver = receiver;
        return this;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity == null){
            return;
        }
        mActivity = activity;
        activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
    }

    @Override
    public void onStart() {
        super.onStart();
        registerSystemDialogReceiver();
    }

    private void registerSystemDialogReceiver() {
        if(mSystemDialogReceiver == null){
            return;
        }
        if (mIntentFilter == null){
            mIntentFilter = new IntentFilter();
            mIntentFilter.addAction("SYSTEM_DIALOG_POSITIVE_BUTTON_CLICK");
            mIntentFilter.addAction("SYSTEM_DIALOG_CANCEL");
        }
        LocalBroadcastManager.getInstance(mActivity).registerReceiver(mSystemDialogReceiver, mIntentFilter);
//        mActivity.registerReceiver(mSystemDialogReceiver, mIntentFilter);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void startTargetService(){
        if(mTargetService == null){
            return;
        }
//        mActivity.startService(new Intent(mActivity.getApplicationContext(), mTargetService));
        if (DeviceUtil.isSystemVersionAfterM() && !Settings.canDrawOverlays(mActivity)) {
            requestAlertWindowPermission();
        } else {
            mActivity.startService(new Intent(mActivity.getApplicationContext(), mTargetService));
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestAlertWindowPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + mActivity.getPackageName()));
        startActivityForResult(intent, REQUEST_CODE_OVERLAY_PERMISSION);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_OVERLAY_PERMISSION && Settings.canDrawOverlays(mActivity)){
            mActivity.startService(new Intent(mActivity.getApplicationContext(), mTargetService));
        }
    }

    public static void showDialog(final Service service){
        // 注意SystemDialog只能使用android.app.AlertDialog，不能使用support包的AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(service.getApplicationContext());
        builder.setTitle("提示");
        builder.setMessage("有最新版本可以下载了");
        builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                LocalBroadcastManager.getInstance(service).sendBroadcast(new Intent(SYSTEM_DIALOG_POSITIVE_BUTTON_CLICK));
//                service.sendBroadcast(new Intent(SYSTEM_DIALOG_POSITIVE_BUTTON_CLICK));
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                LocalBroadcastManager.getInstance(service).sendBroadcast(new Intent(SYSTEM_DIALOG_CANCEL));
//                service.sendBroadcast(new Intent(SYSTEM_DIALOG_CANCEL));
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        dialog.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopTargetService();
        unregisterSystemDialogReceiver();
    }

    private void stopTargetService(){
        if(mTargetService != null){
            mActivity.stopService(new Intent(getActivity().getApplicationContext(), mTargetService));
        }
    }

    private void unregisterSystemDialogReceiver() {
        if(mSystemDialogReceiver != null){
            LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(mSystemDialogReceiver);
//            mActivity.unregisterReceiver(mSystemDialogReceiver);
        }
    }

    public static abstract class SystemDialogReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            switch (intent.getAction()) {
                case SYSTEM_DIALOG_POSITIVE_BUTTON_CLICK:
                    onDialogPositiveButtonClick(intent.getExtras());
                    break;
                case SYSTEM_DIALOG_CANCEL:
                    onDialogCancelClick(intent.getExtras());
                    break;
                default:
                    break;
            }
        }

        protected abstract void onDialogPositiveButtonClick(Bundle bundle);

        protected abstract void onDialogCancelClick(Bundle bundle);
    }

}
