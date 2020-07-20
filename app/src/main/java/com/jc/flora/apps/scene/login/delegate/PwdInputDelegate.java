package com.jc.flora.apps.scene.login.delegate;

import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/5/22.
 */
public class PwdInputDelegate {

    /** 判断密码是否合法的正则 */
    private static String REGULAR_PWD = "^1[7358][0-9]{9}$";

    /** 当前界面 */
    private AppCompatActivity mActivity;
    /** 密码输入控件 */
    private EditText mEtPwd;

    /**
     * 手机号填写业务管理
     *
     * @param activity
     */
    public PwdInputDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    /**
     * 配置密码输入控件
     *
     * @param etPwd 密码输入控件
     */
    public void setEtPwd(EditText etPwd) {
        mEtPwd = etPwd;
    }

    /**
     * 判断密码是否合法
     *
     * @return 密码是否合法
     */
    public boolean isPwdOk() {
//        Pattern pattern = Pattern.compile(REGULAR_PWD);
//        boolean isOk = pattern.matcher(getPwd()).matches();
        String pwd = getPwd();
        boolean isOk = !TextUtils.isEmpty(pwd) && pwd.length() >= 6 && pwd.length() <= 32;
        if (!isOk) {
            ToastDelegate.show(mActivity, "密码不合法，请重新输入");
        }
        return isOk;
    }

    /**
     * 获取密码
     *
     * @return 密码
     */
    public String getPwd() {
        return mEtPwd.getText().toString().trim();
    }
}
