package com.jc.flora.apps.component.distribute.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.distribute.delegate.Distribute7Delegate;
import com.jc.flora.apps.scene.login.delegate.LoginDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2018/12/16.
 */
public class Distribute7Activity extends AppCompatActivity {

    private static final String FRAGMENTS_TAG = "android:fragments";
    private static final String SUPPORT_FRAGMENTS_TAG = "android:support:fragments";

    private TextView mTvLoginStatus;
    private Button mBtnCheckLogin;
    private Button mBtnLogout;
    private Distribute7Delegate mLoginDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 由于页面中使用了Fragment作为业务分发，为了防止后台内存释放后，fragment从缓存中恢复时成员变量为空
        // 特地去掉fragment的自动缓存，这样后台内存释放后，fragment进行重新加载，不会有成员变量为空的问题
        if(savedInstanceState != null){
            savedInstanceState.putParcelable(FRAGMENTS_TAG, null);
            savedInstanceState.putParcelable(SUPPORT_FRAGMENTS_TAG, null);
        }
        super.onCreate(savedInstanceState);
        setTitle("处理Fragment代理异步加载引起的问题");
        setContentView(R.layout.activity_distribute7);
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
                ToastDelegate.show(Distribute7Activity.this, "注销账号");
                mTvLoginStatus.setText("登录状态：未登录");
                mBtnCheckLogin.setVisibility(View.VISIBLE);
                mBtnLogout.setVisibility(View.GONE);
            }
        });
    }

    private void initDelegate(){
        mLoginDelegate = new Distribute7Delegate();
        mLoginDelegate.addToActivity(this,"loginDelegate");
    }

    private void loginIntercept(){
        mLoginDelegate.loginIntercept(new Distribute7Delegate.LoginCallback() {
            @Override
            public void isLoggedIn() {
                mTvLoginStatus.setText("登录状态：已登录");
                mBtnCheckLogin.setVisibility(View.GONE);
                mBtnLogout.setVisibility(View.VISIBLE);
            }
            @Override
            public void onLoginSuccess() {
                ToastDelegate.show(Distribute7Activity.this, "登录成功");
                mTvLoginStatus.setText("登录状态：已登录");
                mBtnCheckLogin.setVisibility(View.GONE);
                mBtnLogout.setVisibility(View.VISIBLE);
            }
            @Override
            public void onLoginCancel() {
                ToastDelegate.show(Distribute7Activity.this, "取消登录");
            }
        });
    }

}