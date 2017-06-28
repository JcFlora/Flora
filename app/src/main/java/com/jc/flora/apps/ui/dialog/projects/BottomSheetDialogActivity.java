package com.jc.flora.apps.ui.dialog.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2017/3/1.
 */
public class BottomSheetDialogActivity extends AppCompatActivity {

    private TestBottomSheetDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("BottomSheetDialog");
        setContentView(R.layout.activity_bottom_sheet_dialog);
        mDialog = new TestBottomSheetDialog(this);
    }

    public void showBottomSheetDialog(View v){
        mDialog.show();
    }

}