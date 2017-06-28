package com.jc.flora.apps.ui.stable.delegate;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jc.flora.apps.component.deviceinfo.DeviceUtil;

/**
 * 沉浸模式业务管理
 * Created by shijincheng on 2017/3/6.
 */
public class StableDelegate {

    private Activity mActivity;

    public StableDelegate(Activity activity) {
        mActivity = activity;
    }

    public void hideStatusBar() {
        hideStatusBarOrNavigation(false);
    }

    public void fitStatusBar(View fitView) {
        fitStatusBarOrNavigation(fitView, false);
    }

    public void hideStatusBarAndNavigation() {
        hideStatusBarOrNavigation(true);
    }

    public void fitStatusBarAndNavigation(View fitView) {
        fitStatusBarOrNavigation(fitView, true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColorResource(@ColorRes int resId){
        if (!isSdkSupport()) {
            return;
        }
        mActivity.getWindow().setStatusBarColor(mActivity.getResources().getColor(resId));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(int color){
        if (!isSdkSupport()) {
            return;
        }
        mActivity.getWindow().setStatusBarColor(color);
    }

    private void hideStatusBarOrNavigation(boolean isHideNavigation) {
        if (!isSdkSupport()) {
            hideActionBar();
            return;
        }
        Window window = mActivity.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        if (isHideNavigation) {
            hideStatusBarAndNavigation(window);
        } else {
            hideStatusBar(window);
        }
        hideActionBar();
    }

    /**
     * 不要使用fitSystemWindow这个属性来适配沉浸模式下的高度，使用手动方式计算最靠谱
     * @param fitView
     * @param isHideNavigation
     */
    private void fitStatusBarOrNavigation(View fitView, boolean isHideNavigation) {
        if (!isSdkSupport()) {
            return;
        }
        int statusBarHeight = DeviceUtil.getStatusBarHeight(mActivity);
        int navigationHeight = DeviceUtil.getNavigationHeight(mActivity);
        ViewGroup.LayoutParams params = fitView.getLayoutParams();
        params.height += isHideNavigation ? (statusBarHeight + navigationHeight) : statusBarHeight;
        int paddingStart = fitView.getPaddingStart();
        int paddingTop = fitView.getPaddingTop() + statusBarHeight;
        int paddingEnd = fitView.getPaddingEnd();
        int paddingBottom = isHideNavigation ?
                (fitView.getPaddingBottom() + navigationHeight) : fitView.getPaddingBottom();
        fitView.setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom);
    }

    private void hideActionBar(){
        if(mActivity instanceof AppCompatActivity){
            ActionBar actionBar = ((AppCompatActivity)mActivity).getSupportActionBar();
            if(actionBar != null){
                actionBar.hide();
            }
        }else{
            android.app.ActionBar actionBar = mActivity.getActionBar();
            if(actionBar != null){
                actionBar.hide();
            }
        }
    }

    private boolean isSdkSupport(){
        return DeviceUtil.isSystemVersionAfterLollipop();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void hideStatusBar(Window window){
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void hideStatusBarAndNavigation(Window window){
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
    }

}
