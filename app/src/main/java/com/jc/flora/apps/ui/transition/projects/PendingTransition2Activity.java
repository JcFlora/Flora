package com.jc.flora.apps.ui.transition.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jc.flora.apps.ui.transition.delegate.PendingTransitionDelegate;

/**
 * Created by shijincheng on 2017/3/2.
 */
public class PendingTransition2Activity extends AppCompatActivity {

    private String mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlag = getIntent().getStringExtra("flag");
        // 设置页面的进入动画
        PendingTransitionDelegate.initPendingTransition(this, mFlag);
        setTitle("测试页面");
        TextView tv = new TextView(this);
        setContentView(tv);
        tv.setText("转场动画过来的测试主页面");
    }

    @Override
    public void finish() {
        super.finish();
        // 设置页面的退出动画
        PendingTransitionDelegate.finishPendingTransition(this, mFlag);
    }

}