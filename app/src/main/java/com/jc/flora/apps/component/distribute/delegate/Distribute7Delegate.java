package com.jc.flora.apps.component.distribute.delegate;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.apps.scene.login.projects.Login4Activity;

/**
 * Created by shijincheng on 2018/12/16.
 */
public class Distribute7Delegate extends Fragment{

    private static final Class LOGIN_ACTIVITY_CLASS = Login4Activity.class;
    private static final int GOTO_LOGIN_REQUEST_CODE = 1;
    public static final int LOGIN_SUCCESS_RESULT_CODE = 1;
    public static final int LOGIN_CANCEL_RESULT_CODE = 2;

    private boolean mIsLogin;
    private Intent mIntent;
    private LoginCallback mLoginCallback;

//    private boolean mWillInterceptOnActivityCreated = false;

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitNowAllowingStateLoss();
//            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
            init(activity);
        }
    }

    private void init(AppCompatActivity activity){
        mIntent = new Intent(activity, LOGIN_ACTIVITY_CLASS);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (mWillInterceptOnActivityCreated){
//            mWillInterceptOnActivityCreated = false;
//            loginIntercept();
//        }
//    }

    public void loginIntercept(LoginCallback loginCallback){
        mLoginCallback = loginCallback;
//        if(getHost() == null){
//            mWillInterceptOnActivityCreated = true;
//            return;
//        }
        loginIntercept();
    }

    private void loginIntercept(){
        if(mIsLogin){
            if(mLoginCallback != null){
                mLoginCallback.isLoggedIn();
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
        if(mLoginCallback == null || requestCode != GOTO_LOGIN_REQUEST_CODE){
            return;
        }
        if(resultCode == LOGIN_SUCCESS_RESULT_CODE){
            mIsLogin = true;
            mLoginCallback.onLoginSuccess();
        }else if(resultCode == LOGIN_CANCEL_RESULT_CODE){
            mLoginCallback.onLoginCancel();
        }
    }

    public interface LoginCallback{
        void isLoggedIn();
        void onLoginSuccess();
        void onLoginCancel();
    }

}
