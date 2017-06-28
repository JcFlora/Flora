package com.jc.flora.apps.component.upgrade.renovate;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.text.MessageFormat;

/**
 * Created by Samurai on 2016/6/12.
 */
public class AppUpgradeInfo {

    /** 强制升级 */
    private static final int TYPE_FORCE_UPGRADE = 1;
    /** 推荐升级 */
    private static final int TYPE_RECOMMEND_UPGRADE = 0;

    private static final String DISPLAY_MESSAGE_PATTERN = "更新版本：{0}\n更新日期：{1}\n更新内容：{2}\n";
    private static final String SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/flora/";

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
    /** 升级类型（0：建议升级；1：强制升级） */
    public int type;
    /** 内部版本号 */
    public int versionCode;
    /** 更新时间 */
    public String modifyDate;

    public String getDisplayMessage(){
        return MessageFormat.format(DISPLAY_MESSAGE_PATTERN, versionName, modifyDate, memo);
    }

    public String getFilePathName(){
        return getFilePathName(url);
    }

    public boolean isFileExists(){
        File file = new File(getFilePathName());
        return file.isFile() && file.exists();
    }

    public boolean deleteFile(){
        File file = new File(getFilePathName());
        return file.isFile() && file.exists() && file.delete();
    }

    public boolean isForceUpgrade(){
        return type == TYPE_FORCE_UPGRADE;
    }

    public static String getFilePathName(String url){
        if(TextUtils.isEmpty(url)){
            return null;
        }
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        return SAVE_PATH + fileName;
    }

    public static AppUpgradeInfo createTestInfo(){
        AppUpgradeInfo info = new AppUpgradeInfo();
        info.url = "http://trinea.cn/app/dev-tools.apk";
        info.versionName = "1.0";
        info.type = TYPE_RECOMMEND_UPGRADE;
        info.versionCode = 2;
        info.modifyDate = "3月8日";
        info.memo = "优化";
        return info;
    }

}
