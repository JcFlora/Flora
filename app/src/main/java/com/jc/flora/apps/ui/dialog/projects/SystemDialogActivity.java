package com.jc.flora.apps.ui.dialog.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.SystemDialogDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/3/8.
 */
public class SystemDialogActivity extends AppCompatActivity {

    private SystemDialogDelegate mSystemDialogDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("系统对话框");
        setContentView(R.layout.activity_system_dialog);
        initDelegate();
    }

    private void initDelegate() {
        mSystemDialogDelegate = new SystemDialogDelegate();
        mSystemDialogDelegate.setTargetService(TestService.class);
        mSystemDialogDelegate.setSystemDialogReceiver(mMyReceiver);
        mSystemDialogDelegate.addToActivity(this, "systemDialogDelegate");
    }

    public void startService(View v) {
        mSystemDialogDelegate.startTargetService();
    }

    private SystemDialogDelegate.SystemDialogReceiver mMyReceiver = new SystemDialogDelegate.SystemDialogReceiver() {
        @Override
        protected void onDialogPositiveButtonClick(Bundle bundle) {
            ToastDelegate.show(SystemDialogActivity.this, "你点击了系统对话框按钮");
        }

        @Override
        protected void onDialogCancelClick(Bundle bundle) {
            ToastDelegate.show(SystemDialogActivity.this, "你关闭了系统对话框");
        }
    };

}