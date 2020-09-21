package com.jc.flora.apps.scene.pray.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.pray.delegate.PrayBottomSheetDialogDelegate;
import com.jc.flora.apps.scene.pray.delegate.PrayDelegate;
import com.jc.flora.apps.scene.pray.delegate.PrayDialogDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/9/17.
 */
public class Pray3Activity extends AppCompatActivity {

    private PrayDialogDelegate mPrayDialogDelegate;
    private PrayBottomSheetDialogDelegate mPrayBottomSheetDialogDelegate;
    private PrayDelegate mPrayDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("封装为PrayDelegate");
        setContentView(R.layout.activity_pray3);
        mPrayDialogDelegate = new PrayDialogDelegate(this);
        showBottomSheetDialog();
        initPrayDelegate();
    }

    private void showBottomSheetDialog(){
        TextView btnRole = (TextView) findViewById(R.id.btn_role);
        mPrayBottomSheetDialogDelegate = new PrayBottomSheetDialogDelegate(this);
        mPrayBottomSheetDialogDelegate.init(btnRole);
        mPrayBottomSheetDialogDelegate.setOnRoleCheckedListener(new PrayBottomSheetDialogDelegate.OnRoleCheckedListener() {
            @Override
            public void onRoleChecked(String role) {
                String content = "1".equals(role) ? "学生" : "老师";
                ToastDelegate.show(Pray3Activity.this, "你选择了" + content + "角色");
            }
        });
    }

    private void initPrayDelegate(){
        mPrayDelegate = new PrayDelegate(this);
        mPrayDelegate.registerDialogContentView(mPrayDialogDelegate.getContentView());
        mPrayDelegate.registerDialogContentView(mPrayBottomSheetDialogDelegate.getContentView());
    }

    public void nextPage(View v){
        startActivity(new Intent(this, Pray4TestActivity.class));
    }

    public void openGrayFilter(View v){
        mPrayDelegate.pray();
    }

    public void closeGrayFilter(View v){
        mPrayDelegate.clear();
    }

    public void showCartoonDialog(View v){
        mPrayDialogDelegate.show();
    }

}