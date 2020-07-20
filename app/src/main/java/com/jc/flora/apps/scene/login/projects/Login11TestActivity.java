package com.jc.flora.apps.scene.login.projects;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.login.delegate.LoginStatusDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2020/6/28.
 */
public class Login11TestActivity extends AppCompatActivity {

    private TextView mTvLoginStatus;
    private Button mBtnCheckLogin;
    private Button mBtnGotoNext;
    private Button mBtnLogout;
    private LoginStatusDelegate mLoginStatusDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用登录状态监听器实现多页面登录状态同步");
        setContentView(R.layout.activity_login11_test);
        findViews();
        initViews();
        initDelegate();
    }

    private void findViews(){
        mTvLoginStatus = (TextView) findViewById(R.id.tv_login_status);
        mBtnCheckLogin = (Button) findViewById(R.id.btn_check_login);
        mBtnGotoNext = (Button) findViewById(R.id.btn_goto_next);
        mBtnLogout = (Button) findViewById(R.id.btn_logout);
    }

    private void initViews(){
        mBtnCheckLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginStatusDelegate.loginIntercept();
            }
        });
        mBtnGotoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login11TestActivity.this, Login11Test2Activity.class));
            }
        });
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginStatusDelegate.logout();
                ToastDelegate.show(Login11TestActivity.this, "注销账号");
            }
        });
        refreshUiByLoginStatus(LoginStatusDelegate.isLogin());
    }

    private void initDelegate(){
        mLoginStatusDelegate = new LoginStatusDelegate();
        mLoginStatusDelegate.setLoginStatusListener(new LoginStatusDelegate.LoginStatusListener() {
            @Override
            public void onLoginSuccess() {
                refreshUiByLoginStatus(true);
            }

            @Override
            public void onLogoutSuccess() {
                refreshUiByLoginStatus(false);
            }
        });
        mLoginStatusDelegate.addToActivity(this,"loginDelegate");
    }

    private void refreshUiByLoginStatus(boolean isLogin){
        mTvLoginStatus.setText(isLogin ? "登录状态：已登录" : "登录状态：未登录");
        mBtnCheckLogin.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        mBtnLogout.setVisibility(isLogin ? View.VISIBLE : View.GONE);
    }

}