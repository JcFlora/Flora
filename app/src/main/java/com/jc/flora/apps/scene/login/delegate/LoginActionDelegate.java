package com.jc.flora.apps.scene.login.delegate;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.apps.scene.login.projects.Login4Activity;

/**
 * 登录动作拦截器
 * Created by shijincheng on 2017/5/22.
 */
public class LoginActionDelegate extends Fragment{

    private static final Class LOGIN_ACTIVITY_CLASS = Login4Activity.class;
    private static final int GOTO_LOGIN_REQUEST_CODE = 1;
    public static final int LOGIN_SUCCESS_RESULT_CODE = Login4Activity.LOGIN_SUCCESS_RESULT_CODE;
    public static final int LOGIN_CANCEL_RESULT_CODE = Login4Activity.LOGIN_CANCEL_RESULT_CODE;

    private boolean mIsLogin;
    private Intent mIntent;
    private LoginActionCallback mLoginActionCallback;

    private boolean mWillInterceptOnActivityCreated = false;

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
            init(activity);
        }
    }

    private void init(AppCompatActivity activity){
        mIntent = new Intent(activity, LOGIN_ACTIVITY_CLASS);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mWillInterceptOnActivityCreated){
            mWillInterceptOnActivityCreated = false;
            loginIntercept();
        }
    }

    public void loginIntercept(LoginActionCallback loginActionCallback){
        mLoginActionCallback = loginActionCallback;
        if(getHost() == null){
            mWillInterceptOnActivityCreated = true;
            return;
        }
        loginIntercept();
    }

    private void loginIntercept(){
        if(mIsLogin){
            if(mLoginActionCallback != null){
                mLoginActionCallback.isLoggedIn();
            }
        }else{
            startActivityForResult(mIntent, GOTO_LOGIN_REQUEST_CODE);
        }
    }

    public void setLogin(boolean isLogin){
        mIsLogin = isLogin;
    }

    public boolean isLogin(){
        return mIsLogin;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mLoginActionCallback == null || requestCode != GOTO_LOGIN_REQUEST_CODE){
            return;
        }
        if(resultCode == LOGIN_SUCCESS_RESULT_CODE){
            mIsLogin = true;
            mLoginActionCallback.onLoginSuccess();
        }else if(resultCode == LOGIN_CANCEL_RESULT_CODE){
            mLoginActionCallback.onLoginCancel();
        }
    }

    public interface LoginActionCallback {
        void isLoggedIn();
        void onLoginSuccess();
        void onLoginCancel();
    }

    public static class LoginActionCallbackAdapter implements LoginActionCallback {
        public void isLoggedIn(){}
        public void onLoginSuccess(){}
        public void onLoginCancel(){}
    }

}