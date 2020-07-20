package com.jc.flora.apps.scene.login.api;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/8/25.
 */
public class CheckVerCodeMockApi implements Response.ErrorListener{

    /** 当前界面 */
    private AppCompatActivity mActivity;
    /** 返回成功的响应 */
    private Response.Listener<CheckVerCodeResponse> mListener;
    /** 返回失败的响应 */
    private Response.ErrorListener mErrorListener;

    public CheckVerCodeMockApi(AppCompatActivity activity, Response.Listener<CheckVerCodeResponse> l) {
        mActivity = activity;
        mListener = l;
    }

    public CheckVerCodeMockApi(AppCompatActivity activity, Response.Listener<CheckVerCodeResponse> l, Response.ErrorListener el) {
        mActivity = activity;
        mListener = l;
        mErrorListener = el;
    }

    public void sendRequest(String phoneNumber, String verCode){
        //Mock
        boolean isPhoneOk = "13312345678".equals(phoneNumber);
        boolean isVerCodeOk = "1234".equals(verCode);
        if (isPhoneOk && isVerCodeOk) {
            mListener.onResponse(new CheckVerCodeResponse(true, "验证成功"));
        } else {
            mListener.onResponse(new CheckVerCodeResponse(false, "验证码错误"));
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