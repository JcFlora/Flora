package com.jc.flora.apps.scene.login.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.jc.flora.R;
import com.jc.flora.apps.scene.login.api.LoginMockApi;
import com.jc.flora.apps.scene.login.api.LoginResponse;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/5/19.
 */
public class Login2Activity extends AppCompatActivity {

    // 手机号输入控件
    private EditText mEtPhoneNumber;
    // 密码输入控件
    private EditText mEtPwd;
    // 登录按钮
    private TextView mBtnLogin;
    // 注册按钮
    private TextView mBtnRegister;
    // 重置密码按钮
    private TextView mBtnResetPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("模拟接口校验");
        setContentView(R.layout.activity_login1);
        mEtPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mBtnLogin = (TextView) findViewById(R.id.btn_login);
        mBtnRegister = (TextView) findViewById(R.id.btn_register);
        mBtnResetPwd = (TextView) findViewById(R.id.btn_reset_pwd);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastDelegate.show(Login2Activity.this,"功能暂未实现");
            }
        });
        mBtnResetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastDelegate.show(Login2Activity.this,"功能暂未实现");
            }
        });
    }

    private void login() {
        new LoginMockApi(this, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                ToastDelegate.show(Login2Activity.this, response.msg);
            }
        }).sendRequest(mEtPhoneNumber.getText().toString().trim(), mEtPwd.getText().toString().trim());
    }

}