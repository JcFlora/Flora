package com.jc.flora.apps.component.upgrade.renovate;

import android.content.Context;

/**
 * 升级信息数据源
 */
public interface UpgradeInfoDataSource {

    /**
     * 更新内容
     * @return
     */
    String getUpgradeContent();

    /**
     * 获取新版本apk的下载地址
     * @return
     */
    String getNewVersionApkUrl();

    /**
     * 是否需要升级，一般由服务器返回的字段直接判断，不需要客户端本地进行判断
     * @return
     */
    boolean isNeedUpgrade();

    /**
     * 是否是强制升级
     * @return
     */
    boolean isForceUpgrade();

    /**
     * 获取新版本的版本号
     * @return
     */
    String getNewVersionName();

    String getDisplayMessage();

    String getFilePathName(Context context);

    boolean isFileExists(Context context);

    boolean deleteFile(Context context);

    String getNotNeedUpgradeToastMsg();

}