package com.jc.flora.apps.component.ndk.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.component.ndk.delegate.NativeDelegate2;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Shijincheng on 2018/4/13.
 */

public class Ndk2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("以源码的形式编译Lame动态库");
        setContentView(R.layout.activity_ndk2);
    }

    public void showLameVersion(View v) {
        String s = new NativeDelegate2().getLameVersion();
        ToastDelegate.show(this, s);
    }

}
