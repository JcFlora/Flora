package com.jc.flora.apps.component.upgrade.renovate;

/**
 * 跳过升级记录的数据源
 * Created by shijincheng on 2021/1/5.
 */
public interface SkipRecordDataSource {

    /**
     * 是否跳过此次升级
     * 如果本地有过用户手动选择跳过此次升级的记录
     * @param upgradeInfo
     * @return
     */
    boolean isSkip(UpgradeInfoDataSource upgradeInfo);

    /**
     * 用户手动跳过此次升级时，将数据保存到本地
     * @param upgradeInfo
     */
    void writeSkipRecord(UpgradeInfoDataSource upgradeInfo);

}