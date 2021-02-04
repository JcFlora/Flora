package com.jc.flora.apps.component.upgrade.delegate;

import android.content.DialogInterface;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.upgrade.api.AppUpgradeInfo;
import com.jc.flora.apps.component.upgrade.api.CheckVersionUpgradeApi;
import com.jc.flora.apps.component.upgrade.renovate.DialogBridge;
import com.jc.flora.apps.component.upgrade.renovate.CheckVersionDialogInteraction;
import com.jc.flora.apps.component.upgrade.renovate.DownloadingDialogInteraction;
import com.jc.flora.apps.component.upgrade.renovate.Renovate;
import com.jc.flora.apps.component.upgrade.renovate.RequestCheckVersionNetBridge;
import com.jc.flora.apps.component.upgrade.renovate.RequestCheckVersionNetCallback;
import com.jc.flora.apps.component.upgrade.renovate.SkipRecordDataSource;
import com.jc.flora.apps.component.upgrade.renovate.UpgradeInfoDataSource;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

/**
 * 版本升级器配置
 */
public class RenovateDelegate {

    public static boolean sAutoCheckUpgrade = true;
    private static String sSkipRecord;

    private AppCompatActivity mActivity;
    /**
     * 版本升级器
     */
    private Renovate mRenovate;
    /**
     * 是否是自动检测模式
     */
    private boolean mIsAutoCheck = false;
    /**
     * 下载对话框中的进度条
     */
    private ProgressBar mProgress;
    /**
     * 下载对话框中的进度显示
     */
    private TextView mTvProgress;

    /**
     * 静态初始化
     */
    public static void init() {
        // 初始化Renovate框架，实际项目中，在Application的onCreate方法中调用
        Renovate.init(new SkipRecordDataSource() {
            @Override
            public boolean isSkip(UpgradeInfoDataSource upgradeInfo) {
                return upgradeInfo.getNewVersionName().equals(sSkipRecord);
            }

            @Override
            public void writeSkipRecord(UpgradeInfoDataSource upgradeInfo) {
                sSkipRecord = upgradeInfo == null ? "" : upgradeInfo.getNewVersionName();
            }
        });
    }

    public RenovateDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    /**
     * 初始化自动检测模式
     * @param onPassUpgradeCallback
     */
    public void initAutoCheck(Runnable onPassUpgradeCallback) {
        mIsAutoCheck = true;
        mRenovate = Renovate.create(mActivity)
                .setOnPassUpgradeCallback(onPassUpgradeCallback)
                .setRequestCheckVersionNetBridge(mNetBridge)
                .setDialogBridge(mDialogBridge)
                .setAutoCheck();
    }

    /**
     * 初始化手动检测模式
     */
    public void initManualCheck() {
        mIsAutoCheck = false;
        mRenovate = Renovate.create(mActivity)
                .setRequestCheckVersionNetBridge(mNetBridge)
                .setDialogBridge(mDialogBridge)
                .setManualCheck();
    }

    /**
     * 开始升级检测
     */
    public void start(){
        if(mRenovate != null){
            mRenovate.start();
        }
    }

    /**
     * 检测升级的桥接接口
     */
    private final RequestCheckVersionNetBridge mNetBridge = new RequestCheckVersionNetBridge() {
        @Override
        public void requestCheckVersion(RequestCheckVersionNetCallback callback) {
            requestCheckUpgrade(callback);
        }
    };

    /**
     * 升级器对话框的桥接接口
     */
    private final DialogBridge mDialogBridge = new DialogBridge() {
        @Override
        public void showCheckVersionDialog(UpgradeInfoDataSource upgradeInfo, CheckVersionDialogInteraction interaction) {
            showCheckUpgradeDialog(upgradeInfo, interaction);
        }
        @Override
        public void showToastMsg(String toastMsg) {
            ToastDelegate.show(mActivity, toastMsg);
        }
        @Override
        public void showDownloadingDialog(UpgradeInfoDataSource upgradeInfo, DownloadingDialogInteraction interaction) {
            showUpgradeDialog(upgradeInfo, interaction);
        }
        @Override
        public void updateDownloadingProgress(int downloadProgress) {
            updateProgress(downloadProgress);
        }
    };

    /**
     * 发起请求：升级检测
     */
    private void requestCheckUpgrade(final RequestCheckVersionNetCallback callback) {
        new CheckVersionUpgradeApi(new CheckVersionUpgradeApi.Listener<AppUpgradeInfo>() {
            @Override
            public void onResponse(AppUpgradeInfo response) {
                callback.onSuccess(response);
            }
        }, new CheckVersionUpgradeApi.ErrorListener() {
            @Override
            public void onErrorResponse() {
                callback.onFail("检测异常");
            }
        }).sendRequest();
    }

    /**
     * 展示升级检测的对话框
     * @param upgradeInfo
     * @param interaction
     */
    public void showCheckUpgradeDialog(final UpgradeInfoDataSource upgradeInfo, final CheckVersionDialogInteraction interaction) {
        // 创建并显示检测对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("升级提醒");
        boolean willInstall = upgradeInfo.isFileExists(mActivity);
        builder.setMessage(willInstall ? "升级包已下载好，是否立刻安装？" : upgradeInfo.getDisplayMessage());
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(interaction != null){
                    interaction.doUpgradeOrInstall(upgradeInfo);
                }
            }
        });
        String text = (mIsAutoCheck && upgradeInfo.isForceUpgrade()) ? "退出" : "以后再说";
        builder.setNegativeButton(text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(interaction != null){
                    interaction.doSkipOrCancel(upgradeInfo);
                }
            }
        });
        if (mIsAutoCheck && !upgradeInfo.isForceUpgrade()) {
            AppCompatCheckBox cb = new AppCompatCheckBox(mActivity);
            cb.setText("不再提示");
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(interaction != null){
                        interaction.doWriteSkipRecord(isChecked ? upgradeInfo : null);
                    }
                }
            });
            builder.setView(cb, 40, 0, 40, 0);
        }
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 展示下载升级的对话框
     * @param upgradeInfo
     * @param interaction
     */
    private void showUpgradeDialog(final UpgradeInfoDataSource upgradeInfo, final DownloadingDialogInteraction interaction) {
        // 构造App下载对话框
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mActivity);
        builder.setTitle("正在下载");
        // 给下载对话框增加进度条
        View v = View.inflate(mActivity, R.layout.progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.progress);
        mTvProgress = (TextView) v.findViewById(R.id.tv_progress);
        builder.setView(v);
        // 取消按钮事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(interaction != null){
                    interaction.doCancelDownloading(upgradeInfo);
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 更新下载进度
     * @param downloadProgress
     */
    private void updateProgress(int downloadProgress) {
        mProgress.setProgress(downloadProgress);
        mTvProgress.setText(downloadProgress+"%");
    }

}