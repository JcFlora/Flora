package com.jc.flora.apps.ui.captain.delegate;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2017/5/13.
 */
public class FragmentDelegate7 implements IndicatorDelegate7.OnTabIndicatorCheckedChangeListener{

    private int mContainerViewId;
    private Fragment[] mFragmentList;
    private FragmentManager mFragmentManager;

    public void setContainerViewId(int containerViewId) {
        mContainerViewId = containerViewId;
    }

    public void setFragmentList(Fragment[] fragmentList) {
        mFragmentList = fragmentList;
    }

    public void init(AppCompatActivity activity){
        mFragmentManager = activity.getSupportFragmentManager();
    }

    @Override
    public void onTabIndicatorCheckedChanged(int checkedPosition) {
        hideAllFragments();
        showFragment(mFragmentList[checkedPosition]);
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
