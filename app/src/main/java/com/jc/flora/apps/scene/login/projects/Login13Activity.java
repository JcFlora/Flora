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
import com.jc.flora.apps.scene.login.delegate.PhoneNumberInputDelegate;
import com.jc.flora.apps.scene.login.delegate.PwdInputDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/8/23.
 */
public class Login13Activity extends AppCompatActivity {

    public static final int LOGIN_SUCCESS_RESULT_CODE = 1;
    public static final int LOGIN_CANCEL_RESULT_CODE = 2;

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

    /** 手机号填写业务管理 */
    private PhoneNumberInputDelegate mPhoneNumberInputDelegate;
    /** 密码填写业务管理 */
    private PwdInputDelegate mPwdInputDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用官方TextInputLayout实现");
        setContentView(R.layout.activity_login13);
        findViews();
        initViews();
        initDelegate();
    }

    private void findViews() {
        mEtPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mBtnLogin = (TextView) findViewById(R.id.btn_login);
        mBtnRegister = (TextView) findViewById(R.id.btn_register);
        mBtnResetPwd = (TextView) findViewById(R.id.btn_reset_pwd);
    }

    private void initViews(){
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastDelegate.show(Login13Activity.this,"功能暂未实现");
            }
        });
        mBtnResetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastDelegate.show(Login13Activity.this,"功能暂未实现");
            }
        });
    }

    private void initDelegate() {
        mPhoneNumberInputDelegate = new PhoneNumberInputDelegate(this);
        mPhoneNumberInputDelegate.setEtPhoneNumber(mEtPhoneNumber);
        mPwdInputDelegate = new PwdInputDelegate(this);
        mPwdInputDelegate.setEtPwd(mEtPwd);
    }

    private void login() {
        // 手机号或密码不合法，直接中断
        if (!mPhoneNumberInputDelegate.isPhoneNumberOk() || !mPwdInputDelegate.isPwdOk()){
            return;
        }
        String phoneNumber = mPhoneNumberInputDelegate.getPhoneNumber();
        String pwd = mPwdInputDelegate.getPwd();
        new LoginMockApi(this, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                if(response.success){
                    setResult(LOGIN_SUCCESS_RESULT_CODE);
                    finish();
                }else{
                    ToastDelegate.show(Login13Activity.this, response.msg);
                }
            }
        }).sendRequest(phoneNumber, pwd);
    }

    @Override
    public void onBackPressed() {
        setResult(LOGIN_CANCEL_RESULT_CODE);
        super.onBackPressed();
    }

}