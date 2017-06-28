package com.jc.flora.apps.ui.transition.projects;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2017/3/2.
 */
public class PendingTransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Activity的转场动画");
        setContentView(R.layout.activity_pending_transition);
    }

    public void slideNextActivity(View v) {
        gotoNextActivity("slide");
    }

    public void fadeNextActivity(View v) {
        gotoNextActivity("fade");
    }

    private void gotoNextActivity(final String flag){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PendingTransitionActivity.this,PendingTransition2Activity.class);
                intent.putExtra("flag",flag);
                startActivity(intent);
            }
        },200);
    }

}