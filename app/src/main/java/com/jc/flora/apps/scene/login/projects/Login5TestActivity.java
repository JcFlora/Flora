package com.jc.flora.apps.scene.login.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.scene.login.delegate.LoginDelegate;

/**
 * Created by shijincheng on 2017/3/1.
 */
public class Login5TestActivity extends AppCompatActivity {

    private TextView mTvLoginStatus;
    private Button mBtnLogout;
    private LoginDelegate mLoginDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("登录检测+使用登录拦截器");
        setContentView(R.layout.activity_login5_test);
        findViews();
        initDelegate();
    }

    private void findViews(){
        mTvLoginStatus = (TextView) findViewById(R.id.tv_login_status);
        mBtnLogout = (Button) findViewById(R.id.btn_logout);
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginDelegate.setLogin(false);
                ToastDelegate.show(Login5TestActivity.this, "注销账号");
                mTvLoginStatus.setText("登录状态：未登录");
                mBtnLogout.setVisibility(View.GONE);
            }
        });
    }

    private void initDelegate(){
        mLoginDelegate = new LoginDelegate();
        mLoginDelegate.addToActivity(this,"loginDelegate");
        mLoginDelegate.loginIntercept(new LoginDelegate.LoginCallback() {
            @Override
            public void isLoggedIn() {
                mTvLoginStatus.setText("登录状态：已登录");
                mBtnLogout.setVisibility(View.VISIBLE);
            }
            @Override
            public void onLoginSuccess() {
                ToastDelegate.show(Login5TestActivity.this, "登录成功");
                mTvLoginStatus.setText("登录状态：已登录");
                mBtnLogout.setVisibility(View.VISIBLE);
            }
            @Override
            public void onLoginCancel() {
                ToastDelegate.show(Login5TestActivity.this, "取消登录");
            }
        });
    }

}