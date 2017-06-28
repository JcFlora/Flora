package com.jc.flora.apps.ui.captain.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.ui.captain.delegate.FragmentDelegate6;
import com.jc.flora.apps.ui.captain.delegate.IndicatorDelegate5;

/**
 * Created by shijincheng on 2017/5/2.
 */
public class Captain6Activity extends AppCompatActivity {

    private static final String[] TITLES = {"首页", "分类", "发现", "购物车", "我的考拉"};
    private static final int COUNT = 5;

    private FragmentDelegate6 mFragmentDelegate;
    private IndicatorDelegate5 mIndicatorDelegate;
    private LinearLayout mLayoutCaptainIndicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("封装Fragment切换");
        setContentView(R.layout.activity_captain4);
        initViews();
        initFragments();
        initIndicators();
    }

    private void initViews() {
        mLayoutCaptainIndicators = (LinearLayout) findViewById(R.id.layout_captain_indicators);
    }

    private void initFragments(){
        CaptainTestFragment[] fragmentList = new CaptainTestFragment[COUNT];
        for (int i = 0; i < COUNT; i++) {
            fragmentList[i] = new CaptainTestFragment();
            fragmentList[i].setTitle(TITLES[i]);
        }
        mFragmentDelegate = new FragmentDelegate6();
        mFragmentDelegate.setContainerViewId(R.id.layout_content);
        mFragmentDelegate.setFragmentList(fragmentList);
        mFragmentDelegate.init(this);
    }

    private void initIndicators() {
        mIndicatorDelegate = new IndicatorDelegate5();
        mIndicatorDelegate.setLayoutCaptainIndicators(mLayoutCaptainIndicators);
        mIndicatorDelegate.setOnTabIndicatorCheckedChangeListener(mFragmentDelegate);
        mIndicatorDelegate.init();
    }

}
