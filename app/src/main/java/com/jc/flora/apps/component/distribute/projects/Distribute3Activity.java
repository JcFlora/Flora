package com.jc.flora.apps.component.distribute.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.distribute.delegate.Distribute1Delegate;
import com.jc.flora.apps.component.distribute.delegate.Distribute3Delegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Shijincheng on 2018/10/7.
 */
public class Distribute3Activity extends AppCompatActivity {

    /** 分发出去的手机号填写业务管理 */
    private Distribute1Delegate mPhoneNumberInputDelegate;
    /** 分发出去的获取验证码业务管理 */
    private Distribute3Delegate mGetVerCodeDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("通过回调实现业务代理之间的关联");
        setContentView(R.layout.activity_distribute2);
        initPhoneNumberInput();
        initGetVerCode();
    }

    private void initPhoneNumberInput(){
        EditText etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        mPhoneNumberInputDelegate = new Distribute1Delegate(this);
        mPhoneNumberInputDelegate.setEtPhoneNumber(etPhoneNumber);
    }

    private void initGetVerCode() {
        TextView btnVerCode = (TextView) findViewById(R.id.btn_ver_code);
        mGetVerCodeDelegate = new Distribute3Delegate(this);
        mGetVerCodeDelegate.setBtnVerCode(btnVerCode);
        mGetVerCodeDelegate.setOnPhoneNumberCheckInterceptor(new Distribute3Delegate.OnPhoneNumberCheckInterceptor() {
            @Override
            public boolean isPhoneNumberOk() {
                return mPhoneNumberInputDelegate.isPhoneNumberOk();
            }
        });
        mGetVerCodeDelegate.ready();
    }

    public void checkPhoneNumber(View v){
        if(mPhoneNumberInputDelegate.isPhoneNumberOk()){
            ToastDelegate.show(this, mPhoneNumberInputDelegate.getPhoneNumber());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 停止验证码业务
        mGetVerCodeDelegate.stop();
    }
}