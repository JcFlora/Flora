package com.jc.flora.launcher;

import android.graphics.Color;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.ui.captain.delegate.CaptainFragment;
import com.jc.flora.apps.ui.captain.delegate.FragmentDelegate18;
import com.jc.flora.apps.ui.captain.delegate.IndicatorDelegate18;
import com.jc.flora.launcher.fragment.LauncherAppFragment;
import com.jc.flora.launcher.fragment.LauncherComponentFragment;
import com.jc.flora.launcher.fragment.LauncherMdFragment;
import com.jc.flora.launcher.fragment.LauncherSceneFragment;
import com.jc.flora.launcher.fragment.LauncherUiFragment;

/**
 * Created by Samurai on 2017/5/27.
 */
public class LauncherCaptainDelegate {

    private static final String[] TITLES = {"组件", "UI", "MD", "场景", "应用"};
    private static final int COUNT = 5;
    private static final int START_INDEX = 0;
    private static final float TEXT_SIZE = 11;
    private static final int COLOR_UNCHECKED = Color.parseColor("#999999");
    private static final int COLOR_CHECKED = Color.parseColor("#00ADED");
    private static final int[] ICON_RES_NORMAL = {R.drawable.launcher_component_normal,
            R.drawable.launcher_ui_normal, R.drawable.launcher_md_normal,
            R.drawable.launcher_scene_normal, R.drawable.launcher_app_normal};
    private static final int[] ICON_RES_FOCUS = {R.drawable.launcher_component_focus,
            R.drawable.launcher_ui_focus, R.drawable.launcher_md_focus,
            R.drawable.launcher_scene_focus, R.drawable.launcher_app_focus};

    private AppCompatActivity mActivity;

    private FragmentDelegate18 mFragmentDelegate;
    private IndicatorDelegate18 mIndicatorDelegate;
    private LinearLayout mLayoutCaptainIndicators;
    private ViewPager mVpContainer;

    public void setActivity(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void setLayoutCaptainIndicators(LinearLayout layoutCaptainIndicators) {
        mLayoutCaptainIndicators = layoutCaptainIndicators;
    }

    public void setVpContainer(ViewPager vpContainer) {
        mVpContainer = vpContainer;
    }

    public void init(){
        initFragments();
        initIndicators();
        start();
    }

    private void initFragments(){
        CaptainFragment[] fragmentList = new CaptainFragment[COUNT];
        fragmentList[0] = new LauncherComponentFragment();
        fragmentList[1] = new LauncherUiFragment();
        fragmentList[2] = new LauncherMdFragment();
        fragmentList[3] = new LauncherSceneFragment();
        fragmentList[4] = new LauncherAppFragment();
        mFragmentDelegate = new FragmentDelegate18();
        mFragmentDelegate.setVpContainer(mVpContainer);
        mFragmentDelegate.setFragmentList(fragmentList);
    }

    private void initIndicators() {
        mIndicatorDelegate = new IndicatorDelegate18();
        mIndicatorDelegate.setCount(COUNT);
        mIndicatorDelegate.setTitles(TITLES);
        mIndicatorDelegate.setTextSize(TEXT_SIZE);
        mIndicatorDelegate.setTextColor(COLOR_UNCHECKED, COLOR_CHECKED);
        mIndicatorDelegate.setIconResNormal(ICON_RES_NORMAL);
        mIndicatorDelegate.setIconResFocus(ICON_RES_FOCUS);
        mIndicatorDelegate.setLayoutCaptainIndicators(mLayoutCaptainIndicators);
        mIndicatorDelegate.setOnTabIndicatorClickListener(mFragmentDelegate);
    }

    private void start(){
        mFragmentDelegate.setOnPageChangeListener(mIndicatorDelegate);
        mFragmentDelegate.init(mActivity);
        mIndicatorDelegate.init(START_INDEX, true);
    }

    public boolean back2Tab0() {
        return mIndicatorDelegate.back2Tab0();
    }

}
