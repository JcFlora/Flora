package com.jc.flora.apps.ui.captain.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.ui.captain.delegate.CaptainDelegate;

/**
 * Created by shijincheng on 2017/5/27.
 */
public class Captain19Activity extends AppCompatActivity {

    private LinearLayout mLayoutCaptainIndicators;
    private LinearLayout mLayoutCaptainBar;

    private CaptainDelegate mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("隔离出CaptainDelegate");
        setContentView(R.layout.activity_captain17);
        initViews();
        initDelegate();
    }

    private void initViews() {
        mLayoutCaptainIndicators = (LinearLayout) findViewById(R.id.layout_captain_indicators);
        mLayoutCaptainBar = (LinearLayout) findViewById(R.id.layout_captain_bar);
    }

    private void initDelegate(){
        mDelegate = new CaptainDelegate();
        mDelegate.setActivity(this);
        mDelegate.setLayoutCaptainIndicators(mLayoutCaptainIndicators);
        mDelegate.setLayoutCaptainBar(mLayoutCaptainBar);
        mDelegate.init();
    }

    @Override
    public void onBackPressed() {
        if(!mDelegate.back2Tab0()){
            super.onBackPressed();
        }
    }
}