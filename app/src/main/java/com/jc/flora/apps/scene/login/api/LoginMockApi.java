package com.jc.flora.apps.scene.login.api;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/5/19.
 */
public class LoginMockApi implements Response.ErrorListener{

    /** 当前界面 */
    private AppCompatActivity mActivity;
    /** 返回成功的响应 */
    private Response.Listener<LoginResponse> mListener;
    /** 返回失败的响应 */
    private Response.ErrorListener mErrorListener;

    public LoginMockApi(AppCompatActivity activity, Response.Listener<LoginResponse> l) {
        mActivity = activity;
        mListener = l;
    }

    public LoginMockApi(AppCompatActivity activity, Response.Listener<LoginResponse> l, Response.ErrorListener el) {
        mActivity = activity;
        mListener = l;
        mErrorListener = el;
    }

    public void sendRequest(String phoneNumber, String pwd){
        //Mock
        boolean isPhoneOk = "13312345678".equals(phoneNumber);
        boolean isPwdOk = "123456".equals(pwd);
        if (isPhoneOk && isPwdOk) {
            mListener.onResponse(new LoginResponse(true, "登录成功"));
        } else {
            mListener.onResponse(new LoginResponse(false, "用户名或密码错误"));
        }
    }

    public void sendRequest(String phoneNumber, String pwd, String verifyCode) {
        //Mock
        boolean isPhoneOk = "13312345678".equals(phoneNumber);
        boolean isPwdOk = "123456".equals(pwd);
        boolean isVerifyCodeOk = "1234".equals(verifyCode);
        if (!isVerifyCodeOk) {
            mListener.onResponse(new LoginResponse(false, "验证码错误"));
        } else if (!isPhoneOk || !isPwdOk) {
            mListener.onResponse(new LoginResponse(false, "用户名或密码错误"));
        } else {
            mListener.onResponse(new LoginResponse(true, "登录成功"));
        }
    }

    /** 返回失败的默认响应 */
    public void onErrorResponse(VolleyError error) {
        if (mActivity != null) {
//            // 关闭Loading框
//            mActivity.hideLoadingDialog();
            // 弹出错误提示
            ToastDelegate.show(mActivity,"获取数据出错，请稍后再试");
        }
    }

}
