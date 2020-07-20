package com.jc.flora.apps.component.upgrade.renovate;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.widget.CompoundButton;

import com.jc.flora.apps.component.deviceinfo.DeviceUtil;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.io.File;

/**
 * 升级检测业务管理
 * 需配置7.0FileProvider
 * Created by Samurai on 2016/6/12.
 */
public class VersionCheckDelegate {

    /** 当前界面 */
    private AppCompatActivity mActivity;

    /** 更新按钮的监听 */
    private OnUpgradeClickListener mOnUpgradeClickListener;
    /** 取消或不需要升级时的回调 */
    private Runnable mOnPassUpgradeCallback;
    /** 自动检测 */
    private boolean mIsAutoCheck = false;

    /**
     * 升级检测业务管理
     *
     * @param activity 当前界面
     */
    public VersionCheckDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void setAutoCheck(boolean autoCheck) {
        mIsAutoCheck = autoCheck;
    }

    /**
     * 设置更新按钮的监听
     *
     * @param onUpgradeClickListener 更新按钮的监听
     */
    public void setOnUpgradeClickListener(OnUpgradeClickListener onUpgradeClickListener) {
        mOnUpgradeClickListener = onUpgradeClickListener;
    }

    /**
     * 设置取消或不需要升级时的回调
     *
     * @param onPassUpgradeCallback 取消或不需要升级时的回调
     */
    public void setOnPassUpgradeCallback(Runnable onPassUpgradeCallback) {
        mOnPassUpgradeCallback = onPassUpgradeCallback;
    }

    /** 开始升级检测的业务 */
    public void startCheckAppUpgrade() {
        // 发起升级检测的请求
        checkAppUpgradeRequest();
    }

    /** 发起请求：升级检测 */
    private void checkAppUpgradeRequest() {
        new CheckVersionUpgradeApi(new CheckVersionUpgradeApi.Listener<AppUpgradeInfo>() {
            @Override
            public void onResponse(AppUpgradeInfo response) {
                onCheckAppUpdateResponse(response);
            }
        }, new CheckVersionUpgradeApi.ErrorListener() {
            @Override
            public void onErrorResponse() {
                onErrorOrNullResponse();
            }
        }).sendRequest();
    }

    /**
     * 升级检测成功后的回调
     *
     * @param response 响应实体
     */
    private void onCheckAppUpdateResponse(AppUpgradeInfo response) {
        if (response == null || TextUtils.isEmpty(response.memo) || TextUtils.isEmpty(response.url)) {
            onErrorOrNullResponse();
            return;
        }
        if (isNeedUpdate(response)) {
            if(!response.isForceUpgrade() && Renovate.sIsUserRefuseUpgrade && mIsAutoCheck){
                onNotNeedUpgradeResponse();
            }else if(response.isFileExists()){
                showAppInstallDialog(response);
            }else{
                showAppUpgradeDialog(response);
            }
        }else{
            onNotNeedUpgradeResponse();
        }
    }

    /** 升级检测异常的回调 */
    private void onErrorOrNullResponse() {
        toastOrPass("检测异常");
    }

    /** 不需要升级的回调 */
    private void onNotNeedUpgradeResponse() {
        toastOrPass("当前版本已经是最新版本");
    }

    /**
     * 是否需要升级
     *
     * @param response 升级信息
     * @return 是否需要升级
     */
    private boolean isNeedUpdate(AppUpgradeInfo response) {
        // 比较版本号，确定是否需要升级
        return response.versionCode > DeviceUtil.getAppVersionCode(mActivity);
    }

    /**
     * 显示安装对话框
     *
     * @param response 升级检测信息
     */

    private void showAppInstallDialog(final AppUpgradeInfo response) {
        showAppDialog(response, true);
    }

    /**
     * 显示升级对话框
     *
     * @param response 升级检测信息
     */
    private void showAppUpgradeDialog(final AppUpgradeInfo response) {
        showAppDialog(response, false);
    }

    /**
     * 显示对话框
     *
     * @param response  升级检测信息
     * @param isInstall
     */
    private void showAppDialog(final AppUpgradeInfo response, final boolean isInstall) {
        // 创建并显示检测对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("升级提醒");
        builder.setMessage(isInstall ? "升级包已下载好，是否立刻安装？" : response.getDisplayMessage());
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onOkClick(response, isInstall);
            }
        });
        String text = response.isForceUpgrade() ? "退出" : "以后再说";
        builder.setNegativeButton(text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onCancelClick(response.isForceUpgrade());
            }
        });
        if(mIsAutoCheck && !response.isForceUpgrade()) {
            AppCompatCheckBox cb = new AppCompatCheckBox(mActivity);
            cb.setText("不再提示");
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Renovate.sIsUserRefuseUpgrade = isChecked;
                }
            });
            builder.setView(cb, 40, 0, 40, 0);
        }
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 确认按钮的事件
     *
     * @param response 升级检测信息
     * @param isInstall
     */
    private void onOkClick(final AppUpgradeInfo response, boolean isInstall) {
        if(isInstall){
            installApk(response);
        }else if (mOnUpgradeClickListener != null){
            mOnUpgradeClickListener.onUpgradeClick(response.url, response.isForceUpgrade());
        }
    }

    private void installApk(final AppUpgradeInfo response) {
        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(response.getFilePathName());
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setDataAndType(FileProvider.getUriForFile(mActivity, mActivity.getPackageName() + ".fileProvider", file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    /**
     * 取消按钮的事件
     *
     * @param isForceUpgrade 是否是强制升级
     */
    private void onCancelClick(boolean isForceUpgrade) {
        if (mIsAutoCheck) {
            if (isForceUpgrade){
                // 强制升级，取消则退出
                mActivity.finish();
            }
            else if (mOnPassUpgradeCallback != null){
                // 推荐升级，取消继则续后续操作
                mOnPassUpgradeCallback.run();
            }
        }
    }

    /**
     * 提示或跳过
     * @param toastMsg 提示消息
     */
    private void toastOrPass(String toastMsg) {
        if (!mIsAutoCheck) {
            // 手动检测只需提示即可
            ToastDelegate.show(mActivity, toastMsg);
        } else if (mOnPassUpgradeCallback != null) {
            // 自动检测继续下一步
            mOnPassUpgradeCallback.run();
        }
    }

    /** 更新按钮的监听 */
    public interface OnUpgradeClickListener {
        void onUpgradeClick(String apkUrl, boolean isForceUpgrade);
    }

}
