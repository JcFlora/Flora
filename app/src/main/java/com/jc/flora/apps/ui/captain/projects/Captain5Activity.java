package com.jc.flora.apps.ui.captain.projects;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.ui.captain.delegate.IndicatorDelegate5;

/**
 * Created by shijincheng on 2017/5/2.
 */
public class Captain5Activity extends AppCompatActivity {

    private static final String[] TITLES = {"首页", "分类", "发现", "购物车", "我的考拉"};
    private static final int COUNT = 5;

    private IndicatorDelegate5 mIndicatorDelegate;
    private LinearLayout mLayoutCaptainIndicators;

    private CaptainTestFragment[] mFragmentList = new CaptainTestFragment[COUNT];
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("关联页面内容切换");
        setContentView(R.layout.activity_captain4);
        initViews();
        initFragments();
        initIndicators();
    }

    private void initViews() {
        mLayoutCaptainIndicators = (LinearLayout) findViewById(R.id.layout_captain_indicators);
    }

    private void initFragments(){
        mFragmentManager = getSupportFragmentManager();
        for (int i = 0; i < COUNT; i++) {
            mFragmentList[i] = new CaptainTestFragment();
            mFragmentList[i].setTitle(TITLES[i]);
        }
    }

    private void initIndicators() {
        mIndicatorDelegate = new IndicatorDelegate5();
        mIndicatorDelegate.setLayoutCaptainIndicators(mLayoutCaptainIndicators);
        mIndicatorDelegate.setOnTabIndicatorCheckedChangeListener(mOnTabIndicatorCheckedChangeListener);
        mIndicatorDelegate.init();
    }

    private IndicatorDelegate5.OnTabIndicatorCheckedChangeListener mOnTabIndicatorCheckedChangeListener
            = new IndicatorDelegate5.OnTabIndicatorCheckedChangeListener() {
        @Override
        public void onTabIndicatorCheckedChanged(int checkedPosition) {
            hideAllFragments();
            showFragment(mFragmentList[checkedPosition]);
        }
    };

    private void showFragment(Fragment fragment){
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if(!fragment.isAdded()){
            ft.add(R.id.layout_content, fragment).commitAllowingStateLoss();
            return;
        }
        // 注意这里千万不能加上isHidden()的判断，
        // 因为hide(fragment)之后isHidden()不是立刻改变的（时间很短，但不是0）
        ft.show(fragment).commitAllowingStateLoss();
//        if(fragment.isHidden()){
//            ft.show(fragment).commitAllowingStateLoss();
//        }
    }

    private void hideAllFragments(){
        for (CaptainTestFragment fragment : mFragmentList) {
            hideFragment(fragment);
        }
    }

    private void hideFragment(Fragment fragment){
        if(fragment.isAdded() && !fragment.isHidden()){
            mFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
        }
    }

}
