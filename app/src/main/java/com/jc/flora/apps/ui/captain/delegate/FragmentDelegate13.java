package com.jc.flora.apps.ui.captain.delegate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by shijincheng on 2017/5/13.
 */
public class FragmentDelegate13 implements IndicatorDelegate13.OnTabIndicatorCheckedChangeListener{

    private int mContainerViewId;
    private Fragment[] mFragmentList;
    private FragmentManager mFragmentManager;

    private ViewPager mVpContainer;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    public void setContainerViewId(int containerViewId) {
        mContainerViewId = containerViewId;
    }

    public void setFragmentList(Fragment[] fragmentList) {
        mFragmentList = fragmentList;
    }

    public void setVpContainer(ViewPager vpContainer) {
        mVpContainer = vpContainer;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public void init(AppCompatActivity activity){
        mFragmentManager = activity.getSupportFragmentManager();
        if(mVpContainer != null){
            addPageChangeListener();
            setAdapter();
        }
    }

    private void addPageChangeListener(){
        if(mOnPageChangeListener!= null){
            mVpContainer.addOnPageChangeListener(mOnPageChangeListener);
        }
    }

    private void setAdapter() {
        final int count = mFragmentList.length;
        FragmentStatePagerAdapter mAdapter = new FragmentStatePagerAdapter(mFragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList[position];
            }

            @Override
            public int getCount() {
                return count;
            }
        };
        mVpContainer.setOffscreenPageLimit(count - 1);
        mVpContainer.setAdapter(mAdapter);
    }

    @Override
    public void onTabIndicatorCheckedChanged(int checkedPosition) {
        if (mContainerViewId > 0) {
            hideAllFragments();
            showFragment(mFragmentList[checkedPosition]);
        }else if(mVpContainer != null){
            // 注意这里要使用两个参数的重载方法，并且传入false
            // 这样，点击指示器进行切换时，ViewPager不会有切换动画
            mVpContainer.setCurrentItem(checkedPosition, false);
        }
    }

    private void showFragment(Fragment fragment){
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if(!fragment.isAdded()){
            ft.add(mContainerViewId, fragment).commitAllowingStateLoss();
            return;
        }
        ft.show(fragment).commitAllowingStateLoss();
    }

    private void hideAllFragments(){
        for (Fragment fragment : mFragmentList) {
            hideFragment(fragment);
        }
    }

    private void hideFragment(Fragment fragment){
        if(fragment.isAdded() && !fragment.isHidden()){
            mFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
        }
    }

}
