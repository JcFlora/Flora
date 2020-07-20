package com.jc.flora.apps.ui.dialog.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.GifProgressDialogDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ProgressDialogDelegate;

/**
 * Created by shijincheng on 2017/2/28.
 */
public class ProgressDialogActivity extends AppCompatActivity {

    private ProgressDialogDelegate mProgressDialogDelegate;
    private GifProgressDialogDelegate mGifProgressDialogDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("进度对话框");
        setContentView(R.layout.activity_progress_dialog);
        mProgressDialogDelegate = new ProgressDialogDelegate(this);
        mGifProgressDialogDelegate = new GifProgressDialogDelegate(this);
    }

    public void showLoadingDialog(View v){
        mProgressDialogDelegate.showLoadingDialog();
    }

    public void showProgressChangeDialog(View v){
    }

    public void showGifLoadingDialog(View v){
        mGifProgressDialogDelegate.show();
    }

    public void stopGifLoadingDialog(View v){
        mGifProgressDialogDelegate.hide();
    }

}
