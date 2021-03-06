package com.jc.flora.apps.scene.identity.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.identity.lander.Lander;
import com.jc.flora.apps.scene.identity.lander.LoginActionCallback;
import com.jc.flora.apps.scene.identity.lander.LoginInfoDataSource;
import com.jc.flora.apps.scene.identity.lander.LoginStatusListener;
import com.jc.flora.apps.scene.identity.lander.LoginStatusSyncExecutor;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/7/21.
 */
public class Identity4Activity extends AppCompatActivity {

    private TextView mTvLoginStatus;
    private Button mBtnCheckLogin;
    private Button mBtnGotoNext;
    private Button mBtnLogout;
    private TextView mTvLoginLog;
    private LoginStatusSyncExecutor mLoginStatusSyncExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("合并单页面登录拦截和多页面登录状态同步，并封装为Lander框架");
        setContentView(R.layout.activity_identity4);
        findViews();
        initViews();
        initDelegate();
    }

    private void findViews(){
        mTvLoginStatus = (TextView) findViewById(R.id.tv_login_status);
        mBtnCheckLogin = (Button) findViewById(R.id.btn_check_login);
        mBtnGotoNext = (Button) findViewById(R.id.btn_goto_next);
        mBtnLogout = (Button) findViewById(R.id.btn_logout);
        mTvLoginLog = (TextView) findViewById(R.id.tv_login_log);
    }

    private void initViews(){
        mBtnCheckLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginStatusSyncExecutor.loginIntercept();
            }
        });
        mBtnGotoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Identity4Activity.this, Identity4TestActivity.class));
            }
        });
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestLogin3Activity.sIsLogin = false;
                mLoginStatusSyncExecutor.logout();
            }
        });
        refreshUiByLoginStatus(TestLogin3Activity.sIsLogin);
    }

    private void initDelegate(){
        // 初始化Lander框架，实际项目中，在Application的onCreate方法中调用
        Lander.init(TestLogin3Activity.class, new LoginInfoDataSource() {
            @Override
            public boolean readLoginStatus() {
                return TestLogin3Activity.sIsLogin;
            }
        });
        // 获取登录状态同步器
        mLoginStatusSyncExecutor = Lander.createExecutor(this, "loginStatusSyncExecutor");
        // 设置登录状态监听
        mLoginStatusSyncExecutor.setLoginStatusListener(new LoginStatusListener() {
            @Override
            public void onLoginSuccess() {
                refreshUiByLoginStatus(true);
            }

            @Override
            public void onLogoutSuccess() {
                refreshUiByLoginStatus(false);
            }
        });
        // 设置登录动作响应
        mLoginStatusSyncExecutor.setLoginActionCallback(new LoginActionCallback() {
            @Override
            public void isLoggedIn() {
            }

            @Override
            public void onLoginSuccess() {
                mTvLoginLog.append("Identity4Activity触发了登录拦截，并且登录成功了");
            }

            @Override
            public void onLoginCancel() {
                mTvLoginLog.append("Identity4Activity触发了登录拦截，但是登录取消了");
            }

            @Override
            public void onLogoutSuccess() {
                mTvLoginLog.append("Identity4Activity触发了登出");
            }
        });
    }

    private void refreshUiByLoginStatus(boolean isLogin){
        mTvLoginStatus.setText(isLogin ? "登录状态：已登录" : "登录状态：未登录");
        mBtnCheckLogin.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        mBtnLogout.setVisibility(isLogin ? View.VISIBLE : View.GONE);
    }

}