package com.jc.flora.apps.component.distribute.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.distribute.delegate.Distribute1Delegate;
import com.jc.flora.apps.component.distribute.delegate.Distribute2Delegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Shijincheng on 2018/9/2.
 */
public class Distribute2Activity extends AppCompatActivity {

    /** 分发出去的手机号填写业务管理 */
    private Distribute1Delegate mPhoneNumberInputDelegate;
    /** 分发出去的获取验证码业务管理 */
    private Distribute2Delegate mGetVerCodeDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("分发一个需要同步生命周期的业务功能");
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
        mGetVerCodeDelegate = new Distribute2Delegate(this);
        mGetVerCodeDelegate.setBtnVerCode(btnVerCode);
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