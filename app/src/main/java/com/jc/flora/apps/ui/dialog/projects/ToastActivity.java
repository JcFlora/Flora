package com.jc.flora.apps.ui.dialog.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/3/1.
 */
public class ToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Toast");
        setContentView(R.layout.activity_toast);
    }

    public void showToast(View v){
        ToastDelegate.show(this, "我是一个Toast");
    }

}
