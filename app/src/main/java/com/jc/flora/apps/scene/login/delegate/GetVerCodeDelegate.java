package com.jc.flora.apps.scene.login.delegate;

import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.jc.flora.R;
import com.jc.flora.apps.component.request.volley.L;
import com.jc.flora.apps.scene.login.api.GetVerCodeMockApi;
import com.jc.flora.apps.scene.login.api.GetVerCodeResponse;

/**
 * Created by Samurai on 2017/8/25.
 */
public class GetVerCodeDelegate {

    /** 验证码按钮文字：验证码 */
    private static final String BTN_VER_CODE_TEXT = "验证码";
    /** 按钮可用状态背景 */
    private static final int BG_RES_ENABLED = R.drawable.app_bg_btn_accent;
    /** 按钮不可用状态背景 */
    private static final int BG_RES_UNABLE = R.drawable.app_bg_btn_unable;
    /** 验证码获取倒计时时长 */
    private static final long VER_CODE_COUNT_DOWN_TIME = 30000;
    /** 验证码获取倒计时刷新间隔 */
    private static final long VER_CODE_COUNT_DOWN_INTERVAL = 1000;

    private AppCompatActivity mActivity;
    /** 验证码按钮 */
    private TextView mBtnVerCode;
    /** 手机号填写业务管理 */
    private PhoneNumberInputDelegate mPhoneNumberInputDelegate;
    /** 验证码获取成功后的监听 */
    private OnGetVerCodeSuccessListener mGetVerCodeListener;

    public GetVerCodeDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    /**
     * 配置验证码按钮
     *
     * @param btnVerCode 验证码按钮
     */
    public void setBtnVerCode(TextView btnVerCode) {
        mBtnVerCode = btnVerCode;
    }

    /**
     * 配置手机号填写业务管理
     *
     * @param delegate 手机号填写业务管理
     */
    public void setPhoneNumberInputDelegate(PhoneNumberInputDelegate delegate) {
        mPhoneNumberInputDelegate = delegate;
    }

    public void setGetVerCodeListener(OnGetVerCodeSuccessListener l) {
        mGetVerCodeListener = l;
    }

    /** 验证码业务的准备工作 */
    public void ready() {
        if (mBtnVerCode == null || mPhoneNumberInputDelegate == null)
            return;
        // 设置验证码按钮的点击事件
        mBtnVerCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取验证码
                getVerCode();
            }
        });
    }

    /** 获取验证码 */
    private void getVerCode() {
        // 手机号码不合法，直接返回
        if (!mPhoneNumberInputDelegate.isPhoneNumberOk())
            return;
        final String phoneNumber = mPhoneNumberInputDelegate.getPhoneNumber();
        // 按钮设置为不可用
        setBtnEnabled(false);
        // 启动倒计时，倒计时结束后点击按钮可以再次获取验证码
        mCountDownTimer.start();
        // 发起获取验证码的请求
        requestGetVerCode(phoneNumber);
    }

    /**
     * 设置按钮是否可用
     *
     * @param enabled 是否可用
     */
    private void setBtnEnabled(boolean enabled) {
        mBtnVerCode.setText(enabled ? BTN_VER_CODE_TEXT : "");
        mBtnVerCode.setEnabled(enabled);
        mBtnVerCode.setBackgroundResource(enabled ? BG_RES_ENABLED : BG_RES_UNABLE);
    }

    /** 验证码倒计时工具 */
    private CountDownTimer mCountDownTimer = new CountDownTimer(
            VER_CODE_COUNT_DOWN_TIME, VER_CODE_COUNT_DOWN_INTERVAL) {
        @Override
        public void onTick(long millisUntilFinished) {
            // 每隔1秒刷新一次验证码按钮上的时间
            mBtnVerCode.setText((millisUntilFinished / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            // 按钮设置为可用
            setBtnEnabled(true);
            // 取消倒计时任务
            mCountDownTimer.cancel();
        }
    };

    /**
     * 发起请求：获取验证码
     *
     * @param phoneNumber 手机号
     */
    private void requestGetVerCode(String phoneNumber) {
        new GetVerCodeMockApi(mActivity, new Response.Listener<GetVerCodeResponse>() {
            @Override
            public void onResponse(GetVerCodeResponse response) {
                onGetVerCodeResponse(response);
            }
        }).sendRequest(phoneNumber);
    }

    /**
     * 获取验证码成功后的回调
     *
     * @param response 响应实体
     */
    private void onGetVerCodeResponse(GetVerCodeResponse response) {
        if (response == null)
            return;
        L.w("getVerCode", response.msg);
        // 如果有回调事件，响应回调事件
        if (response.success && mGetVerCodeListener != null)
            mGetVerCodeListener.onGetVerCodeSuccess(response);
    }

    /** 验证码逻辑停止，在Activity的onDestroy（）方法中调用 */
    public void stop() {
        // 取消倒计时任务
        mCountDownTimer.cancel();
    }

    /** 验证码获取成功后的监听 */
    public interface OnGetVerCodeSuccessListener {
        /** 验证码获取成功后的监听 */
        void onGetVerCodeSuccess(GetVerCodeResponse response);
    }

}