package com.jc.flora.apps.ui.dialog.delegate;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.jc.flora.R;
import com.jc.flora.apps.component.vi.fidelity.Fidelity;
import com.jc.flora.apps.ui.shape.delegate.ShapeDelegate;

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
        onCreateViewHolder();
        onBindViewHolder();
    }

    public void show(){
        mHolder.etCode.setText("");
        mDialog.show();
    }

    private void onDialogCreated(){
        // 去掉默认的标题栏
        mDialog.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置自定义布局
        mDialog.setContentView(R.layout.dialog_custom);
        // 设置可关闭
        mDialog.setCancelable(true);
        // 设置背景为白底圆角
        mDialog.getWindow().setBackgroundDrawable(
                ShapeDelegate.getSolidCornerDrawable(20, 0xffffffff));
        // 重设对话框宽度
        setDialogWidth((int) Fidelity.getInstance(mActivity).getViDimen(640));
    }

    private void onCreateViewHolder(){
        mHolder = new ViewHolder();
        mHolder.etCode = (EditText) mDialog.findViewById(R.id.et_dialog_code);
        mHolder.btnCancel = mDialog.findViewById(R.id.btn_dialog_cancel);
        mHolder.btnOk = mDialog.findViewById(R.id.btn_dialog_ok);
    }

    private void onBindViewHolder(){
        // 设置编辑框的背景为圆角线框
        mHolder.etCode.setBackground(
                ShapeDelegate.getStrokeCornerDrawable(50, 1, 0xff999999));
        // 设置取消按钮事件
        mHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        // 设置确定按钮事件
        mHolder.btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(android.view.View v) {
                mDialog.dismiss();
                String code = mHolder.etCode.getText().toString().trim();
                if(!TextUtils.isEmpty(code)){
                    ToastDelegate.show(mActivity, code);
                }
            }
        });
    }

    private void setDialogWidth(int width){
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.width = width;
        lp.gravity = Gravity.CENTER_HORIZONTAL;
    }

    private static class ViewHolder{
        private EditText etCode;
        private View btnCancel;
        private View btnOk;
    }

}
