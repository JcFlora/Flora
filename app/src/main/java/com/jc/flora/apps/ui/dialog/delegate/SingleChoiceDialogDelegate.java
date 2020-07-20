package com.jc.flora.apps.ui.dialog.delegate;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2017/3/8.
 */
public class SingleChoiceDialogDelegate {

    private final String[] ITEMS = {"产品汪", "设计狮", "程序猿", "测试X", "运营喵"};
    // 选定的item
    private int checkedIndex = 0;
    // 正在选择的item（点击确定有效）
    private int checkingIndex = 0;

    private AppCompatActivity mActivity;

    public SingleChoiceDialogDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void show() {
        // 同步正在选择的item
        checkingIndex = checkedIndex;
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("请选择你的身份");
        builder.setSingleChoiceItems(ITEMS, checkingIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkingIndex = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                checkedIndex = checkingIndex;
                ToastDelegate.show(mActivity, ITEMS[checkedIndex]);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    public String getCheckedItem(){
        return ITEMS[checkedIndex];
    }

}
