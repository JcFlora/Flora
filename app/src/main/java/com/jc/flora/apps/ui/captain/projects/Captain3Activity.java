package com.jc.flora.apps.ui.captain.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.captain.delegate.IndicatorDelegate3;

/**
 * Created by shijincheng on 2017/5/2.
 */
public class Captain3Activity extends AppCompatActivity {

    private static final int COUNT = 5;
    private static final int[] TAB_RES = {R.id.btn_tab_home, R.id.btn_tab_category,
            R.id.btn_tab_discovery, R.id.btn_tab_cart, R.id.btn_tab_kaola};
    private TextView[] mBtnTabs = new TextView[COUNT];

    private IndicatorDelegate3 mIndicatorDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("封装导航条");
        setContentView(R.layout.activity_captain1);
        initViews();
        initIndicators();
    }

    private void initViews() {
        for (int i = 0; i < COUNT; i++) {
            mBtnTabs[i] = (TextView) findViewById(TAB_RES[i]);
        }
    }

    private void initIndicators() {
        mIndicatorDelegate = new IndicatorDelegate3();
        mIndicatorDelegate.setBtnTabs(mBtnTabs);
        mIndicatorDelegate.init();
    }

}
