package com.jc.flora.apps.component.permit.delegate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * 申请单个固定权限+申请结果回调
 * Created by Shijincheng on 2020/5/19.
 */

public class PermitDelegate2 extends Fragment {

    /** 已经有权限 */
    public static final int HAS_PERMISSION = 1;
    /** 用户在App中开启了权限 */
    public static final int GRANTED_IN_APP = 2;
    /** 用户在设置中开启了权限 */
    public static final int GRANTED_FROM_SETTINGS = 3;
    /** 用户在App中否决了权限 */
    public static final int DENIED_IN_APP = 1;
    /** 用户在设置中否决了权限 */
    public static final int DENIED_FROM_SETTINGS = 2;

    private static final int RECORD_REQUEST_CODE = 1;
    private static final int SETTINGS_REQUEST_CODE = 2;

    /** 是否被拒绝（勾选了不再询问） */
    private static boolean sIsDenied = false;
    /** 申请结果回调 */
    private OnRequestPermissionsCallback mOnRequestPermissionsCallback;

    public void setOnRequestPermissionsCallback(OnRequestPermissionsCallback callback) {
        mOnRequestPermissionsCallback = callback;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitNowAllowingStateLoss();
        }
    }

    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted(HAS_PERMISSION);
        } else {
            //申请录音权限
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RECORD_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(GRANTED_IN_APP);
            } else if (!shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                //用户勾选了不再询问，提示用户手动打开权限
                onPermissionDenied();
            }
        }
    }

    private void onPermissionGranted(int flag){
        sIsDenied = false;
        if(mOnRequestPermissionsCallback != null){
            mOnRequestPermissionsCallback.onGranted(flag);
        }
    }

    private void onPermissionDenied(){
        // 需要区分是否是第一次拒绝
        if(!sIsDenied){
            sIsDenied = true;
            // 第一次拒绝，这次拒绝不会跳转到权限设置
            if(mOnRequestPermissionsCallback != null){
                mOnRequestPermissionsCallback.onDenied(DENIED_IN_APP);
            }
            return;
        }
        //todo 让用户决定是否要去、什么时候去设置界面
        gotoAppDetailSettings();
    }

    private void gotoAppDetailSettings(){
        // 以前拒绝过，跳到权限设置
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
        startActivityForResult(intent,SETTINGS_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SETTINGS_REQUEST_CODE){
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)
                    == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(GRANTED_FROM_SETTINGS);
            } else {
                if(mOnRequestPermissionsCallback != null){
                    mOnRequestPermissionsCallback.onDenied(DENIED_FROM_SETTINGS);
                }
            }
        }
    }

    /**
     * 申请结果回调
     */
    public interface OnRequestPermissionsCallback {
        void onGranted(int flag);
        void onDenied(int flag);
    }

}