package com.jc.flora.apps.ui.dialog.delegate;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2017/3/9.
 */
public class CustomListDialogDelegate {

    private static final String[] ITEMS = {"相册", "拍照", "取消"};
    private static final int[] ICON_IDS = {R.mipmap.item_ic_gallery, R.mipmap.item_ic_camera, R.mipmap.item_ic_cancel};

    private AppCompatActivity mActivity;

    public CustomListDialogDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("选择照片");
        builder.setAdapter(mAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastDelegate.show(mActivity,"你选择了第"+(which+1)+"个");
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return ITEMS.length;
        }

        @Override
        public Object getItem(int position) {
            return ITEMS[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AppCompatTextView tv = new AppCompatTextView(mActivity);
            tv.setText(ITEMS[position]);
            tv.setPadding(60, 30, 60, 30);
            tv.setCompoundDrawablesWithIntrinsicBounds(ICON_IDS[position], 0, 0, 0);
            tv.setCompoundDrawablePadding(60);
            tv.setTextSize(18);
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            return tv;
        }
    };

}
