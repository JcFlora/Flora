package com.jc.flora.apps.component.upgrade.renovate;

import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;

/**
 * 升级检测业务管理
 * 需配置7.0FileProvider
 * Created by Samurai on 2016/6/12.
 */
public class VersionCheckDelegate {

    /** 自动检测模式下，用户跳过升级的记录数据源 */
    private static SkipRecordDataSource sSkipRecordDataSource;

    /** 当前界面 */
    private AppCompatActivity mActivity;

    /** 更新按钮的监听 */
    private OnUpgradeClickListener mOnUpgradeClickListener;
    /** 取消或不需要升级时的回调 */
    private Runnable mOnPassUpgradeCallback;
    /** 检测升级的桥接接口 */
    private RequestCheckVersionNetBridge mRequestCheckVersionNetBridge;
    /** 升级器对话框的桥接接口 */
    private DialogBridge mDialogBridge;
    /** 自动检测模式，默认为手动升级模式 */
    private boolean mIsAutoCheck = false;

    /**
     * 设置用户跳过升级的记录数据源
     * @param skipRecordDataSource
     */
    static void setSkipRecordDataSource(SkipRecordDataSource skipRecordDataSource) {
        sSkipRecordDataSource = skipRecordDataSource;
    }

    /**
     * 升级检测业务管理
     *
     * @param activity 当前界面
     */
    VersionCheckDelegate(AppCompatActivity activity) {
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
     * 设置更新按钮的监听
     *
     * @param onUpgradeClickListener 更新按钮的监听
     */
    void setOnUpgradeClickListener(OnUpgradeClickListener onUpgradeClickListener) {
        mOnUpgradeClickListener = onUpgradeClickListener;
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
     * 设置检测升级的桥接接口
     * @param bridge
     */
    void setRequestCheckVersionNetBridge(RequestCheckVersionNetBridge bridge) {
        mRequestCheckVersionNetBridge = bridge;
    }

    /**
     * 设置升级时的信息对话框的桥接接口
     * @param bridge
     */
    void setDialogBridge(DialogBridge bridge) {
        mDialogBridge = bridge;
    }

    /** 开始升级检测的业务 */
    void startCheckAppUpgrade() {
        // 发起升级检测的请求
        if(mRequestCheckVersionNetBridge != null){
            mRequestCheckVersionNetBridge.requestCheckVersion(new RequestCheckVersionNetCallback() {
                @Override
                public void onSuccess(UpgradeInfoDataSource upgradeInfo) {
                    checkUpgrade(upgradeInfo);
                }
                @Override
                public void onFail(String errorMsg) {
                    checkFail(errorMsg);
                }
            });
        }
    }

    /**
     * 升级检测信息获取成功后检测
     *
     * @param upgradeInfo 升级检测信息
     */
    private void checkUpgrade(UpgradeInfoDataSource upgradeInfo) {
        if (upgradeInfo == null) {
            onErrorOrNullResponse("接口返回数据异常异常");
            return;
        }
        if (!upgradeInfo.isNeedUpgrade()
                || TextUtils.isEmpty(upgradeInfo.getUpgradeContent())
                || TextUtils.isEmpty(upgradeInfo.getNewVersionApkUrl())) {
            onNotNeedUpgradeResponse(upgradeInfo);
            return;
        }
        if (!upgradeInfo.isForceUpgrade() && mIsAutoCheck && isUserSkipUpgrade(upgradeInfo)) {
            onNotNeedUpgradeResponse(upgradeInfo);
        } else {
            showCheckVersionDialog(upgradeInfo);
        }
    }

    /**
     * 升级检测信息获取失败后处理
     */
    private void checkFail(String errorMsg){
        onErrorOrNullResponse(errorMsg);
    }

    /**
     * 用户是否曾经跳过此次版本的升级
     * @param upgradeInfo
     * @return
     */
    private boolean isUserSkipUpgrade(UpgradeInfoDataSource upgradeInfo){
        if(sSkipRecordDataSource == null){
            return false;
        }
        return sSkipRecordDataSource.isSkip(upgradeInfo);
    }

    /**
     * 升级检测异常的回调
     * @param errorMsg
     */
    private void onErrorOrNullResponse(String errorMsg) {
        toastOrPass(errorMsg);
    }

    /**
     * 不需要升级的回调
     * @param upgradeInfo
     */
    private void onNotNeedUpgradeResponse(UpgradeInfoDataSource upgradeInfo) {
        toastOrPass(upgradeInfo.getNotNeedUpgradeToastMsg());
    }

    /**
     * 显示对话框
     *
     * @param upgradeInfo 升级检测信息
     */
    private void showCheckVersionDialog(final UpgradeInfoDataSource upgradeInfo) {
        // 显示检测对话框
        if(mDialogBridge != null){
            mDialogBridge.showCheckVersionDialog(upgradeInfo, new CheckVersionDialogInteraction() {
                @Override
                public void doUpgradeOrInstall(UpgradeInfoDataSource upgradeInfo) {
                    upgradeOrInstall(upgradeInfo);
                }

                @Override
                public void doSkipOrCancel(UpgradeInfoDataSource upgradeInfo) {
                    skipOrCancel(upgradeInfo);
                }

                @Override
                public void doWriteSkipRecord(UpgradeInfoDataSource upgradeInfo) {
                    writeSkipRecord(upgradeInfo);
                }
            });
        }
    }

    /**
     * 更新或安装
     *
     * @param upgradeInfo 升级检测信息
     */
    private void upgradeOrInstall(UpgradeInfoDataSource upgradeInfo) {
        boolean willInstall = upgradeInfo.isFileExists(mActivity);
        if(willInstall){
            Utils.installApk(mActivity, upgradeInfo.getFilePathName(mActivity));
        }else if (mOnUpgradeClickListener != null){
            mOnUpgradeClickListener.onUpgradeClick(upgradeInfo);
        }
    }

    /**
     * 跳过或取消
     *
     * @param upgradeInfo 升级检测信息
     */
    private void skipOrCancel(UpgradeInfoDataSource upgradeInfo) {
        boolean isForceUpgrade = upgradeInfo.isForceUpgrade();
        if (mIsAutoCheck) {
            if (isForceUpgrade){
                // 强制升级，取消则退出
                mActivity.finish();
            } else if (mOnPassUpgradeCallback != null){
                // 推荐升级，取消则继续后续操作
                mOnPassUpgradeCallback.run();
            }
        }
    }

    /**
     * 写入用户跳过版本升级的记录
     *
     * @param upgradeInfo 升级检测信息
     */
    private void writeSkipRecord(UpgradeInfoDataSource upgradeInfo){
        if(sSkipRecordDataSource == null){
            return;
        }
        sSkipRecordDataSource.writeSkipRecord(upgradeInfo);
    }

    /**
     * 提示或跳过
     * @param toastMsg 提示消息
     */
    private void toastOrPass(String toastMsg) {
        if (!mIsAutoCheck) {
            if(mDialogBridge != null){
                // 手动检测只需提示即可
                mDialogBridge.showToastMsg(toastMsg);
            }
        } else if (mOnPassUpgradeCallback != null) {
            // 自动检测继续下一步
            mOnPassUpgradeCallback.run();
        }
    }

    /** 更新按钮的监听 */
    public interface OnUpgradeClickListener {
        void onUpgradeClick(UpgradeInfoDataSource upgradeInfo);
    }

}