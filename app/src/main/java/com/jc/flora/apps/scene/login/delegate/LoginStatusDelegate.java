package com.jc.flora.apps.scene.login.delegate;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.jc.flora.apps.scene.login.projects.Login4Activity;

/**
 * 多页面登录状态监听器（实现多页面登录状态同步）
 * Created by shijincheng on 2020/6/24.
 */
public class LoginStatusDelegate extends Fragment {

    private static final String TAG = "LoginStatusDelegate";

    /**
     * 登录目标页面的Class类型
     */
    private static final Class LOGIN_ACTIVITY_CLASS = Login4Activity.class;

    /**
     * 登录的发起标记
     */
    private static final int GOTO_LOGIN_REQUEST_CODE = 1;
    /**
     * 登录成功的响应标记
     */
    private static final int LOGIN_SUCCESS_RESULT_CODE = Login4Activity.LOGIN_SUCCESS_RESULT_CODE;
    /**
     * 登录取消的响应标记
     */
    private static final int LOGIN_CANCEL_RESULT_CODE = Login4Activity.LOGIN_CANCEL_RESULT_CODE;
    /**
     * 模拟全局登录状态
     */
    private static boolean sIsLogin;
    /**
     * 跳转到登录目标页面的Intent
     */
    private Intent mIntent;
    /**
     * 登录状态回调
     */
    private LoginStatusListener mLoginStatusListener;

    /**
     * 添加到Activity
     *
     * @param activity
     * @param tag
     */
    public void addToActivity(AppCompatActivity activity, String tag) {
        if (activity != null) {
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitNowAllowingStateLoss();
            init(activity);
        }
    }

    /**
     * 添加到Fragment
     *
     * @param fragment
     * @param tag
     */
    public void addToFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            fragment.getChildFragmentManager().beginTransaction().add(this, tag).commitNowAllowingStateLoss();
            init(fragment.getActivity());
        }
    }

    /**
     * 登录页面登录成功后调用此方法
     *
     * @param activity
     */
    public static void loginSuccess(Activity activity) {
        activity.setResult(LOGIN_SUCCESS_RESULT_CODE);
        activity.finish();
    }

    /**
     * 登录页面登录取消后调用此方法
     *
     * @param activity
     */
    public static void loginCancel(Activity activity) {
        activity.setResult(LOGIN_CANCEL_RESULT_CODE);
        activity.finish();
    }

    /**
     * 模拟登录状态检测，实际项目中使用User对象实现
     * @return
     */
    public static boolean isLogin(){
        return sIsLogin;
    }

    /**
     * 初始化
     *
     * @param activity
     */
    private void init(Activity activity) {
        mIntent = new Intent(activity, LOGIN_ACTIVITY_CLASS);
    }

    /**
     * 设置状态监听回调
     *
     * @param loginStatusListener
     */
    public void setLoginStatusListener(LoginStatusListener loginStatusListener) {
        mLoginStatusListener = loginStatusListener;
    }

    /**
     * 发起登录拦截
     */
    public void loginIntercept() {
        if (sIsLogin) {
            if (mLoginStatusListener != null) {
                mLoginStatusListener.onLoginSuccess();
            }
        } else {
            startActivityForResult(mIntent, GOTO_LOGIN_REQUEST_CODE);
        }
    }

    /**
     * 模拟注销功能，实际项目中使用User对象实现
     */
    public void logout(){
        sIsLogin = false;
        // 发送本地广播，通知所有页面登出成功
        sendLogoutBroadcast(getContext(), true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != GOTO_LOGIN_REQUEST_CODE) {
            return;
        }
        // 发送本地广播，通知所有页面登录成功
        sendLoginBroadcast(getContext(), resultCode == LOGIN_SUCCESS_RESULT_CODE);
    }

    /** 登录状态广播的Action */
    private static final String LOGIN_FEEDBACK = "LOGIN_FEEDBACK";
    /** 登录成功广播的携带参数 */
    private static final String LOGIN_SUCCESS = "loginSuccess";
    /** 登出成功广播的携带参数 */
    private static final String LOGOUT_SUCCESS = "logoutSuccess";
    /** 登录状态广播的IntentFilter */
    private IntentFilter mIntentFilter;

    /**
     * 发送登录成功的本地广播
     * @param context
     * @param isLoginSuccess
     */
    private void sendLoginBroadcast(Context context, boolean isLoginSuccess) {
        Intent intent = new Intent(LOGIN_FEEDBACK);
        intent.putExtra(LOGIN_SUCCESS, isLoginSuccess);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * 发送登出成功的本地广播
     * @param context
     * @param isLogoutSuccess
     */
    private void sendLogoutBroadcast(Context context, boolean isLogoutSuccess) {
        Intent intent = new Intent(LOGIN_FEEDBACK);
        intent.putExtra(LOGOUT_SUCCESS, isLogoutSuccess);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerLoginReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterLoginReceiver();
    }

    /**
     * 注册登录监听的接收器
     */
    private void registerLoginReceiver() {
        if (mLoginReceiver == null) {
            return;
        }
        if (mIntentFilter == null) {
            mIntentFilter = new IntentFilter();
            mIntentFilter.addAction(LOGIN_FEEDBACK);
        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mLoginReceiver, mIntentFilter);
    }

    /**
     * 反注册登录监听的接收器
     */
    private void unregisterLoginReceiver() {
        if (mLoginReceiver != null) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mLoginReceiver);
        }
    }

    /**
     * 登录监听的接收器
     */
    private BroadcastReceiver mLoginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && mLoginStatusListener != null && LOGIN_FEEDBACK.equals(intent.getAction())) {
                boolean isLoginSuccess = intent.getBooleanExtra(LOGIN_SUCCESS, false);
                if (isLoginSuccess) {
                    sIsLogin = true;
                    mLoginStatusListener.onLoginSuccess();
                }
                boolean isLogoutSuccess = intent.getBooleanExtra(LOGOUT_SUCCESS, false);
                if (isLogoutSuccess) {
                    mLoginStatusListener.onLogoutSuccess();
                }
            }
        }
    };

    /**
     * 登录状态回调
     */
    public interface LoginStatusListener {
        void onLoginSuccess();
        void onLogoutSuccess();
    }

}