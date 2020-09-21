package com.jc.flora.apps.scene.pray.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.jc.flora.R;
import com.jc.flora.apps.scene.login.api.LoginResponse;
import com.jc.flora.apps.scene.pray.api.PrayMockApi;
import com.jc.flora.apps.scene.pray.api.PrayResponse;
import com.jc.flora.apps.scene.pray.delegate.PrayBottomSheetDialogDelegate;
import com.jc.flora.apps.scene.pray.delegate.PrayDialogDelegate;
import com.jc.flora.apps.scene.pray.elete.Elete;
import com.jc.flora.apps.scene.pray.elete.PrayCmdExecutor;
import com.jc.flora.apps.scene.pray.elete.PrayInfoDataSource;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/9/21.
 */
public class Pray5Activity extends AppCompatActivity {

    private static boolean sIsPrayOn = false;

    private PrayDialogDelegate mPrayDialogDelegate;
    private PrayBottomSheetDialogDelegate mPrayBottomSheetDialogDelegate;
    private PrayCmdExecutor mPrayCmdExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("模拟接口控制");
        setContentView(R.layout.activity_pray5);
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
                ToastDelegate.show(Pray5Activity.this, "你选择了" + content + "角色");
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
        mPrayCmdExecutor.registerDialogContentView(mPrayDialogDelegate.getContentView());
        mPrayCmdExecutor.registerDialogContentView(mPrayBottomSheetDialogDelegate.getContentView());
        mPrayCmdExecutor.syncPrayStatus();
    }

    public void nextPage(View v){
        startActivity(new Intent(this, Pray5TestActivity.class));
    }

    public void requestOpenGrayFilter(View v){
        new PrayMockApi(this, new Response.Listener<PrayResponse>() {
            @Override
            public void onResponse(PrayResponse response) {
                if(response.success){
                    Elete.pray(Pray5Activity.this);
                }else{
                    Elete.clear(Pray5Activity.this);
                }
            }
        }).sendOpenGrayFilterRequest();
    }

    public void requestCloseGrayFilter(View v){
        new PrayMockApi(this, new Response.Listener<PrayResponse>() {
            @Override
            public void onResponse(PrayResponse response) {
                if(response.success){
                    Elete.pray(Pray5Activity.this);
                }else{
                    Elete.clear(Pray5Activity.this);
                }
            }
        }).sendCloseGrayFilterRequest();
    }

    public void showCartoonDialog(View v){
        mPrayDialogDelegate.show();
    }

}