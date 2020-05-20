package com.jc.flora.apps.component.permit.projects;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jc.flora.apps.component.permit.delegate.PermitDelegate3;

/**
 * Created by Samurai on 2020/5/19.
 */
public class Permit3Activity extends AppCompatActivity {

    private PermitDelegate3 mPermitDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("拒绝后展示对话框进行提醒");
        requestPermission();
    }

    private void requestPermission(){
        mPermitDelegate = new PermitDelegate3();
        mPermitDelegate.setOnRequestPermissionsCallback(new PermitDelegate3.OnRequestPermissionsCallback() {
            @Override
            public void onGranted(int flag) {
                if(flag == PermitDelegate3.HAS_PERMISSION){
                    Toast.makeText(Permit3Activity.this,"已经有录音权限了",Toast.LENGTH_SHORT).show();
                }else if(flag == PermitDelegate3.GRANTED_IN_APP){
                    Toast.makeText(Permit3Activity.this,"录音权限申请成功",Toast.LENGTH_SHORT).show();
                }else if(flag == PermitDelegate3.GRANTED_FROM_SETTINGS){
                    Toast.makeText(Permit3Activity.this,"设置里录音权限申请成功",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onDenied(int flag) {
                showPermitDialog();
            }
        });
        mPermitDelegate.addToActivity(this, "permit");
        mPermitDelegate.requestPermission();
    }

    /**
     * 展示权限开启对话框
     */
    private void showPermitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("开启录音权限才能继续使用此功能");
        builder.setPositiveButton("去开启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mPermitDelegate.gotoAppDetailSettings();
            }
        });
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}