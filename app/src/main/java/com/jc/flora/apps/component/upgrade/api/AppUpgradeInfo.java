package com.jc.flora.apps.component.upgrade.api;

import android.content.Context;
import android.text.TextUtils;

import com.jc.flora.apps.component.folder.FolderUtils;
import com.jc.flora.apps.component.upgrade.renovate.UpgradeInfoDataSource;

import java.io.File;
import java.text.MessageFormat;

/**
 * Created by Samurai on 2016/6/12.
 */
public class AppUpgradeInfo implements UpgradeInfoDataSource {

    /** 强制升级 */
    private static final int TYPE_FORCE_UPGRADE = 1;
    /** 推荐升级 */
    private static final int TYPE_RECOMMEND_UPGRADE = 0;

    private static final String DISPLAY_MESSAGE_PATTERN = "更新版本：{0}\n更新日期：{1}\n更新内容：{2}\n";

    /** 包名 */
    public String packageName;
    /** 应用APK名 */
    public String apkName;
    /** 更新的URL */
    public String url;
    /** 更新内容的描述 */
    public String memo;
    /** 版本号 */
    public String versionName;
    /** 是否需要升级，一般由服务器返回的字段直接判断，不需要客户端本地进行判断 */
    public boolean needUpdate;
    /** 升级类型（0：建议升级；1：强制升级） */
    public int type;
    /** 内部版本号 */
    public int versionCode;
    /** 更新时间 */
    public String modifyDate;

    @Override
    public String getUpgradeContent() {
        return memo;
    }

    @Override
    public String getNewVersionApkUrl() {
        return url;
    }

    @Override
    public boolean isNeedUpgrade() {
        return needUpdate;
    }

    @Override
    public String getNewVersionName() {
        return versionName;
    }

    @Override
    public String getDisplayMessage(){
        return MessageFormat.format(DISPLAY_MESSAGE_PATTERN, versionName, modifyDate, memo);
    }

    @Override
    public String getFilePathName(Context context){
        return getFilePathName(context, url);
    }

    @Override
    public boolean isFileExists(Context context){
        File file = new File(getFilePathName(context));
        return file.isFile() && file.exists();
    }

    @Override
    public boolean deleteFile(Context context){
        File file = new File(getFilePathName(context));
        return file.isFile() && file.exists() && file.delete();
    }

    @Override
    public boolean isForceUpgrade(){
        return type == TYPE_FORCE_UPGRADE;
    }

    @Override
    public String getNotNeedUpgradeToastMsg() {
        return "当前版本已经是最新版本";
    }

    private static String getFilePathName(Context context, String url){
        if(TextUtils.isEmpty(url)){
            return null;
        }
        String savePath = FolderUtils.getAppFolderPath(context) + "upgrade/";
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        return savePath + fileName;
    }

    public static AppUpgradeInfo createTestInfo(){
        AppUpgradeInfo info = new AppUpgradeInfo();
        info.url = "http://www.ppmbook.com/apk/phoenix_book_v1.1.0_test.apk";
        info.versionName = "1.0";
        info.needUpdate = true;
        info.type = TYPE_RECOMMEND_UPGRADE;
        info.versionCode = 2;
        info.modifyDate = "3月8日";
        info.memo = "优化";
        return info;
    }

}
