package com.jc.flora.apps.scene.identity.lander;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 多页面登录状态同步器（合并单页面登录拦截）
 * Created by shijincheng on 2020/6/29.
 */
public class LoginStatusSyncExecutor extends Fragment {

    private static final String TAG = "LoginStatusSyncExecutor";

    /**
     * 登录目标页面的Class类型
     */
    private static Class<? extends Activity> sLoginActivityClass;
    /**
     * 登录状态数据源
     */
    private static LoginInfoDataSource sLoginInfoDataSource;
    /**
     * 登录的发起标记
     */
    private static final int GOTO_LOGIN_REQUEST_CODE = 1;
    /**
     * 登录成功的响应标记
     */
    private static final int LOGIN_SUCCESS_RESULT_CODE = 1;
    /**
     * 登录取消的响应标记
     */
    private static final int LOGIN_CANCEL_RESULT_CODE = 2;
    /**
     * 跳转到登录目标页面的Intent
     */
    private Intent mIntent;
    /**
     * 登录状态回调
     */
    private LoginStatusListener mLoginStatusListener;
    /**
     * 登录动作响应
     */
    private LoginActionCallback mLoginActionCallback;

    /**
     * 设置登录页面的Activity
     * @param loginActivityClass
     */
    static void setLoginActivityClass(Class<? extends Activity> loginActivityClass) {
        sLoginActivityClass = loginActivityClass;
    }

    /**
     * 设置登录状态信息读写的数据源
     * @param loginInfoDataSource
     */
    static void setLoginInfoDataSource(LoginInfoDataSource loginInfoDataSource) {
        sLoginInfoDataSource = loginInfoDataSource;
    }

    /**
     * 登录页面登录成功后调用此方法
     *
     * @param activity
     */
    static void loginSuccess(Activity activity) {
        // 发送本地广播，通知所有页面登录成功
        sendLoginBroadcast(activity, true);
        activity.setResult(LOGIN_SUCCESS_RESULT_CODE);
        activity.finish();
    }

    /**
     * 登录页面登录取消后调用此方法
     *
     * @param activity
     */
    static void loginCancel(Activity activity) {
        // 发送本地广播，通知所有页面登出成功
        sendLoginBroadcast(activity, false);
        activity.setResult(LOGIN_CANCEL_RESULT_CODE);
        activity.finish();
    }

    /**
     * 发送登录成功的本地广播
     * @param context
     * @param isLoginSuccess
     */
    private static void sendLoginBroadcast(Context context, boolean isLoginSuccess) {
        Intent intent = new Intent(LOGIN_FEEDBACK);
        intent.putExtra(LOGIN_SUCCESS, isLoginSuccess);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * 发送登出成功的本地广播
     * @param context
     * @param isLogoutSuccess
     */
    private static void sendLogoutBroadcast(Context context, boolean isLogoutSuccess) {
        Intent intent = new Intent(LOGIN_FEEDBACK);
        intent.putExtra(LOGOUT_SUCCESS, isLogoutSuccess);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * 创建登录状态同步器
     * @param activity
     * @param tag
     * @return
     */
    static LoginStatusSyncExecutor createExecutor(AppCompatActivity activity, String tag){
        LoginStatusSyncExecutor executor = (LoginStatusSyncExecutor) activity.getSupportFragmentManager().findFragmentByTag(tag);
        if(executor == null){
            executor = new LoginStatusSyncExecutor();
            executor.addToActivity(activity, tag);
        }
        return executor;
    }

    /**
     * 创建登录状态同步器
     * @param fragment
     * @param tag
     * @return
     */
    static LoginStatusSyncExecutor createExecutor(Fragment fragment, String tag){
        LoginStatusSyncExecutor executor = (LoginStatusSyncExecutor) fragment.getChildFragmentManager().findFragmentByTag(tag);
        if(executor == null){
            executor = new LoginStatusSyncExecutor();
            executor.addToFragment(fragment, tag);
        }
        return executor;
    }

    /**
     * 添加到Activity
     *
     * @param activity
     * @param tag
     */
    void addToActivity(AppCompatActivity activity, String tag) {
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
    void addToFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            fragment.getChildFragmentManager().beginTransaction().add(this, tag).commitNowAllowingStateLoss();
            init(fragment.getActivity());
        }
    }

    /**
     * 设置跳转到登录界面携带的数据
     * @param extras
     */
    public void putExtras(Bundle extras){
        mIntent.putExtras(extras);
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
     * 设置动作响应
     * 动作响应的代码在状态监听回调之后，
     * 动作响应应该只是当前页面触发登录动作之后的特定执行逻辑，
     * 不能包含状态监听回调里的逻辑
     * 否则会导致监听回调和动作响应都执行
     * @param loginActionCallback
     */
    public void setLoginActionCallback(LoginActionCallback loginActionCallback) {
        mLoginActionCallback = loginActionCallback;
    }

    /**
     * 发起登录拦截
     */
    public void loginIntercept() {
        if (sLoginInfoDataSource.readLoginStatus()) {
            if (mLoginStatusListener != null) {
                mLoginStatusListener.onLoginSuccess();
            }
            if(mLoginActionCallback != null){
                mLoginActionCallback.isLoggedIn();
            }
        } else {
            startActivityForResult(mIntent, GOTO_LOGIN_REQUEST_CODE);
        }
    }

    /**
     * 注销功能
     */
    public void logout(){
        // 发送本地广播，通知所有页面登出成功
        sendLogoutBroadcast(getContext(), true);
        // 触发动作响应，通知当前页面登出成功
        if(mLoginActionCallback != null){
            mLoginActionCallback.onLogoutSuccess();
        }
    }

    /**
     * 初始化
     *
     * @param activity
     */
    private void init(Activity activity) {
        mIntent = new Intent(activity, sLoginActivityClass);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != GOTO_LOGIN_REQUEST_CODE) {
            return;
        }
        // 触发登录动作响应，通知当前页面
        if(resultCode == LOGIN_SUCCESS_RESULT_CODE){
            mLoginActionCallback.onLoginSuccess();
        }else if(resultCode == LOGIN_CANCEL_RESULT_CODE){
            mLoginActionCallback.onLoginCancel();
        }
    }

    /** 登录状态广播的Action */
    private static final String LOGIN_FEEDBACK = "LOGIN_FEEDBACK";
    /** 登录成功广播的携带参数 */
    private static final String LOGIN_SUCCESS = "loginSuccess";
    /** 登出成功广播的携带参数 */
    private static final String LOGOUT_SUCCESS = "logoutSuccess";
    /** 登录状态广播的IntentFilter */
    private IntentFilter mIntentFilter;

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
                    mLoginStatusListener.onLoginSuccess();
                }
                boolean isLogoutSuccess = intent.getBooleanExtra(LOGOUT_SUCCESS, false);
                if (isLogoutSuccess) {
                    mLoginStatusListener.onLogoutSuccess();
                }
            }
        }
    };

}