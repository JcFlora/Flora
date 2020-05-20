package com.jc.flora.apps.component.permit.projects;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jc.flora.R;
import com.jc.flora.apps.component.permit.delegate.CameraPermitDelegate;
import com.jc.flora.apps.component.permit.delegate.RecordPermitDelegate;
import com.jc.flora.apps.component.permit.delegate.WriteSdPermitDelegate;

/**
 * Created by Samurai on 2020/5/20.
 */
public class Permit4Activity extends AppCompatActivity {

    private RecordPermitDelegate mRecordPermitDelegate;
    private WriteSdPermitDelegate mWriteSdPermitDelegate;
    private CameraPermitDelegate mCameraPermitDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("点击事件触发申请单个固定权限");
        setContentView(R.layout.activity_permit4);
    }

    public void checkRecordPermission(View v){
        requestRecordPermission();
    }

    public void checkWriteSdPermission(View v){
        requestWriteSdPermission();
    }

    public void checkCameraPermission(View v){
        requestCameraPermission();
    }

    /**
     * 申请录音权限
     */
    private void requestRecordPermission(){
        mRecordPermitDelegate = new RecordPermitDelegate();
        mRecordPermitDelegate.setOnRequestPermissionsCallback(new RecordPermitDelegate.OnRequestPermissionsCallback() {
            @Override
            public void onGranted(int flag) {
                if(flag == RecordPermitDelegate.HAS_PERMISSION){
                    Toast.makeText(Permit4Activity.this,"已经有录音权限了",Toast.LENGTH_SHORT).show();
                }else if(flag == RecordPermitDelegate.GRANTED_IN_APP){
                    Toast.makeText(Permit4Activity.this,"录音权限申请成功",Toast.LENGTH_SHORT).show();
                }else if(flag == RecordPermitDelegate.GRANTED_FROM_SETTINGS){
                    Toast.makeText(Permit4Activity.this,"设置里录音权限申请成功",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onDenied(int flag) {
                showRecordPermitDialog();
            }
        });
        mRecordPermitDelegate.addToActivity(this, "recordPermit");
        mRecordPermitDelegate.requestPermission();
    }

    /**
     * 申请写Sd卡权限
     */
    private void requestWriteSdPermission(){
        mWriteSdPermitDelegate = new WriteSdPermitDelegate();
        mWriteSdPermitDelegate.setOnRequestPermissionsCallback(new WriteSdPermitDelegate.OnRequestPermissionsCallback() {
            @Override
            public void onGranted(int flag) {
                if(flag == WriteSdPermitDelegate.HAS_PERMISSION){
                    Toast.makeText(Permit4Activity.this,"已经有Sd卡权限了",Toast.LENGTH_SHORT).show();
                }else if(flag == WriteSdPermitDelegate.GRANTED_IN_APP){
                    Toast.makeText(Permit4Activity.this,"Sd卡权限申请成功",Toast.LENGTH_SHORT).show();
                }else if(flag == WriteSdPermitDelegate.GRANTED_FROM_SETTINGS){
                    Toast.makeText(Permit4Activity.this,"设置里Sd卡权限申请成功",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onDenied(int flag) {
                showWriteSdPermitDialog();
            }
        });
        mWriteSdPermitDelegate.addToActivity(this, "writeSdPermit");
        mWriteSdPermitDelegate.requestPermission();
    }

    /**
     * 申请相机权限
     */
    private void requestCameraPermission(){
        mCameraPermitDelegate = new CameraPermitDelegate();
        mCameraPermitDelegate.setOnRequestPermissionsCallback(new CameraPermitDelegate.OnRequestPermissionsCallback() {
            @Override
            public void onGranted(int flag) {
                if(flag == CameraPermitDelegate.HAS_PERMISSION){
                    Toast.makeText(Permit4Activity.this,"已经有相机权限了",Toast.LENGTH_SHORT).show();
                }else if(flag == CameraPermitDelegate.GRANTED_IN_APP){
                    Toast.makeText(Permit4Activity.this,"相机权限申请成功",Toast.LENGTH_SHORT).show();
                }else if(flag == CameraPermitDelegate.GRANTED_FROM_SETTINGS){
                    Toast.makeText(Permit4Activity.this,"设置里相机权限申请成功",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onDenied(int flag) {
                showCameraPermitDialog();
            }
        });
        mCameraPermitDelegate.addToActivity(this, "cameraPermit");
        mCameraPermitDelegate.requestPermission();
    }

    /**
     * 展示录音权限开启对话框
     */
    private void showRecordPermitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("开启录音权限才能继续使用此功能");
        builder.setPositiveButton("去开启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mRecordPermitDelegate.gotoAppDetailSettings();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 展示Sd卡权限开启对话框
     */
    private void showWriteSdPermitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("开启存储权限才能继续使用此功能");
        builder.setPositiveButton("去开启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mWriteSdPermitDelegate.gotoAppDetailSettings();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 展示相机权限开启对话框
     */
    private void showCameraPermitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("开启相机权限才能继续使用此功能");
        builder.setPositiveButton("去开启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mCameraPermitDelegate.gotoAppDetailSettings();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}