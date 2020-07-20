package com.jc.flora.apps.ui.captain.projects;

import android.graphics.Color;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.ui.captain.delegate.FragmentDelegate10;
import com.jc.flora.apps.ui.captain.delegate.IndicatorDelegate10;

/**
 * Created by shijincheng on 2017/5/2.
 */
public class Captain10Activity extends AppCompatActivity {

    private static final String[] TITLES = {"首页", "分类", "发现", "购物车", "我的考拉"};
    private static final int COUNT = 5;
    private static final int START_INDEX = 0;
    private static final float TEXT_SIZE = 11;
    private static final int COLOR_UNCHECKED = Color.BLACK;
    private static final int COLOR_CHECKED = Color.parseColor("#d12147");
    private static final int[] ICON_RES_NORMAL = {R.drawable.captain_home_normal,
            R.drawable.captain_category_normal, R.drawable.captain_discovery_normal,
            R.drawable.captain_cart_normal, R.drawable.captain_kaola_normal};
    private static final int[] ICON_RES_FOCUS = {R.drawable.captain_home_focus,
            R.drawable.captain_category_focus, R.drawable.captain_discovery_focus,
            R.drawable.captain_cart_focus, R.drawable.captain_kaola_focus};

    private FragmentDelegate10 mFragmentDelegate;
    private IndicatorDelegate10 mIndicatorDelegate;
    private ViewPager mVpContainer;
    private LinearLayout mLayoutCaptainIndicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("滑动切换内容页面");
        setContentView(R.layout.activity_captain10);
        initViews();
        initFragments();
        initIndicators();
        start();
    }

    private void initViews() {
        mVpContainer = (ViewPager) findViewById(R.id.vp_content);
        mLayoutCaptainIndicators = (LinearLayout) findViewById(R.id.layout_captain_indicators);
    }

    private void initFragments(){
        CaptainTestFragment[] fragmentList = new CaptainTestFragment[COUNT];
        for (int i = 0; i < COUNT; i++) {
            fragmentList[i] = new CaptainTestFragment();
            fragmentList[i].setTitle(TITLES[i]);
        }
        mFragmentDelegate = new FragmentDelegate10();
        mFragmentDelegate.setVpContainer(mVpContainer);
        mFragmentDelegate.setFragmentList(fragmentList);
    }

    private void initIndicators() {
        mIndicatorDelegate = new IndicatorDelegate10();
        mIndicatorDelegate.setCount(COUNT);
        mIndicatorDelegate.setTitles(TITLES);
        mIndicatorDelegate.setTextSize(TEXT_SIZE);
        mIndicatorDelegate.setTextColor(COLOR_UNCHECKED, COLOR_CHECKED);
        mIndicatorDelegate.setIconResNormal(ICON_RES_NORMAL);
        mIndicatorDelegate.setIconResFocus(ICON_RES_FOCUS);
        mIndicatorDelegate.setLayoutCaptainIndicators(mLayoutCaptainIndicators);
        mIndicatorDelegate.setOnTabIndicatorCheckedChangeListener(mFragmentDelegate);
        mFragmentDelegate.setOnPageChangeListener(mIndicatorDelegate);
    }

    private void start(){
        mFragmentDelegate.init(this);
        mIndicatorDelegate.init(START_INDEX);
    }

}