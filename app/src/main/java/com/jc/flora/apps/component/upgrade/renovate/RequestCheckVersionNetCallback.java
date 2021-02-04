package com.jc.flora.apps.component.upgrade.renovate;

/**
 * 升级器检测升级后的回调调用
 * 内部实现，外部调用即可
 * Created by shijincheng on 2021/1/5.
 */
public interface RequestCheckVersionNetCallback {
    /**
     * 升级器检测升级接口调用成功后，调用此方法
     * @param upgradeInfo
     */
    void onSuccess(UpgradeInfoDataSource upgradeInfo);

    /**
     * 升级器检测升级接口调用失败后，调用此方法
     * @param errorMsg
     */
    void onFail(String errorMsg);
}