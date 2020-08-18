package com.jc.flora.apps.scene.identity.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/7/21.
 */
public class Identity1Activity extends AppCompatActivity {

    private static final int GOTO_LOGIN_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("普通用户登录验证");
        setContentView(R.layout.activity_identity1);
    }

    public void gotoLogin(View v){
        startActivityForResult(new Intent(this, TestLogin1Activity.class), GOTO_LOGIN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode != GOTO_LOGIN_REQUEST_CODE){
            return;
        }
        if(resultCode == TestLogin1Activity.LOGIN_SUCCESS_RESULT_CODE){
            ToastDelegate.show(this, "登录成功");
        }else if(resultCode == TestLogin1Activity.LOGIN_CANCEL_RESULT_CODE){
            ToastDelegate.show(this, "取消登录");
        }
    }

}