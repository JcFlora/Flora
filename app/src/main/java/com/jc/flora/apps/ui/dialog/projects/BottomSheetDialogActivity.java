package com.jc.flora.apps.ui.dialog.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.BottomSheetDialogDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

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
        showBottomSheetDialog2();
    }

    private void showBottomSheetDialog2(){
        TextView btnRole = (TextView) findViewById(R.id.btn_role);
        BottomSheetDialogDelegate delegate = new BottomSheetDialogDelegate(this);
        delegate.init(btnRole);
        delegate.setOnRoleCheckedListener(new BottomSheetDialogDelegate.OnRoleCheckedListener() {
            @Override
            public void onRoleChecked(String role) {
                String content = "1".equals(role) ? "学生" : "老师";
                ToastDelegate.show(BottomSheetDialogActivity.this, "你选择了" + content + "角色");
            }
        });
    }

    public void showBottomSheetDialog(View v){
        mDialog.show();
    }

}