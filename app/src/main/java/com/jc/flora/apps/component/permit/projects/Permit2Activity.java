package com.jc.flora.apps.component.permit.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.jc.flora.apps.component.permit.delegate.PermitDelegate2;

/**
 * Created by Samurai on 2020/5/19.
 */
public class Permit2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("页面触发申请单个固定权限+申请结果回调");
        requestPermission();
    }

    private void requestPermission(){
        final PermitDelegate2 delegate = new PermitDelegate2();
        delegate.setOnRequestPermissionsCallback(new PermitDelegate2.OnRequestPermissionsCallback() {
            @Override
            public void onGranted(int flag) {
                if(flag == PermitDelegate2.HAS_PERMISSION){
                    Toast.makeText(Permit2Activity.this,"已经有录音权限了",Toast.LENGTH_SHORT).show();
                }else if(flag == PermitDelegate2.GRANTED_IN_APP){
                    Toast.makeText(Permit2Activity.this,"录音权限申请成功",Toast.LENGTH_SHORT).show();
                }else if(flag == PermitDelegate2.GRANTED_FROM_SETTINGS){
                    Toast.makeText(Permit2Activity.this,"设置里录音权限申请成功",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onDenied(int flag) {
                if(flag == PermitDelegate2.DENIED_IN_APP){
                    Toast.makeText(Permit2Activity.this,"录音权限已被禁止了",Toast.LENGTH_SHORT).show();
                }else if(flag == PermitDelegate2.DENIED_FROM_SETTINGS){
                    Toast.makeText(Permit2Activity.this,"录音权限依然还是被禁止",Toast.LENGTH_SHORT).show();
                }
            }
        });
        delegate.addToActivity(this,"permit");
        delegate.requestPermission();
    }

}