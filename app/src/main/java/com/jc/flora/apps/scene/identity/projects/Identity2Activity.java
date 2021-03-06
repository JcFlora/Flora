package com.jc.flora.apps.scene.identity.projects;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.identity.delegate.LoginActionDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/7/21.
 */
public class Identity2Activity extends AppCompatActivity {

    private TextView mTvLoginStatus;
    private Button mBtnCheckLogin;
    private Button mBtnLogout;
    private LoginActionDelegate mLoginDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("单页面登录检测+使用登录动作拦截器");
        setContentView(R.layout.activity_identity2);
        findViews();
        initViews();
        initDelegate();
        loginIntercept();
    }

    private void findViews(){
        mTvLoginStatus = (TextView) findViewById(R.id.tv_login_status);
        mBtnCheckLogin = (Button) findViewById(R.id.btn_check_login);
        mBtnLogout = (Button) findViewById(R.id.btn_logout);
    }

    private void initViews(){
        mBtnCheckLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginIntercept();
            }
        });
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginDelegate.setLogin(false);
                ToastDelegate.show(Identity2Activity.this, "注销账号");
                refreshUiByLoginStatus(false);
            }
        });
    }

    private void initDelegate(){
        mLoginDelegate = new LoginActionDelegate();
        mLoginDelegate.addToActivity(this,"loginDelegate");
    }

    private void loginIntercept(){
        mLoginDelegate.loginIntercept(new LoginActionDelegate.LoginActionCallback() {
            @Override
            public void isLoggedIn() {
                refreshUiByLoginStatus(true);
            }
            @Override
            public void onLoginSuccess() {
                ToastDelegate.show(Identity2Activity.this, "登录成功");
                refreshUiByLoginStatus(true);
            }
            @Override
            public void onLoginCancel() {
                ToastDelegate.show(Identity2Activity.this, "取消登录");
            }
        });
    }

    private void refreshUiByLoginStatus(boolean isLogin){
        mTvLoginStatus.setText(isLogin ? "登录状态：已登录" : "登录状态：未登录");
        mBtnCheckLogin.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        mBtnLogout.setVisibility(isLogin ? View.VISIBLE : View.GONE);
    }

}