package com.jc.flora.apps.component.upgrade.renovate;

import androidx.appcompat.app.AppCompatActivity;

/**
 * App更新业务管理
 * Created by Samurai on 2016/6/12.
 */
public class VersionUpgradeDelegate {

    /** 当前界面 */
    private AppCompatActivity mActivity;
    /** 取消或不需要升级时的回调 */
    private Runnable mOnPassUpgradeCallback;
    /** 升级器对话框的桥接接口 */
    private DialogBridge mDialogBridge;
    /** App下载工具类 */
    private VersionDownloadUtil mDownloadUtil;
    /** 自动检测模式，默认为手动升级模式 */
    private boolean mIsAutoCheck = false;

    /**
     * 升级业务管理
     *
     * @param activity 当前界面
     */
    VersionUpgradeDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    /**
     * 设置自动检测模式
     * @param autoCheck
     */
    void setAutoCheck(boolean autoCheck) {
        mIsAutoCheck = autoCheck;
    }

    /**
     * 设置取消或不需要升级时的回调
     *
     * @param onPassUpgradeCallback 取消或不需要升级时的回调
     */
    void setOnPassUpgradeCallback(Runnable onPassUpgradeCallback) {
        mOnPassUpgradeCallback = onPassUpgradeCallback;
    }

    /**
     * 设置升级时的信息对话框的桥接接口
     * @param bridge
     */
    void setDialogBridge(DialogBridge bridge) {
        mDialogBridge = bridge;
    }

    /**
     * 开始下载升级
     *
     * @param upgradeInfo   升级检测信息
     */
    void startUpgrade(UpgradeInfoDataSource upgradeInfo) {
        showDownloadingDialog(upgradeInfo);
        String apkUrl = upgradeInfo.getNewVersionApkUrl();
        String filePathName = upgradeInfo.getFilePathName(mActivity);
        // 创建下载工具类
        mDownloadUtil = new VersionDownloadUtil(mActivity, apkUrl, filePathName, mOnDownloadProgressChanged);
        // 开始下载升级
        mDownloadUtil.startUpgrade();
    }

    /**
     * 展示升级下载对话框
     * @param upgradeInfo
     */
    private void showDownloadingDialog(UpgradeInfoDataSource upgradeInfo){
        if(mDialogBridge != null){
            mDialogBridge.showDownloadingDialog(upgradeInfo, new DownloadingDialogInteraction() {
                @Override
                public void doCancelDownloading(UpgradeInfoDataSource upgradeInfo) {
                    cancelUpgrade(upgradeInfo);
                }
            });
        }
    }

    /**
     * 取消升级下载
     *
     * @param upgradeInfo 升级检测信息
     */
    private void cancelUpgrade(UpgradeInfoDataSource upgradeInfo) {
        // 停止下载
        mDownloadUtil.cancelUpgrade();
        boolean isForceUpgrade = upgradeInfo.isForceUpgrade();
        if (mIsAutoCheck) {
            if (isForceUpgrade) {
                // 强制更新，取消则退出
                mActivity.finish();
            } else if (mOnPassUpgradeCallback != null) {
                // 建议更新，取消继则续后续操作
                mOnPassUpgradeCallback.run();
            }
        }
    }

    /** 下载过程中的回调 */
    private VersionDownloadUtil.onDownloadProgressChangedListener mOnDownloadProgressChanged
            = new VersionDownloadUtil.onDownloadProgressChangedListener() {
        @Override
        public void onDownloadProgressChanged(int downloadProgress) {
            // 更新下载进度
            if(mDialogBridge != null){
                mDialogBridge.updateDownloadingProgress(downloadProgress);
            }
        }
    };

}