package com.jc.flora.apps.ui.transition.projects;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.transition.delegate.PendingTransitionDelegate;

/**
 * Created by shijincheng on 2017/3/2.
 */
public class PendingTransitionActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Activity的转场动画");
        setContentView(R.layout.activity_pending_transition);
    }

    public void slideNextActivity(View v) {
        gotoNextActivity(PendingTransitionDelegate.PUSH);
    }

    public void fadeNextActivity(View v) {
        gotoNextActivity(PendingTransitionDelegate.FADE);
    }

    private void gotoNextActivity(final String flag){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PendingTransitionActivity.this,PendingTransition2Activity.class);
                intent.putExtra("flag",flag);
                startActivity(intent);
            }
        },200);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
    }
}