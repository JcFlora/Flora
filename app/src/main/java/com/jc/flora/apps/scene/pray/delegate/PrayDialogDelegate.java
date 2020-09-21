package com.jc.flora.apps.scene.pray.delegate;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jc.flora.R;
import com.jc.flora.apps.component.vi.fidelity.Fidelity;
import com.jc.flora.apps.ui.shape.delegate.ShapeDelegate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

/**
 * Created by Samurai on 2020/9/17.
 */
public class PrayDialogDelegate {

    private AppCompatActivity mActivity;
    private AppCompatDialog mDialog;
    private ViewHolder mHolder;

    public PrayDialogDelegate(AppCompatActivity activity) {
        mActivity = activity;
        mDialog = new AppCompatDialog(activity);
        onDialogCreated();
        onCreateViewHolder();
        onBindViewHolder();
    }

    public void show(){
        mDialog.show();
    }

    public View getContentView(){
        return mDialog.getWindow().getDecorView();
    }

    private void onDialogCreated(){
        // 去掉默认的标题栏
        mDialog.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置自定义布局
        mDialog.setContentView(R.layout.dialog_custom2);
        // 设置可关闭
        mDialog.setCancelable(true);
        // 设置背景为透明
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
//        // 重设对话框宽度
//        setDialogWidth((int) Fidelity.getInstance(mActivity).hifi2px(640));
    }

    private void onCreateViewHolder(){
        mHolder = new ViewHolder();
        mHolder.btnCancel = mDialog.findViewById(R.id.btn_dialog_cancel);
        mHolder.btnOk = mDialog.findViewById(R.id.btn_dialog_ok);
    }

    private void onBindViewHolder(){
        // 设置确定按钮的背景为圆角白色
        int radius = (int)Fidelity.getInstance(mActivity).dp2px(22);
        mHolder.btnOk.setBackground(ShapeDelegate.getSolidCornerDrawable(radius, 0xffffffff));
        // 设置取消按钮事件
        mHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        // 设置确定按钮事件
        mHolder.btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    private void setDialogWidth(int width){
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.width = width;
        lp.gravity = Gravity.CENTER_HORIZONTAL;
    }

    private static class ViewHolder{
        private View btnCancel;
        private View btnOk;
    }

}
