package com.jc.flora.apps.component.distribute.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.distribute.delegate.Distribute1Delegate;
import com.jc.flora.apps.component.distribute.delegate.Distribute5Delegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Shijincheng on 2018/12/16.
 */
public class Distribute6Activity extends AppCompatActivity {

    private static final String FRAGMENTS_TAG = "android:fragments";
    private static final String SUPPORT_FRAGMENTS_TAG = "android:support:fragments";

    /** 分发出去的手机号填写业务管理 */
    private Distribute1Delegate mPhoneNumberInputDelegate;
    /** 分发出去的获取验证码业务管理 */
    private Distribute5Delegate mGetVerCodeDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 由于页面中使用了Fragment作为业务分发，为了防止后台内存释放后，fragment从缓存中恢复时成员变量为空
        // 特地去掉fragment的自动缓存，这样后台内存释放后，fragment进行重新加载，不会有成员变量为空的问题
        if(savedInstanceState != null){
            savedInstanceState.putParcelable(FRAGMENTS_TAG, null);
            savedInstanceState.putParcelable(SUPPORT_FRAGMENTS_TAG, null);
        }
        super.onCreate(savedInstanceState);
        setTitle("处理Fragment代理默认缓存恢复引起的问题");
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
        mGetVerCodeDelegate = new Distribute5Delegate();
        mGetVerCodeDelegate.setBtnVerCode(btnVerCode);
        mGetVerCodeDelegate.setPhoneNumberInputDelegate(mPhoneNumberInputDelegate);
        mGetVerCodeDelegate.addToActivity(this,"getVerCode");
    }

    public void checkPhoneNumber(View v){
        if(mPhoneNumberInputDelegate.isPhoneNumberOk()){
            ToastDelegate.show(this, mPhoneNumberInputDelegate.getPhoneNumber());
        }
    }

}