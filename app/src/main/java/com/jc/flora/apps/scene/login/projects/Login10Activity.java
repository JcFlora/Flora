package com.jc.flora.apps.scene.login.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.jc.flora.R;
import com.jc.flora.apps.scene.login.api.GetVerCodeResponse;
import com.jc.flora.apps.scene.login.api.LoginMockApi;
import com.jc.flora.apps.scene.login.api.LoginResponse;
import com.jc.flora.apps.scene.login.delegate.CheckVerCodeDelegate;
import com.jc.flora.apps.scene.login.delegate.InputClearDelegate;
import com.jc.flora.apps.scene.login.delegate.LoginEnabledDelegate;
import com.jc.flora.apps.scene.login.delegate.PhoneNumberInputDelegate;
import com.jc.flora.apps.scene.login.delegate.PwdInputDelegate;
import com.jc.flora.apps.scene.login.delegate.PwdToggleDelegate;
import com.jc.flora.apps.scene.login.delegate.RxGetVerCodeDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/8/25.
 */
public class Login10Activity extends AppCompatActivity {

    public static final int LOGIN_SUCCESS_RESULT_CODE = 1;
    public static final int LOGIN_CANCEL_RESULT_CODE = 2;

    // 手机号输入控件
    private EditText mEtPhoneNumber;
    // 密码输入控件
    private EditText mEtPwd;
    /** 验证码输入控件 */
    private EditText mEtVerCode;
    /** 验证码按钮 */
    private TextView mBtnVerCode;
    // 登录按钮
    private TextView mBtnLogin;
    // 注册按钮
    private TextView mBtnRegister;
    // 重置密码按钮
    private TextView mBtnResetPwd;
    // 手机号输入清空按钮
    private ImageView mBtnPhoneNumberClear;
    // 密码输入清空按钮
    private ImageView mBtnPwdClear;
    // 验证码输入清空按钮
    private ImageView mBtnVerCodeClear;
    // 明密文切换按钮
    private AppCompatCheckBox mCbPwdToggle;

    /** 手机号填写业务管理 */
    private PhoneNumberInputDelegate mPhoneNumberInputDelegate;
    /** 密码填写业务管理 */
    private PwdInputDelegate mPwdInputDelegate;
    /** 获取验证码业务管理 */
    private RxGetVerCodeDelegate mRxGetVerCodeDelegate;
    /** 验证验证码业务管理 */
    private CheckVerCodeDelegate mCheckVerCodeDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("验证码2(使用RxJava)");
        setContentView(R.layout.activity_login9);
        findViews();
        initViews();
        initDelegate();
    }

    private void findViews() {
        mEtPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mEtVerCode = (EditText) findViewById(R.id.et_ver_code);
        mBtnVerCode = (TextView) findViewById(R.id.btn_ver_code);
        mBtnLogin = (TextView) findViewById(R.id.btn_login);
        mBtnRegister = (TextView) findViewById(R.id.btn_register);
        mBtnResetPwd = (TextView) findViewById(R.id.btn_reset_pwd);
        mBtnPhoneNumberClear = (ImageView) findViewById(R.id.btn_phone_number_clear);
        mBtnPwdClear = (ImageView) findViewById(R.id.btn_pwd_clear);
        mBtnVerCodeClear = (ImageView) findViewById(R.id.btn_ver_code_clear);
        mCbPwdToggle = (AppCompatCheckBox) findViewById(R.id.cb_pwd_toggle);
    }

    private void initViews(){
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 验证版本号并登录
                checkVerCodeAndLogin();
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastDelegate.show(Login10Activity.this,"功能暂未实现");
            }
        });
        mBtnResetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastDelegate.show(Login10Activity.this,"功能暂未实现");
            }
        });
    }

    private void initDelegate() {
        mPhoneNumberInputDelegate = new PhoneNumberInputDelegate(this);
        mPhoneNumberInputDelegate.setEtPhoneNumber(mEtPhoneNumber);
        mPwdInputDelegate = new PwdInputDelegate(this);
        mPwdInputDelegate.setEtPwd(mEtPwd);
        // 调用以下方法实现按钮点击清空输入内容
        InputClearDelegate.init(mEtPhoneNumber, mBtnPhoneNumberClear);
        InputClearDelegate.init(mEtPwd, mBtnPwdClear);
        InputClearDelegate.init(mEtVerCode, mBtnVerCodeClear);
        // 调用这个方法实现按钮点击切换密码明密文
        PwdToggleDelegate.init(mEtPwd, mCbPwdToggle);
        // 调用这个方法实现控制登录按钮是否可用
        LoginEnabledDelegate.setLoginEnabled(mEtPhoneNumber, mEtPwd, mEtVerCode, mBtnLogin);

        // 创建获取验证码业务管理
        mRxGetVerCodeDelegate = new RxGetVerCodeDelegate(this);
        // 给该管理器配置验证码按钮
        mRxGetVerCodeDelegate.setBtnVerCode(mBtnVerCode);
        // 给该管理器配置手机号填写业务管理
        mRxGetVerCodeDelegate.setPhoneNumberInputDelegate(mPhoneNumberInputDelegate);
        // 设置获取验证码成功后的回调
        mRxGetVerCodeDelegate.setGetVerCodeListener(new RxGetVerCodeDelegate.OnGetVerCodeSuccessListener() {
            @Override
            public void onGetVerCodeSuccess(GetVerCodeResponse response) {
                ToastDelegate.show(Login10Activity.this, response.msg);
            }
        });
        // 开始验证码业务的准备工作
        mRxGetVerCodeDelegate.ready();

        // 创建验证验证码业务管理
        mCheckVerCodeDelegate = new CheckVerCodeDelegate(this);
        // 给该管理器配置验证码输入控件
        mCheckVerCodeDelegate.setEtVerCode(mEtVerCode);
        // 给该管理器配置手机号填写业务管理
        mCheckVerCodeDelegate.setPhoneNumberInputDelegate(mPhoneNumberInputDelegate);
    }

    /** 验证版本号并登录 */
    private void checkVerCodeAndLogin(){
        mCheckVerCodeDelegate.checkVerCode(new CheckVerCodeDelegate.OnCheckVerCodeSuccessListener() {
            @Override
            public void onCheckVerCodeSuccess() {
                //登录
                login();
            }
        });
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
                    ToastDelegate.show(Login10Activity.this, response.msg);
                }
            }
        }).sendRequest(phoneNumber, pwd);
    }

    @Override
    public void onBackPressed() {
        setResult(LOGIN_CANCEL_RESULT_CODE);
        super.onBackPressed();
        // 界面销毁时，取消掉验证码的倒计时任务
        mRxGetVerCodeDelegate.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 界面销毁时，取消掉验证码的倒计时任务
        mRxGetVerCodeDelegate.stop();
    }

}