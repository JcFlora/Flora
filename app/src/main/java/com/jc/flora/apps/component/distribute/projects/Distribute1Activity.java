package com.jc.flora.apps.component.distribute.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.jc.flora.R;
import com.jc.flora.apps.component.distribute.delegate.Distribute1Delegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Shijincheng on 2018/9/1.
 */
public class Distribute1Activity extends AppCompatActivity {

    /** 分发出去的手机号填写业务管理 */
    private Distribute1Delegate mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("分发一个简单的业务功能");
        setContentView(R.layout.activity_distribute1);
        EditText etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        mDelegate = new Distribute1Delegate(this);
        mDelegate.setEtPhoneNumber(etPhoneNumber);
    }

    public void checkPhoneNumber(View v){
        if(mDelegate.isPhoneNumberOk()){
            ToastDelegate.show(this, mDelegate.getPhoneNumber());
        }
    }

}