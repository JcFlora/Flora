package com.jc.flora.apps.component.upgrade.renovate;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 版本升级器外观
 * Created by shijincheng on 2017/3/13.
 */
public class Renovate {

    /** 当前界面 */
    private AppCompatActivity mActivity;
    /** 升级检测业务管理 */
    private VersionCheckDelegate mVersionCheckDelegate;
    /** 升级业务管理 */
    private VersionUpgradeDelegate mVersionUpgradeDelegate;

    /**
     * 设置用户跳过升级的记录数据源，一般用在启动页
     * @param skipRecordDataSource
     */
    public static void init(SkipRecordDataSource skipRecordDataSource) {
        VersionCheckDelegate.setSkipRecordDataSource(skipRecordDataSource);
    }

    public static Renovate create(AppCompatActivity activity){
        return new Renovate(activity);
    }

    private Renovate(AppCompatActivity activity) {
        mActivity = activity;
        ready();
    }

    /**
     * 准备工作
     */
    private void ready(){
        // 创建升级检测业务管理
        mVersionCheckDelegate = new VersionCheckDelegate(mActivity);
        // 创建升级业务管理
        mVersionUpgradeDelegate = new VersionUpgradeDelegate(mActivity);
        // 设置更新按钮的监听
        mVersionCheckDelegate.setOnUpgradeClickListener(new VersionCheckDelegate.OnUpgradeClickListener() {
            @Override
            public void onUpgradeClick(UpgradeInfoDataSource upgradeInfo) {
                // 开始更新
                mVersionUpgradeDelegate.startUpgrade(upgradeInfo);
            }
        });
    }

    /**
     * 设置跳过升级后的回调，一般用在启动页
     * @param onPassUpgradeCallback
     * @return
     */
    public Renovate setOnPassUpgradeCallback(Runnable onPassUpgradeCallback){
        mVersionCheckDelegate.setOnPassUpgradeCallback(onPassUpgradeCallback);
        mVersionUpgradeDelegate.setOnPassUpgradeCallback(onPassUpgradeCallback);
        return this;
    }

    /**
     * 设置检测升级的桥接接口
     * @param bridge
     * @return
     */
    public Renovate setRequestCheckVersionNetBridge(RequestCheckVersionNetBridge bridge) {
        mVersionCheckDelegate.setRequestCheckVersionNetBridge(bridge);
        return this;
    }

    /**
     * 设置升级器对话框的桥接接口
     * @param bridge
     * @return
     */
    public Renovate setDialogBridge(DialogBridge bridge){
        mVersionCheckDelegate.setDialogBridge(bridge);
        mVersionUpgradeDelegate.setDialogBridge(bridge);
        return this;
    }

    /**
     * 设置自动检测模式，一般用在启动页
     * @return
     */
    public Renovate setAutoCheck(){
        mVersionCheckDelegate.setAutoCheck(true);
        mVersionUpgradeDelegate.setAutoCheck(true);
        return this;
    }

    /**
     * 设置手动检测模式，一般用在设置页面
     * @return
     */
    public Renovate setManualCheck(){
        mVersionCheckDelegate.setAutoCheck(false);
        mVersionUpgradeDelegate.setAutoCheck(false);
        return this;
    }

    /**
     * 开始升级检测的业务
     */
    public void start(){
        mVersionCheckDelegate.startCheckAppUpgrade();
    }

}