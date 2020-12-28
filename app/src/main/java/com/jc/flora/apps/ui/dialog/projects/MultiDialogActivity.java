package com.jc.flora.apps.ui.dialog.projects;

import android.os.Bundle;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.CustomDialogDelegate3;
import com.jc.flora.apps.ui.dialog.delegate.CustomDialogDelegate4;
import com.jc.flora.apps.ui.dialog.multi_dialog.MultiDialogDelegate;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/9/29.
 */
public class MultiDialogActivity extends AppCompatActivity {

    private CustomDialogDelegate3 mCustomDialogDelegate3;
    private CustomDialogDelegate4 mCustomDialogDelegate4;
    private MultiDialogDelegate mMultiDialogDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("多弹窗管理");
        setContentView(R.layout.activity_multi_dialog);
        initMultiDialogDelegate();
    }

    private void initMultiDialogDelegate(){
        mCustomDialogDelegate3 = new CustomDialogDelegate3(this);
        mCustomDialogDelegate4 = new CustomDialogDelegate4(this);
        mMultiDialogDelegate = new MultiDialogDelegate();
    }

    public void showMultiDialog(View v) {
        mMultiDialogDelegate.clear();
        mMultiDialogDelegate.push(mCustomDialogDelegate3);
        mMultiDialogDelegate.push(mCustomDialogDelegate4);
        mMultiDialogDelegate.startToShow();
    }

    public void showMultiDialogOnlyOnce(View v) {

    }

}