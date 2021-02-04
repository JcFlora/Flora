package com.jc.flora.apps.component.distribute.delegate;

import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Created by Shijincheng on 2021/1/11.
 */
public class Distribute11Delegate extends Fragment{

    private AppCompatActivity mActivity;

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            mActivity = activity;
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!AppForegroundDelegate.isForeGround()){
            ToastDelegate.show(mActivity, "从后台返回前台了");
        }
    }

}