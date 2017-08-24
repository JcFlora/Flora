package com.jc.flora.apps.scene.login.delegate;

import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * 控制密码可见
 * Created by Samurai on 2017/8/24.
 */
public class PwdToggleDelegate {

    public static void init(final EditText etPwd, AppCompatCheckBox cbPwdToggle) {
        cbPwdToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // display password text, for example "123456"
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password, display "."
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                etPwd.setSelection(etPwd.getText().length());
            }
        });
    }

}
