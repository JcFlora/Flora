package com.jc.flora.apps.ui.captain.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.ui.captain.delegate.IndicatorDelegate4;

/**
 * Created by shijincheng on 2017/5/2.
 */
public class Captain4Activity extends AppCompatActivity {

    private IndicatorDelegate4 mIndicatorDelegate;
    private LinearLayout mLayoutCaptainIndicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("动态添加指示器item");
        setContentView(R.layout.activity_captain4);
        initViews();
        initIndicators();
    }

    private void initViews() {
        mLayoutCaptainIndicators = (LinearLayout) findViewById(R.id.layout_captain_indicators);
    }

    private void initIndicators() {
        mIndicatorDelegate = new IndicatorDelegate4();
        mIndicatorDelegate.setLayoutCaptainIndicators(mLayoutCaptainIndicators);
        mIndicatorDelegate.init();
    }

}
