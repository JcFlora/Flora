package com.jc.flora.apps.ui.stable.projects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.stable.delegate.StableDelegate;

/**
 * Created by shijincheng on 2017/3/6.
 */
public class Stable4Activity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private Test1Fragment mTest1Fragment;
    private Test3Fragment mTest3Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StableDelegate stableDelegate = new StableDelegate(this);
        stableDelegate.hideStatusBar();
        setContentView(R.layout.activity_stable4);
        mFragmentManager = getSupportFragmentManager();
        mTest1Fragment = (Test1Fragment)(mFragmentManager.findFragmentById(R.id.fg_test1));
        mTest3Fragment = (Test3Fragment)(mFragmentManager.findFragmentById(R.id.fg_test3));
        mTest3Fragment.fitStatusBar(stableDelegate);
        hideFragment(mTest3Fragment);
    }

    public void switchFragment1(View v){
        hideFragment(mTest3Fragment);
        showFragment(mTest1Fragment);
    }

    public void switchFragment2(View v){
        hideFragment(mTest1Fragment);
        showFragment(mTest3Fragment);
    }

    private void showFragment(Fragment fragment){
        mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
    }

    private void hideFragment(Fragment fragment){
        mFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

}