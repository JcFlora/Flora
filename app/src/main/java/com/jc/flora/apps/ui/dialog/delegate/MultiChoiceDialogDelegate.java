package com.jc.flora.apps.ui.dialog.delegate;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/3/1.
 */
public class MultiChoiceDialogDelegate {

    private final String[] ITEMS = {"小说", "文学", "社科", "计算机"};
    // 选定的item
    private boolean[] checkedItems = {false, false, false, false};
    // 正在选择的item（点击确定有效）
    private boolean[] checkingItems = {false, false, false, false};

    private AppCompatActivity mActivity;

    public MultiChoiceDialogDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void show() {
        // 同步正在选择的item
        checkingItems = checkedItems.clone();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("选择分类");
        builder.setMultiChoiceItems(ITEMS, checkingItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                checkedItems = checkingItems.clone();
                ToastDelegate.show(mActivity, getCheckedItemsStr());
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    public ArrayList<String> getCheckedItems(){
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0, length = checkedItems.length; i < length; i++) {
            if(checkedItems[i]){
                items.add(ITEMS[i]);
            }
        }
        return items;
    }

    public String getCheckedItemsStr() {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> items = getCheckedItems();
        for (String item : items) {
            sb.append(item).append(";");
        }
        return sb.toString();
    }
}
