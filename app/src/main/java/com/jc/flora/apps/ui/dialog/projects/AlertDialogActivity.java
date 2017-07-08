package com.jc.flora.apps.ui.dialog.projects;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.CustomDialogDelegate;
import com.jc.flora.apps.ui.dialog.delegate.CustomListDialogDelegate;
import com.jc.flora.apps.ui.dialog.delegate.MultiChoiceDialogDelegate;
import com.jc.flora.apps.ui.dialog.delegate.SingleChoiceDialogDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/2/28.
 */
public class AlertDialogActivity extends AppCompatActivity {

    private SingleChoiceDialogDelegate mSingleChoiceDialogDelegate;
    private MultiChoiceDialogDelegate mMultiChoiceDialogDelegate;
    private CustomListDialogDelegate mCustomListDialogDelegate;
    private CustomDialogDelegate mCustomDialogDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("普通对话框");
        setContentView(R.layout.activity_alert_dialog);
        mSingleChoiceDialogDelegate = new SingleChoiceDialogDelegate(this);
        mMultiChoiceDialogDelegate = new MultiChoiceDialogDelegate(this);
        mCustomListDialogDelegate = new CustomListDialogDelegate(this);
        mCustomDialogDelegate = new CustomDialogDelegate(this);
    }

    public void showToastDialog(View v){
        showToastDialog();
    }

    public void showAskDialog(View v){
        showAskDialog();
    }

    public void showListDialog(View v){
        showListDialog();
    }

    public void showSingleChoiceDialog(View v){
        mSingleChoiceDialogDelegate.show();
    }

    public void showMultiChoiceDialog(View v){
        mMultiChoiceDialogDelegate.show();
    }

    public void showTwoColorDialog(View v){
        showTwoColorDialog();
    }

    public void showCustomListDialog(View v){
        mCustomListDialogDelegate.show();
    }

    public void showCustomDialog(View v){
        mCustomDialogDelegate.show();
    }

    private void showToastDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("有最新版本可以下载了");
        builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    private void showAskDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定要退出嘛？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    private void showListDialog() {
        final String[] ITEMS = {"相册", "拍照", "取消"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择照片");
        builder.setItems(ITEMS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ToastDelegate.show(AlertDialogActivity.this,"你选择了第"+(which+1)+"个");
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    private void showTwoColorDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定要退出嘛？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.show();
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                .setTextColor(getResources().getColor(android.R.color.holo_blue_light));
    }

}
