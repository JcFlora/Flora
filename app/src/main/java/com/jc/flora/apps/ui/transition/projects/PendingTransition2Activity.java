package com.jc.flora.apps.ui.transition.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2017/3/2.
 */
public class PendingTransition2Activity extends AppCompatActivity {

    private String mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlag = getIntent().getStringExtra("flag");
        if("slide".equals(mFlag)){
            overridePendingTransition(R.anim.page_enter_slide, R.anim.page_exit_slide);
        }else if("fade".equals(mFlag)){
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        setTitle("测试页面");
        TextView tv = new TextView(this);
        setContentView(tv);
        tv.setText("转场动画过来的测试主页面");
    }

    @Override
    public void finish() {
        super.finish();
        if("slide".equals(mFlag)){
            overridePendingTransition(R.anim.page_reenter_slide, R.anim.page_return_slide);
        }else if("fade".equals(mFlag)){
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

}