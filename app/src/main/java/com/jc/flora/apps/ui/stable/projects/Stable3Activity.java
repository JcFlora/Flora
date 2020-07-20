package com.jc.flora.apps.ui.stable.projects;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.stable.delegate.StableDelegate;

/**
 * Created by shijincheng on 2017/3/6.
 */
public class Stable3Activity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private Test1Fragment mTest1Fragment;
    private Test2Fragment mTest2Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StableDelegate stableDelegate = new StableDelegate(this);
        stableDelegate.hideStatusBar();
        setContentView(R.layout.activity_stable3);
        mFragmentManager = getSupportFragmentManager();
        mTest1Fragment = (Test1Fragment)(mFragmentManager.findFragmentById(R.id.fg_test1));
        mTest2Fragment = (Test2Fragment)(mFragmentManager.findFragmentById(R.id.fg_test2));
        mTest2Fragment.fitStatusBar(stableDelegate);
        hideFragment(mTest2Fragment);
    }

    public void switchFragment1(View v){
        hideFragment(mTest2Fragment);
        showFragment(mTest1Fragment);
    }

    public void switchFragment2(View v){
        hideFragment(mTest1Fragment);
        showFragment(mTest2Fragment);
    }

    private void showFragment(Fragment fragment){
        mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
    }

    private void hideFragment(Fragment fragment){
        mFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

}