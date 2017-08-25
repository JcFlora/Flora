package com.jc.flora.apps.scene.login.delegate;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import com.android.volley.Response;
import com.jc.flora.apps.component.request.volley.L;
import com.jc.flora.apps.scene.login.api.CheckVerCodeMockApi;
import com.jc.flora.apps.scene.login.api.CheckVerCodeResponse;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Samurai on 2017/8/25.
 */
public class CheckVerCodeDelegate {

    private AppCompatActivity mActivity;
    /** 验证码输入控件 */
    private EditText mEtVerCode;
    /** 手机号填写业务管理 */
    private PhoneNumberInputDelegate mPhoneNumberInputDelegate;
    /** 验证码验证成功后的监听 */
    private OnCheckVerCodeSuccessListener mCheckVerCodeListener;

    public CheckVerCodeDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    /**
     * 配置验证码输入控件
     *
     * @param etVerCode 配置验证码输入控件
     */
    public void setEtVerCode(EditText etVerCode) {
        mEtVerCode = etVerCode;
    }

    /**
     * 配置手机号填写业务管理
     *
     * @param delegate 手机号填写业务管理
     */
    public void setPhoneNumberInputDelegate(PhoneNumberInputDelegate delegate) {
        mPhoneNumberInputDelegate = delegate;
    }

    /**
     * 验证验证码
     *
     * @param l 验证验证码成功后的回调
     */
    public void checkVerCode(OnCheckVerCodeSuccessListener l) {
        // 手机号码不合法，直接返回
        if (!mPhoneNumberInputDelegate.isPhoneNumberOk())
            return;
        // 验证码为空，直接返回
        if (isVerCodeEmpty())
            return;
        // 设置监听
        mCheckVerCodeListener = l;
        final String phoneNumber = mPhoneNumberInputDelegate.getPhoneNumber();
        // 发起验证验证码的请求
        requestCheckVerCode(phoneNumber, getInputVerCode());
    }

    /**
     * 判断验证码是否为空
     *
     * @return 验证码是否为空
     */
    private boolean isVerCodeEmpty() {
        boolean isEmpty = TextUtils.isEmpty(getInputVerCode());
        if (isEmpty)
            ToastDelegate.show(mActivity, "请填写验证码");
        return isEmpty;
    }

    /**
     * 获取当前输入的验证码
     *
     * @return 当前输入的验证码
     */
    private String getInputVerCode() {
        return mEtVerCode.getText().toString().trim();
    }

    /**
     * 发起请求：验证验证码
     *
     * @param phoneNumber 手机号
     * @param verCode     验证验证码
     */
    private void requestCheckVerCode(String phoneNumber, String verCode) {
        new CheckVerCodeMockApi(mActivity, new Response.Listener<CheckVerCodeResponse>() {
            @Override
            public void onResponse(CheckVerCodeResponse checkVerCodeResponse) {
                onCheckVerCodeResponse(checkVerCodeResponse);
            }
        }).sendRequest(phoneNumber, verCode);
    }

    /**
     * 验证码验证成功后的回调
     *
     * @param response 响应实体
     */
    private void onCheckVerCodeResponse(CheckVerCodeResponse response) {
        if (response == null)
            return;
        L.w("checkVerCode", response.msg);
        if (!response.success)
            // 验证失败，提示给用户
            ToastDelegate.show(mActivity, response.msg);
        else if (mCheckVerCodeListener != null)
            // 如果有回调事件，响应回调事件
            mCheckVerCodeListener.onCheckVerCodeSuccess();
    }

    /** 验证码验证成功后的监听 */
    public interface OnCheckVerCodeSuccessListener {
        /** 验证码获取成功后的监听 */
        void onCheckVerCodeSuccess();
    }

}