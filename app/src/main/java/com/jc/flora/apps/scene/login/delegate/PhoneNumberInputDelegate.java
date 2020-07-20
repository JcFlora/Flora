package com.jc.flora.apps.scene.login.delegate;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;

import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.util.regex.Pattern;

/**
 * Created by shijincheng on 2017/5/22.
 */
public class PhoneNumberInputDelegate {

    /** 判断手机号是否合法的正则 */
    public static final String REGULAR_PHONE_NUMBER = "^1[7358][0-9]{9}$";

    /** 当前界面 */
    private AppCompatActivity mActivity;
    /** 手机号输入控件 */
    private EditText mEtPhoneNumber;

    /**
     * 手机号填写业务管理
     *
     * @param activity
     */
    public PhoneNumberInputDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    /**
     * 配置手机号输入控件
     *
     * @param etPhoneNumber 手机号输入控件
     */
    public void setEtPhoneNumber(EditText etPhoneNumber) {
        mEtPhoneNumber = etPhoneNumber;
    }

    /**
     * 判断手机号是否合法
     *
     * @return 手机号是否合法
     */
    public boolean isPhoneNumberOk() {
        Pattern pattern = Pattern.compile(REGULAR_PHONE_NUMBER);
        boolean isOk = pattern.matcher(getPhoneNumber()).matches();
        if (!isOk) {
            ToastDelegate.show(mActivity, "手机号码不合法，请重新输入");
        }
        return isOk;
    }

    /**
     * 获取手机号
     *
     * @return 手机号
     */
    public String getPhoneNumber() {
        return mEtPhoneNumber.getText().toString().trim();
    }

}
