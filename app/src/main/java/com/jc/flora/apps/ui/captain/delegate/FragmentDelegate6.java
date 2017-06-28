package com.jc.flora.apps.ui.captain.delegate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by shijincheng on 2017/5/13.
 */
public class FragmentDelegate6 implements IndicatorDelegate5.OnTabIndicatorCheckedChangeListener{

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
        // 注意这里千万不能加上isHidden()的判断，
        // 因为hide(fragment)之后isHidden()不是立刻改变的（时间很短，但不是0）
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
