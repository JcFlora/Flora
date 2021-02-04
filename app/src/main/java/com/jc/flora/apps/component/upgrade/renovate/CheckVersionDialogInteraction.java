package com.jc.flora.apps.component.upgrade.renovate;

/**
 * 升级器检测升级对话框的交互接口
 * 内部实现，外部调用即可
 * Created by shijincheng on 2021/1/29.
 */
public interface CheckVersionDialogInteraction {

    void doUpgradeOrInstall(UpgradeInfoDataSource upgradeInfo);

    void doSkipOrCancel(UpgradeInfoDataSource upgradeInfo);

    void doWriteSkipRecord(UpgradeInfoDataSource upgradeInfo);

}