package com.jc.flora.apps.component.upgrade.renovate;

/**
 * 升级器下载对话框的交互接口
 * 内部实现，外部调用即可
 * Created by shijincheng on 2021/2/2.
 */
public interface DownloadingDialogInteraction {

    void doCancelDownloading(UpgradeInfoDataSource upgradeInfo);

}