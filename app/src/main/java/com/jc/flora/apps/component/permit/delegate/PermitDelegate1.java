package com.jc.flora.apps.component.permit.delegate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * 申请单个固定权限
 * Created by Shijincheng on 2020/5/19.
 */

public class PermitDelegate1 extends Fragment {

    /** 应用内申请当前权限的请求码 */
    private static final int RECORD_REQUEST_CODE = 1;
    /** 去设置申请当前权限的请求码 */
    private static final int SETTINGS_REQUEST_CODE = 2;

    /** 是否被拒绝（勾选过不再询问） */
    private static boolean sIsDenied = false;

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitNowAllowingStateLoss();
        }
    }

    /**
     * 发起权限请求
     */
    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            // 已经有此权限
            // 权限状态：允许
            Toast.makeText(getActivity(),"已经有录音权限了",Toast.LENGTH_SHORT).show();
        } else {
            // 没有权限，申请录音权限
            // 权限状态：询问or禁止
            // 询问状态会弹对话框，禁止状态会直接回调onRequestPermissionsResult
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_REQUEST_CODE);
        }
    }

    /**
     * 权限请求的回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != RECORD_REQUEST_CODE || grantResults.length == 0) {
            return;
        }
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 用户同意授权
            // 权限状态：询问->允许
            sIsDenied = false;
            Toast.makeText(getActivity(), "录音权限申请成功", Toast.LENGTH_SHORT).show();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
            // 用户不同意授权，但没有勾选不再询问
            // 权限状态：询问->询问
            Toast.makeText(getActivity(), "录音权限被禁止了", Toast.LENGTH_SHORT).show();
        } else if (!sIsDenied) {
            // 用户不同意授权，并且勾选了不再询问，这次不会跳转到权限设置
            // 权限状态：询问->禁止（第一次）
            sIsDenied = true;
            Toast.makeText(getActivity(), "录音权限被禁止了", Toast.LENGTH_SHORT).show();
        } else {
            // 用户之前已经勾选过不再询问，跳到权限设置，提示用户手动打开权限
            // 权限状态：禁止->禁止（非第一次）
            //todo 让用户决定是否要去、什么时候去设置界面
            gotoAppDetailSettings();
        }
    }

    /**
     * 跳转到应用详情
     */
    private void gotoAppDetailSettings() {
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
                // 用户在设置里同意授权，回到应用页面
                // 权限状态：禁止->允许
                Toast.makeText(getActivity(),"设置里录音权限申请成功",Toast.LENGTH_SHORT).show();
            } else {
                // 用户在设置未操作或不同意授权，回到应用页面
                // 权限状态：禁止->询问or禁止
                Toast.makeText(getActivity(), "从设置回来，录音权限依然还是被禁止", Toast.LENGTH_SHORT).show();
            }
        }
    }

}