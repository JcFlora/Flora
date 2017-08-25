package com.jc.flora.apps.scene.login.delegate;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jc.flora.R;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

/**
 * 控制登录按钮是否可用（使用RxJava）
 * compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
 * Created by Samurai on 2017/8/24.
 */
public class LoginEnabledDelegate {

    /** 按钮可用状态背景 */
    private static final int BG_RES_ENABLED = R.drawable.app_bg_btn_accent;
    /** 按钮不可用状态背景 */
    private static final int BG_RES_UNABLE = R.drawable.app_bg_btn_unable;

    public static void setLoginEnabled(EditText etPhoneNumber, EditText etPwd, final View btnLogin) {
        Observable<CharSequence> phoneNumber = RxTextView.textChanges(etPhoneNumber);
        Observable<CharSequence> pwd = RxTextView.textChanges(etPwd);
        Observable.combineLatest(phoneNumber, pwd, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(@NonNull CharSequence phoneNumber, @NonNull CharSequence pwd) throws Exception {
                return !TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(pwd);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean enabled) throws Exception {
                btnLogin.setEnabled(enabled);
                int bgRes = enabled ? BG_RES_ENABLED : BG_RES_UNABLE;
                btnLogin.setBackgroundResource(bgRes);
            }
        });
    }

    public static void setLoginEnabled(EditText etPhoneNumber, EditText etPwd, EditText etVerifyCode, final View btnLogin) {
        Observable<CharSequence> phoneNumber = RxTextView.textChanges(etPhoneNumber);
        Observable<CharSequence> pwd = RxTextView.textChanges(etPwd);
        Observable<CharSequence> verifyCode = RxTextView.textChanges(etVerifyCode);
        Observable.combineLatest(phoneNumber, pwd, verifyCode, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(@NonNull CharSequence phoneNumber, @NonNull CharSequence pwd, @NonNull CharSequence verifyCode) throws Exception {
                return !TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(verifyCode);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean enabled) throws Exception {
                btnLogin.setEnabled(enabled);
                int bgRes = enabled ? BG_RES_ENABLED : BG_RES_UNABLE;
                btnLogin.setBackgroundResource(bgRes);
            }
        });
    }

}