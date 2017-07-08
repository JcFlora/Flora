package com.jc.flora.apps.ui.dialog.delegate;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.jc.flora.R;

/**
 * Created by Samurai on 2017/7/8.
 */
public class CustomDialogDelegate {

    private AppCompatActivity mActivity;
    private AppCompatDialog mDialog;
    private ViewHolder mHolder;

    public CustomDialogDelegate(AppCompatActivity activity) {
        mActivity = activity;
        mDialog = new AppCompatDialog(activity);
        onDialogCreated();
    }

    public void show(){
        mHolder.etCode.setText("");
        mDialog.show();
    }

    private void onDialogCreated(){
        View layout = LayoutInflater.from(mActivity).inflate(R.layout.dialog_custom, null);
        // 去掉默认的标题栏
        mDialog.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置自定义布局
        mDialog.setContentView(layout);
        // 设置可关闭
        mDialog.setCancelable(true);
        onCreateViewHolder(layout);
        onBindViewHolder();
    }

    private void onCreateViewHolder(View layout){
        mHolder = new ViewHolder();
        mHolder.etCode = (EditText) layout.findViewById(R.id.et_dialog_code);
        mHolder.btnCancel = layout.findViewById(R.id.btn_dialog_cancel);
        mHolder.btnOk = layout.findViewById(R.id.btn_dialog_ok);
    }

    private void onBindViewHolder(){
        mHolder.etCode.requestFocus();
        mHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mHolder.btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(android.view.View v) {
                String code = mHolder.etCode.getText().toString().trim();
                if(!TextUtils.isEmpty(code)){
                    ToastDelegate.show(mActivity, code);
                }
                mDialog.dismiss();
            }
        });
    }

    private static class ViewHolder{
        private EditText etCode;
        private View btnCancel;
        private View btnOk;
    }

}
