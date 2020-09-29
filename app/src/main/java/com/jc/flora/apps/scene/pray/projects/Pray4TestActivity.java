package com.jc.flora.apps.scene.pray.projects;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.pray.delegate.PrayBottomSheetDialogDelegate;
import com.jc.flora.apps.scene.pray.delegate.PrayDialogDelegate;
import com.jc.flora.apps.scene.pray.elete.Elete;
import com.jc.flora.apps.scene.pray.elete.PrayCmdExecutor;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/9/18.
 */
public class Pray4TestActivity extends AppCompatActivity {

    private PrayDialogDelegate mPrayDialogDelegate;
    private PrayBottomSheetDialogDelegate mPrayBottomSheetDialogDelegate;
    private PrayCmdExecutor mPrayCmdExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("测试页面");
        setContentView(R.layout.activity_pray4_test);
        mPrayDialogDelegate = new PrayDialogDelegate(this);
        showBottomSheetDialog();
        initPrayCmdExecutor();
    }

    private void showBottomSheetDialog(){
        TextView btnRole = (TextView) findViewById(R.id.btn_role);
        mPrayBottomSheetDialogDelegate = new PrayBottomSheetDialogDelegate(this);
        mPrayBottomSheetDialogDelegate.init(btnRole);
        mPrayBottomSheetDialogDelegate.setOnRoleCheckedListener(new PrayBottomSheetDialogDelegate.OnRoleCheckedListener() {
            @Override
            public void onRoleChecked(String role) {
                String content = "1".equals(role) ? "学生" : "老师";
                ToastDelegate.show(Pray4TestActivity.this, "你选择了" + content + "角色");
            }
        });
    }

    private void initPrayCmdExecutor(){
        mPrayCmdExecutor = Elete.createExecutor(this, "pray");
        mPrayCmdExecutor.registerCurrentActivity();
        mPrayCmdExecutor.registerDialogContentView(mPrayDialogDelegate.getContentView());
        mPrayCmdExecutor.registerDialogContentView(mPrayBottomSheetDialogDelegate.getContentView());
        mPrayCmdExecutor.syncPrayStatus();
    }

    public void openGrayFilter(View v){
        Elete.pray(this);
    }

    public void closeGrayFilter(View v){
        Elete.clear(this);
    }

    public void showCartoonDialog(View v){
        mPrayDialogDelegate.show();
    }

}