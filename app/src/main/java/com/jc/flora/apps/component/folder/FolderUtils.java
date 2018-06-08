package com.jc.flora.apps.component.folder;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.Formatter;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * 文件处理一般需要两个权限
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * 约定：该工具类下的所有方法的路径参数，如果是目录，必须以“/”结尾。
 * Created by Shijincheng on 2018/6/6.
 */

public class FolderUtils {

    /** 当前应用总文件夹的路径名 */
    private static final String APP_FOLDER_SDCARD_PATH_NAME = "flora/";

    private static final long K_BYTES = 1000;
    private static final long M_BYTES = K_BYTES * 1000;
    private static final long G_BYTES = M_BYTES * 1000;
    private static final long T_BYTES = G_BYTES * 1000;

    /**
     * 获取SD卡根路径
     * @return
     */
    public static String getSdcardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    }

    /**
     * 获取当前应用总文件夹的路径
     * @return
     */
    public static String getAppFolderPath() {
        return getSdcardPath() + APP_FOLDER_SDCARD_PATH_NAME;
    }

/*---------------------------------------------文件查询----------------------------------------------*/

    /**
     * 根据文件路径判断文件是否存在
     * @param path
     * @return
     */
    private static boolean exists(String path) {
        if(TextUtils.isEmpty(path)){
            return false;
        }
        return !TextUtils.isEmpty(path) && new File(path).exists();
    }

    /**
     * 根据文件父路径和文件名判断文件是否存在
     * @param dirPath 目录
     * @param fileName 文件名称
     * @return true->存在；否则表示不存在
     */
    public static boolean exists(String dirPath, String fileName) {
        if(TextUtils.isEmpty(dirPath) || TextUtils.isEmpty(fileName)){
            return false;
        }
        return new File(dirPath, fileName).exists();
    }

/*---------------------------------------------文件创建----------------------------------------------*/

    /**
     * 创建文件
     * @param dirPath  父路径
     * @param fileName 文件名
     * @return
     */
    public static boolean createFile(String dirPath, String fileName) {
        File file = new File(dirPath, fileName);
        if (file.exists()) {
            return false;
        }else {
            boolean hasParent = true;
            if(!exists(dirPath)){
                hasParent = createDir(dirPath);
            }
            try {
                return hasParent && file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 根据文件路径创建文件夹
     *
     * @param path
     */
    private static boolean createDir(String path) {
        File dir = new File(path);
        if(dir.exists()){
            return false;
        }else{
            return dir.mkdirs();
        }
    }

/*---------------------------------------------文件删除----------------------------------------------*/

/*---------------------------------------------文件写入内容------------------------------------------*/

/*---------------------------------------------文件复制----------------------------------------------*/

/*---------------------------------------------文件合并----------------------------------------------*/

/*---------------------------------------------压缩解压缩--------------------------------------------*/

/*---------------------------------------------文件长度----------------------------------------------*/

    /**
     * 获取格式化后的文件长度
     * @param context
     * @param path
     * @return
     */
    public static String getFormatedFileSize(@Nullable Context context, String path){
        long fileSize = getFileSize(path);
        return formatFileSize(context, fileSize);
    }

    /**
     * 获取格式化后的指定目录下文件的总大小
     * @param context
     * @param path
     * @return
     */
    public static String getFormatedDirSize(@Nullable Context context, String path){
        long dirSize = getDirSize(path);
        return formatFileSize(context, dirSize);
    }

    /**
     * 获取文件长度
     * @param path
     * @return 如果此文件不存在、不可读、文件夹则返回 -1；否则返回文件长度（单位：字节）
     */
    public static long getFileSize(String path){
        File file = new File(path);
        if(file.exists() && file.canRead() && file.isFile()){
            return file.length();
        }
        return -1;
    }

    /**
     * 获取指定目录下文件的总大小
     * @param path
     * @return
     */
    public static long getDirSize(String path) {
        return getDirSize(new File(path));
    }

    /**
     * 递归获取指定目录下文件的总大小
     * 如果目录不存在，返回-1
     * @param file
     * @return
     */
    private static long getDirSize(File file) {
        if(!file.exists()){
            return -1;
        }
        if (file.isFile() && !file.isDirectory()){
            return file.length();
        }
        long size = 0;
        final File[] children = file.listFiles();
        if (children != null){
            for (File child : children){
                size += getDirSize(child);
            }
        }
        return size;
    }

    /**
     * 格式化文件长度
     * 这个使用的是安卓系统提供的方法
     * @param context
     * @param fileSize
     * @return
     */
    public static String formatFileSize(@Nullable Context context, long fileSize) {
        return Formatter.formatFileSize(context, fileSize);
    }

    /**
     * 格式化文件长度
     * @param fileSize
     * @return
     */
    public static String formatFileSize(long fileSize){
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString ;
        if (fileSize < K_BYTES) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < M_BYTES) {
            fileSizeString = df.format((double) fileSize / K_BYTES) + "KB";
        } else if (fileSize < G_BYTES) {
            fileSizeString = df.format((double) fileSize / M_BYTES) + "MB";
        } else if (fileSize < T_BYTES) {
            fileSizeString = df.format((double) fileSize / G_BYTES) + "GB";
        } else {
            fileSizeString = df.format((double) fileSize / T_BYTES) + "TB";
        }
        return fileSizeString;
    }

}