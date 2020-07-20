package com.jc.flora.apps.component.upgrade.renovate;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 版本升级器外观
 * Created by shijincheng on 2017/3/13.
 */
public class Renovate {

    public static boolean sIsUserRefuseUpgrade = false;

    /** 当前界面 */
    private AppCompatActivity mActivity;
    /** 升级检测业务管理 */
    private VersionCheckDelegate mVersionCheckDelegate;
    /** 升级业务管理 */
    private VersionUpgradeDelegate mVersionUpgradeDelegate;

    public static Renovate create(AppCompatActivity activity){
        return new Renovate(activity);
    }

    private Renovate(AppCompatActivity activity) {
        mActivity = activity;
        ready();
    }

    private void ready(){
        // 创建升级检测业务管理
        mVersionCheckDelegate = new VersionCheckDelegate(mActivity);
        // 创建升级业务管理
        mVersionUpgradeDelegate = new VersionUpgradeDelegate(mActivity);
        // 设置更新按钮的监听
        mVersionCheckDelegate.setOnUpgradeClickListener(new VersionCheckDelegate.OnUpgradeClickListener() {
            @Override
            public void onUpgradeClick(String apkUrl, boolean isForceUpgrade) {
                // 开始更新
                mVersionUpgradeDelegate.startUpgrade(apkUrl, isForceUpgrade);
            }
        });
    }

    public Renovate setOnPassUpgradeCallback(Runnable onPassUpgradeCallback){
        mVersionCheckDelegate.setOnPassUpgradeCallback(onPassUpgradeCallback);
        mVersionUpgradeDelegate.setOnPassUpgradeCallback(onPassUpgradeCallback);
        return this;
    }

    public void autoCheck(){
        mVersionCheckDelegate.setAutoCheck(true);
        mVersionUpgradeDelegate.setAutoCheck(true);
        // 开始升级检测的业务
        mVersionCheckDelegate.startCheckAppUpgrade();
    }

    public void manualCheck(){
        // 开始升级检测的业务
        mVersionCheckDelegate.startCheckAppUpgrade();
    }

}
