package com.jc.flora.apps.component.upgrade.renovate;

/**
 * 升级器对话框的桥接接口
 * Created by shijincheng on 2021/1/29.
 */
public interface DialogBridge {
    /**
     * 实现此方法，展示升级时的信息对话框
     * @param upgradeInfo
     * @param interaction
     */
    void showCheckVersionDialog(UpgradeInfoDataSource upgradeInfo, CheckVersionDialogInteraction interaction);

    /**
     * 实现此方法，展示提示信息
     * @param toastMsg
     */
    void showToastMsg(String toastMsg);

    /**
     * 实现此方法，展示下载对话框
     * @param upgradeInfo
     * @param interaction
     */
    void showDownloadingDialog(UpgradeInfoDataSource upgradeInfo, DownloadingDialogInteraction interaction);

    /**
     * 实现此方法，刷新下载进度
     * @param downloadProgress
     */
    void updateDownloadingProgress(int downloadProgress);
}