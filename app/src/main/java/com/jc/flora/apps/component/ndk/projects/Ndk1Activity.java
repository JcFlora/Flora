package com.jc.flora.apps.component.ndk.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.component.ndk.delegate.NativeDelegate1;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Shijincheng on 2018/4/13.
 */

public class Ndk1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("最基础的JNI调用");
        setContentView(R.layout.activity_ndk1);
    }

    public void callJni(View v) {
        String s = new NativeDelegate1().getStringFromJni();
        ToastDelegate.show(this, s);
    }

    public void callJava(View v) {
        String s = new NativeDelegate1().callJavaMethod();
        ToastDelegate.show(this, s);
    }

}
