package com.jc.flora.apps.ui.captain.projects;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.ui.captain.delegate.FragmentDelegate8;
import com.jc.flora.apps.ui.captain.delegate.IndicatorDelegate8;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/5/2.
 */
public class Captain8Activity extends AppCompatActivity {

    private static final String[] TITLES = {"首页", "分类", "购物车", "我的考拉"};
    private static final int COUNT = 4;
    private static final int START_INDEX = 0;
    private static final float TEXT_SIZE = 11;
    private static final int COLOR_UNCHECKED = Color.BLACK;
    private static final int COLOR_CHECKED = Color.parseColor("#d12147");
    private static final int[] ICON_RES_NORMAL = {R.drawable.captain_home_normal,
            R.drawable.captain_category_normal, R.drawable.captain_cart_normal, R.drawable.captain_kaola_normal};
    private static final int[] ICON_RES_FOCUS = {R.drawable.captain_home_focus,
            R.drawable.captain_category_focus, R.drawable.captain_cart_focus, R.drawable.captain_kaola_focus};
    private static final int ICON_RES_RUDDER = R.drawable.captain_pen_focus;
    private static final int RUDDER_MARGIN = 30;

    private FragmentDelegate8 mFragmentDelegate;
    private IndicatorDelegate8 mIndicatorDelegate;
    private LinearLayout mLayoutCaptainIndicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("舵式导航");
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
        mFragmentDelegate = new FragmentDelegate8();
        mFragmentDelegate.setContainerViewId(R.id.layout_content);
        mFragmentDelegate.setFragmentList(fragmentList);
        mFragmentDelegate.init(this);
    }

    private void initIndicators() {
        mIndicatorDelegate = new IndicatorDelegate8();
        mIndicatorDelegate.setCount(COUNT);
        mIndicatorDelegate.setTitles(TITLES);
        mIndicatorDelegate.setTextSize(TEXT_SIZE);
        mIndicatorDelegate.setTextColor(COLOR_UNCHECKED, COLOR_CHECKED);
        mIndicatorDelegate.setIconResNormal(ICON_RES_NORMAL);
        mIndicatorDelegate.setIconResFocus(ICON_RES_FOCUS);
        mIndicatorDelegate.setIconRudder(ICON_RES_RUDDER);
        mIndicatorDelegate.setRudderMargin(RUDDER_MARGIN);
        mIndicatorDelegate.setLayoutCaptainIndicators(mLayoutCaptainIndicators);
        mIndicatorDelegate.setOnTabIndicatorCheckedChangeListener(mFragmentDelegate);
        mIndicatorDelegate.setOnRudderClickListener(mRudderClickListener);
        mIndicatorDelegate.init(START_INDEX);
    }

    private View.OnClickListener mRudderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastDelegate.show(Captain8Activity.this,"你点击了Rudder");
        }
    };

}