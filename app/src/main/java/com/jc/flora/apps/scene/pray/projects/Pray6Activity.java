package com.jc.flora.apps.scene.pray.projects;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.scene.pray.delegate.PrayTestCaptainDelegate;
import com.jc.flora.apps.scene.pray.elete.Elete;
import com.jc.flora.apps.scene.pray.elete.PrayCmdExecutor;
import com.jc.flora.apps.scene.pray.elete.PrayInfoDataSource;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/9/25.
 */
public class Pray6Activity extends AppCompatActivity {

    private static boolean sIsPrayOn = true;

    private LinearLayout mLayoutCaptainIndicators;
    private LinearLayout mLayoutCaptainBar;

    private PrayTestCaptainDelegate mDelegate;
    private PrayCmdExecutor mPrayCmdExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("仅首页Fragment和底部Tab置灰");
        setContentView(R.layout.activity_pray6);
        initViews();
        initDelegate();
        initPrayCmdExecutor();
    }

    private void initViews() {
        mLayoutCaptainIndicators = (LinearLayout) findViewById(R.id.layout_captain_indicators);
        mLayoutCaptainBar = (LinearLayout) findViewById(R.id.layout_captain_bar);
    }

    private void initDelegate(){
        mDelegate = new PrayTestCaptainDelegate();
        mDelegate.setActivity(this);
        mDelegate.setLayoutCaptainIndicators(mLayoutCaptainIndicators);
        mDelegate.setLayoutCaptainBar(mLayoutCaptainBar);
        mDelegate.init();
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
        mPrayCmdExecutor.registerView(mLayoutCaptainBar);
        mPrayCmdExecutor.syncPrayStatus();
    }

    @Override
    public void onBackPressed() {
        if(!mDelegate.back2Tab0()){
            super.onBackPressed();
        }
    }
}