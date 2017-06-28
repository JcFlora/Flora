package com.jc.flora.apps.component.upgrade.renovate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jc.flora.R;

/**
 * App更新业务管理
 * Created by Samurai on 2016/6/12.
 */
public class VersionUpgradeDelegate {

    /** 当前界面 */
    private AppCompatActivity mActivity;
    /** 对话框中的进度条 */
    private ProgressBar mProgress;
    /** 对话框中的进度显示 */
    private TextView mTvProgress;
    /** App下载工具类 */
    private VersionDownloadUtil mDownloadUtil;

    /** 取消或不需要升级时的回调 */
    private Runnable mOnPassUpgradeCallback;

    private boolean mIsAutoCheck = false;

    /**
     * 升级业务管理
     *
     * @param activity 当前界面
     */
    public VersionUpgradeDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void setAutoCheck(boolean autoCheck) {
        mIsAutoCheck = autoCheck;
    }

    /**
     * 设置取消或不需要升级时的回调
     *
     * @param onPassUpgradeCallback 取消或不需要升级时的回调
     */
    public void setOnPassUpgradeCallback(Runnable onPassUpgradeCallback) {
        mOnPassUpgradeCallback = onPassUpgradeCallback;
    }

    /**
     * 开始下载升级
     *
     * @param apkUrl 升级apk地址
     */
    public void startUpgrade(String apkUrl, boolean isForceUpgrade) {
        createDialog(isForceUpgrade).show();
        // 创建下载工具类
        mDownloadUtil = new VersionDownloadUtil(mActivity, apkUrl, mOnDownloadProgressChanged);
        // 开始下载升级
        mDownloadUtil.startUpgrade();
    }

    /**
     * 创建下载对话框
     * @param isForceUpgrade 是否是强制升级
     * @return
     */
    private AlertDialog createDialog(final boolean isForceUpgrade) {
        // 构造App下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
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
                onCancelClick(isForceUpgrade);
            }
        });
        builder.setCancelable(false);
        return builder.create();
    }

    /**
     * 取消按钮的事件
     *
     * @param isForceUpgrade 是否是强制升级
     */
    private void onCancelClick(boolean isForceUpgrade) {
        // 设置取消状态
        mDownloadUtil.cancelUpgrade();
        if (mIsAutoCheck) {
            if (isForceUpgrade){
                // 强制更新，取消则退出
                mActivity.finish();
            }
            else if (mOnPassUpgradeCallback != null){
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
            mProgress.setProgress(downloadProgress);
            mTvProgress.setText(downloadProgress+"%");
        }
    };

}
