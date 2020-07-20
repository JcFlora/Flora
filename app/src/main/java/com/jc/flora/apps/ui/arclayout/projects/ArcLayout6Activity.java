package com.jc.flora.apps.ui.arclayout.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.ogaclejapan.arclayout.ArcLayout;

import java.util.Random;

/**
 * Created by shijincheng on 2017/2/8.
 */
public class ArcLayout6Activity extends AppCompatActivity {

    private ArcTabDelegate mArcTabDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("弧形Tab+指示器");
        setContentView(R.layout.activity_arclayout6);
        init();
    }

    private void init(){
        ArcLayout arcLayout = (ArcLayout) findViewById(R.id.arc_layout);
        View arrow = findViewById(R.id.v_arrow);
        mArcTabDelegate = new ArcTabDelegate(arcLayout,arrow);
        mArcTabDelegate.setOnArcTabSelectedListener(new ArcTabDelegate.OnArcTabSelectedListener() {
            @Override
            public void onLocked(int position) {
                ToastDelegate.show(ArcLayout6Activity.this, "你锁定了第" + (position + 1) + "个标签");
            }
            @Override
            public void onFling(int position) {
                ToastDelegate.show(ArcLayout6Activity.this, "当前指向第" + (position + 1) + "个标签");
            }
        });
        mArcTabDelegate.init();
    }

    public void onPreClick(View v) {
        mArcTabDelegate.flingPre();
    }

    public void onNextClick(View v) {
        mArcTabDelegate.flingNext();
    }

    public void onRandomClick(View v){
        mArcTabDelegate.flingAt(new Random().nextInt(4));
    }

    public void onLockClick(View v) {
        mArcTabDelegate.onLock();
    }

    public void onUnlockClick(View v){
        mArcTabDelegate.onUnlock();
    }

    @Override
    protected void onDestroy() {
        mArcTabDelegate.onDestroy();
        super.onDestroy();
    }
}
