package com.jc.flora.apps.ui.dialog.delegate;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2017/3/18.
 */
public class ProgressDialogDelegate {

    private AppCompatActivity mActivity;
    private ProgressDialog mLoadingDialog;

    public ProgressDialogDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void showLoadingDialog() {
        showLoadingDialog("数据正在加载中，请稍候");
    }

    public void showLoadingDialog(CharSequence msg) {
        if (mLoadingDialog == null){
            mLoadingDialog = ProgressDialog.show(mActivity, "", msg, true, true);
        }else if (!mLoadingDialog.isShowing()){
            mLoadingDialog.setMessage(msg);
            mLoadingDialog.show();
        }
    }

    public void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
    }

}
