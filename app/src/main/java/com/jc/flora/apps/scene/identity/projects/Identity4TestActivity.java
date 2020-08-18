package com.jc.flora.apps.scene.identity.projects;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.identity.lander.Lander;
import com.jc.flora.apps.scene.identity.lander.LoginActionCallback;
import com.jc.flora.apps.scene.identity.lander.LoginStatusListener;
import com.jc.flora.apps.scene.identity.lander.LoginStatusSyncExecutor;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/6/28.
 */
public class Identity4TestActivity extends AppCompatActivity {

    private TextView mTvLoginStatus;
    private Button mBtnCheckLogin;
    private Button mBtnGotoPrev;
    private Button mBtnLogout;
    private TextView mTvLoginLog;
    private LoginStatusSyncExecutor mLoginStatusSyncExecutor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("测试页面");
        setContentView(R.layout.activity_identity4_test);
        findViews();
        initViews();
        initDelegate();
    }

    private void findViews(){
        mTvLoginStatus = (TextView) findViewById(R.id.tv_login_status);
        mBtnCheckLogin = (Button) findViewById(R.id.btn_check_login);
        mBtnGotoPrev = (Button) findViewById(R.id.btn_goto_prev);
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
        mBtnGotoPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                mTvLoginLog.append("Identity4TestActivity触发了登录拦截，并且登录成功了");
            }

            @Override
            public void onLoginCancel() {
                mTvLoginLog.append("Identity4TestActivity触发了登录拦截，但是登录取消了");
            }

            @Override
            public void onLogoutSuccess() {
                mTvLoginLog.append("Identity4TestActivity触发了登出");
            }
        });
    }

    private void refreshUiByLoginStatus(boolean isLogin){
        mTvLoginStatus.setText(isLogin ? "登录状态：已登录" : "登录状态：未登录");
        mBtnCheckLogin.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        mBtnLogout.setVisibility(isLogin ? View.VISIBLE : View.GONE);
    }

}