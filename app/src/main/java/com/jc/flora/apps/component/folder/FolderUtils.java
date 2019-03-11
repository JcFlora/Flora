package com.jc.flora.apps.component.folder;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.Formatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.DecimalFormat;

/**
 * 文件处理一般需要两个权限
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * 约定：该工具类下的所有方法的路径参数，如果是目录，必须以“/”结尾。
 * Created by Shijincheng on 2018/6/6.
 */

public class FolderUtils {

    /** 当前应用总文件夹的路径名，若使用隐藏文件夹，文件夹名以.开头 */
    private static final String APP_FOLDER_SDCARD_PATH_NAME = "flora/";

    private static final long K_BYTES = 1000;
    private static final long M_BYTES = K_BYTES * 1000;
    private static final long G_BYTES = M_BYTES * 1000;
    private static final long T_BYTES = G_BYTES * 1000;

/*---------------------------------------------SD卡相关----------------------------------------------*/

    /**
     * 获取SD卡根路径
     * @return
     */
    public static String getSdcardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    }

    /**
     * 获取安装在用户手机上的应用的沙盒cache路径
     * 该路径的数据会随应用卸载一并删除？todo
     * 4.4以前的手机，没装sd卡的话，该路径返回空
     * @return cache path
     */
    public static String getAppExternalCacheFolderPath(Context context) {
        File cacheDir = context.getExternalCacheDir();
        if(cacheDir == null){
            return "";
        }
        return cacheDir.getAbsolutePath() + "/";
    }

    /**
     * 获取当前应用总文件夹的路径
     * @return
     */
    public static String getAppFolderPath() {
        return getSdcardPath() + APP_FOLDER_SDCARD_PATH_NAME;
    }

    /**
     * 创建应用总文件夹
     * @return
     */
    public static boolean createAppFolder(){
        File dir = new File(getAppFolderPath());
        return dir.exists() || dir.mkdirs();
    }

/*---------------------------------------------SD卡空间----------------------------------------------*/

    /**
     * 获取格式化后的Sd卡总空间
     * @param context
     * @return
     */
    public static String getFormatedSdcardTotalSize(@Nullable Context context){
        return formatFileSize(context, getSdcardTotalSize());
    }

    /**
     * 获取格式化后的Sd卡可用空间
     * @param context
     * @return
     */
    public static String getFormatedSdcardAvailableSize(@Nullable Context context){
        return formatFileSize(context, getSdcardAvailableSize());
    }

    /**
     * 获取Sd卡总空间
     * @return
     */
    public static long getSdcardTotalSize() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        long blockCount = statFs.getBlockSize();
        long availableBlocks = statFs.getBlockCount();
        return blockCount * availableBlocks;
    }

    /**
     * 获取Sd卡可用空间
     * @return
     */
    public static long getSdcardAvailableSize() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        long blockSize = statFs.getBlockSize();
        long availableBlocks = statFs.getAvailableBlocks();
        return blockSize * availableBlocks;
    }

/*---------------------------------------------类型转换----------------------------------------------*/

    /**
     * 获取文件的完整文件名，包含后缀名
     * @param path
     * @return
     */
    public static String getFileNameByPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            return path.substring(path.lastIndexOf(File.separator) + 1);
        }
        return "";
    }

    public static File getFileByPath(String path){
        return new File(path);
    }

    public static File getFileByPath(String dirPath, String fileName){
        return new File(dirPath, fileName);
    }

    public static Uri getUriByFilePath(String path){
        return Uri.fromFile(new File(path));
    }

    public static Uri getUriByFilePath(String dirPath, String fileName){
        return Uri.fromFile(new File(dirPath, fileName));
    }

/*---------------------------------------------文件查询----------------------------------------------*/

    /**
     * 根据文件路径判断文件是否存在
     * @param path
     * @return
     */
    public static boolean exists(String path) {
        if(TextUtils.isEmpty(path)){
            return false;
        }
        return new File(path).exists();
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

    /**
     * 获取文件的最后一次修改时间
     * @param path
     * @return
     */
    public static long getLastModifiedTime(String path){
        if(TextUtils.isEmpty(path)){
            return -1L;
        }
        return new File(path).lastModified();
    }

    /**
     * 获取无后缀文件名
     * @param path
     * @return
     */
    public static String getNoExtFileName(String path) {
        return "";
    }

    /**
     * 获取文件后缀名
     * @param path
     * @return
     */
    public static String getFileExtName(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        int dot = path.lastIndexOf('.');
        if ((dot > -1) && (dot < (path.length() - 1))) {
            return path.substring(dot + 1);
        }else{
            return "";
        }
    }

/*todo---------------------------------------------文件遍历筛选-------------------------------------------*/

    /**
     * 遍历文件夹下的所有文件
     * @param path
     * @return
     */
    public static File[] listFiles(String path){
        return null;
    }

    /**
     * 筛选文件夹下的特定格式文件
     * @param path
     * @return
     */
    public static File[] listFiles2(String path){
        return null;
    }

    /**
     * 遍历并排序文件夹下的所有文件
     * @param path
     * @return
     */
    public static File[] listFiles3(String path){
        return null;
    }

/*---------------------------------------------文件创建----------------------------------------------*/

    /**
     * 创建文件
     * @param path  文件全路径名
     * @return
     */
    public static boolean createFile(String path) {
        if(TextUtils.isEmpty(path)){
            return false;
        }
        File file = new File(path);
        if (file.exists()) {
            return false;
        }
        boolean hasParent = true;
        String dirPath = path.substring(0, path.lastIndexOf("/"));
        if(!exists(dirPath)){
            hasParent = createDir(dirPath);
        }
        try {
            return hasParent && file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建文件
     * @param dirPath  父路径
     * @param fileName 文件名
     * @return
     */
    public static boolean createFile(String dirPath, String fileName) {
        if(TextUtils.isEmpty(dirPath) || TextUtils.isEmpty(fileName)){
            return false;
        }
        File file = new File(dirPath, fileName);
        if (file.exists()) {
            return false;
        }
        boolean hasParent = true;
        if(!exists(dirPath)){
            hasParent = createDir(dirPath);
        }
        try {
            return hasParent && file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据文件路径创建文件夹
     *
     * @param path
     */
    private static boolean createDir(String path) {
        if(TextUtils.isEmpty(path)){
            return false;
        }
        return new File(path).mkdirs();
    }

/*---------------------------------------------文件删除----------------------------------------------*/

    /**
     * 删除一个文件或文件夹
     * @param path
     * @return
     */
    public static boolean delete(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return file.delete();
        } else {
            return deleteDir(file);
        }
    }

    /**
     * 删除一个文件夹
     * @param file
     * @return
     */
    private static boolean deleteDir(File file){
        if(file.isFile()){
            return file.delete();
        }
        File[] children = file.listFiles();
        return deleteFiles(children) && file.delete();
    }

    /**
     * 删除文件数组
     * @param files
     * @return
     */
    private static boolean deleteFiles(File[] files){
        if(files == null || files.length == 0){
            return true;
        }
        boolean flag = true;
        for (File child : files){
            if(child.isFile()){
                flag = child.delete();
                if(!flag){
                    break;
                }
            }else{
                flag = deleteDir(child);
                if(!flag) {
                    break;
                }
            }
        }
        return flag;
    }

/*---------------------------------------------文件读出内容------------------------------------------*/

    /**
     * 读取文件
     * @param path
     * @return
     */
    public static String readFromFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        StringBuilder content = new StringBuilder();
        FileChannel channel = null;
        try {
            channel = new FileInputStream(file).getChannel();
            ByteBuffer bb = ByteBuffer.allocate(80_000);
            while (channel.read(bb) != -1) {
                bb.flip();
                CharBuffer charBuffer = Charset.defaultCharset().decode(bb.asReadOnlyBuffer());
                content.append(charBuffer.toString());
                bb.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(channel != null){
                    channel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content.toString().trim();
    }

/*---------------------------------------------文件写入内容------------------------------------------*/

    /**
     * 向文件写入数据
     * @param dirPath
     * @param fileName
     * @param content
     */
    public static void writeToFile(String dirPath, String fileName, String content){
        if(TextUtils.isEmpty(dirPath) || TextUtils.isEmpty(fileName)){
            return;
        }
        File file = new File(dirPath, fileName);
        if(!file.exists()){
            createFile(dirPath, fileName);
        }
        FileChannel channel = null;
        try {
            channel = new FileOutputStream(file).getChannel();
            channel.write(ByteBuffer.wrap(content.getBytes()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(channel != null){
                    channel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

/*---------------------------------------------文件移动----------------------------------------------*/

    /**
     * 文件移动（重命名）
     * 源文件必须存在，目标文件必须不存在
     * @param path1 源文件路径
     * @param path2 目标文件路径
     * @return
     */
    public static boolean move(String path1, String path2){
        if(TextUtils.isEmpty(path1) || TextUtils.isEmpty(path2)){
            return false;
        }
        File file1 = new File(path1);
        File file2 = new File(path2);
        if(!file1.exists() || file2.exists()){
            return false;
        }
        return file1.renameTo(file2);
    }

/*---------------------------------------------文件复制----------------------------------------------*/

    /**
     * 文件复制
     * 源文件必须存在，目标文件必须不存在
     * @param srcPath 源文件路径
     * @param destDirPath 目标文件父路径
     * @param destFileName 目标文件名字
     * @return
     */
    public static boolean copy(String srcPath, String destDirPath, String destFileName){
        if(TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(destDirPath)|| TextUtils.isEmpty(destFileName)){
            return false;
        }
        File srcFile = new File(srcPath);
        File destFile = new File(destDirPath, destFileName);
        if(!srcFile.exists() || destFile.exists()){
            return false;
        }
        if(!createDir(destDirPath)){
            return false;
        }
        copy(srcFile, destFile);
        return true;
    }

    /**
     * 文件复制
     * @param srcFile   源文件
     * @param destFile  目标文件
     */
    private static void copy(final File srcFile, final File destFile) {
        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            fcin = new FileInputStream(srcFile).getChannel();
            fcout = new FileOutputStream(destFile).getChannel();
            fcin.transferTo(0, fcin.size(), fcout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fcin != null) {
                    fcin.close();
                }
                if (fcout != null) {
                    fcout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

/*---------------------------------------------文件合并----------------------------------------------*/

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
        if(file.isFile() && file.canRead()){
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
        if (file.isFile()){
            return file.length();
        }
        long size = 0;
        File[] children = file.listFiles();
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

/*---------------------------------------------压缩解压缩--------------------------------------------*/

/*---------------------------------------------Assert相关--------------------------------------------*/

    /**
     * 从assets文件夹中获取文件并读取数据
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String readFromAssetsFile(Context context, String fileName) {
        String result = "";
        try {
            final InputStream in = context.getResources().getAssets().open(fileName);
            // 获取文件的字节数
            int length = in.available();
            // 创建byte数组
            byte[] buffer = new byte[length];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            result = new String(buffer, "UTF-8");
            in.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}