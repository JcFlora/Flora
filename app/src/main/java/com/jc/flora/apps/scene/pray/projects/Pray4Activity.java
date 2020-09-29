package com.jc.flora.apps.scene.pray.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.pray.delegate.PrayBottomSheetDialogDelegate;
import com.jc.flora.apps.scene.pray.delegate.PrayDialogDelegate;
import com.jc.flora.apps.scene.pray.elete.Elete;
import com.jc.flora.apps.scene.pray.elete.PrayCmdExecutor;
import com.jc.flora.apps.scene.pray.elete.PrayInfoDataSource;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/9/18.
 */
public class Pray4Activity extends AppCompatActivity {

    private static boolean sIsPrayOn = false;

    private PrayDialogDelegate mPrayDialogDelegate;
    private PrayBottomSheetDialogDelegate mPrayBottomSheetDialogDelegate;
    private PrayCmdExecutor mPrayCmdExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("全局置灰+封装为PrayDelegate");
        setContentView(R.layout.activity_pray4);
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
                ToastDelegate.show(Pray4Activity.this, "你选择了" + content + "角色");
            }
        });
    }

    private void initPrayCmdExecutor(){
        // 初始化Elete框架，实际项目中，在Application的onCreate方法中调用
        Elete.init(new PrayInfoDataSource() {
            @Override
            public boolean readPrayStatus() {
                return sIsPrayOn;
            }

            @Override
            public void writePrayStatus(boolean isPrayOn) {
                sIsPrayOn = isPrayOn;
            }
        });
        mPrayCmdExecutor = Elete.createExecutor(this, "pray");
        mPrayCmdExecutor.registerCurrentActivity();
        mPrayCmdExecutor.registerDialogContentView(mPrayDialogDelegate.getContentView());
        mPrayCmdExecutor.registerDialogContentView(mPrayBottomSheetDialogDelegate.getContentView());
        mPrayCmdExecutor.syncPrayStatus();
    }

    public void nextPage(View v){
        startActivity(new Intent(this, Pray4TestActivity.class));
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