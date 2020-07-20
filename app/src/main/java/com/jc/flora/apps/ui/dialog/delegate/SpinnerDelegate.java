package com.jc.flora.apps.ui.dialog.delegate;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Samurai on 2018/5/15.
 */
public class SpinnerDelegate {

    private static final String[] ITEMS = {"泰国", "新加坡", "印度尼西亚"};

    private AppCompatActivity mActivity;
    private TextView mBtn;
    private int mSelectedIndex = -1;

    private OnSpinnerItemSelectedListener mItemSelectedListener;

    public SpinnerDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void init(TextView button){
        mBtn = button;
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });
    }

    public void setOnItemSelectedListener(OnSpinnerItemSelectedListener l) {
        mItemSelectedListener = l;
    }

    public void setSelectedIndex(int selectedIndex) {
        setSelectedIndex(selectedIndex, false);
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    private void setSelectedIndex(int selectedIndex, boolean byUser) {
        if(selectedIndex < 0){
            return;
        }
        mSelectedIndex = selectedIndex;
        mBtn.setText(ITEMS[selectedIndex]);
        if(mItemSelectedListener != null){
            mItemSelectedListener.onItemSelected(selectedIndex, byUser);
        }
    }

    private void showListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("选择国家");
        builder.setItems(ITEMS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                setSelectedIndex(which, true);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    public interface OnSpinnerItemSelectedListener{
        void onItemSelected(int which, boolean byUser);
    }

}
