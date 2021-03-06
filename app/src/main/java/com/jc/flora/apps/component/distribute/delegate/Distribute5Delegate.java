package com.jc.flora.apps.component.distribute.delegate;

import android.os.CountDownTimer;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Shijincheng on 2018/12/16.
 */
public class Distribute5Delegate extends Fragment{

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
    private Distribute1Delegate mPhoneNumberInputDelegate;

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
    public void setPhoneNumberInputDelegate(Distribute1Delegate delegate) {
        mPhoneNumberInputDelegate = delegate;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            mActivity = activity;
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
            ready();
        }
    }

    /** 验证码业务的准备工作 */
    private void ready() {
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
        // 按钮设置为不可用
        setBtnEnabled(false);
        // 启动倒计时，倒计时结束后点击按钮可以再次获取验证码
        mCountDownTimer.start();
        // 模拟验证码获取成功
        ToastDelegate.show(mActivity,"验证码是：1234");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 取消倒计时任务
        mCountDownTimer.cancel();
    }

}