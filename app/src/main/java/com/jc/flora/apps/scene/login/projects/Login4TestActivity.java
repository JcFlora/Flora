package com.jc.flora.apps.scene.login.projects;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/3/1.
 */
public class Login4TestActivity extends AppCompatActivity {

    private static final int GOTO_LOGIN_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加单页面登录结果回调");
        setContentView(R.layout.activity_login4_test);
    }

    public void gotoLogin(View v){
        startActivityForResult(new Intent(this, Login4Activity.class), GOTO_LOGIN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode != GOTO_LOGIN_REQUEST_CODE){
            return;
        }
        if(resultCode == Login4Activity.LOGIN_SUCCESS_RESULT_CODE){
            ToastDelegate.show(this, "登录成功");
        }else if(resultCode == Login4Activity.LOGIN_CANCEL_RESULT_CODE){
            ToastDelegate.show(this, "取消登录");
        }
    }

}