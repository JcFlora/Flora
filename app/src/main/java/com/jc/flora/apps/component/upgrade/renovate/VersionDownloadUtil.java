package com.jc.flora.apps.component.upgrade.renovate;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.jc.flora.apps.component.folder.FolderUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class VersionDownloadUtil {

	private static final int MSG_DOWNLOAD_START = 0x10002;
	private static final int MSG_DOWNLOAD_LOADING = 0x10003;
	private static final int MSG_DOWNLOAD_FINISH = 0x10004;
	private static final int MSG_DOWNLOAD_ERROR = 0x10005;
	private static final int MSG_DOWNLOAD_CANCEL = 0x10006;
	private Context mContext;
	
	private final String mApkUrl;
	private final String mFilePathName;
	private boolean mCancelUpgrade;
	private onDownloadProgressChangedListener mListener;
	private DownloadThread mDownloadThread ;
	
	public VersionDownloadUtil(Context context, String apkUrl, String filePathName, onDownloadProgressChangedListener listener){
		mContext = context ;
		mApkUrl = apkUrl;
		mListener = listener;
		mFilePathName = filePathName;
	}
	
	public void startUpgrade(){
		if(TextUtils.isEmpty(mFilePathName)){
			return;
		}
		if(!FolderUtils.exists(mFilePathName)){
			FolderUtils.createFile(mFilePathName);
		}
		mCancelUpgrade = false;
		mDownloadThread = new DownloadThread();
		mDownloadThread.start();
	}
	
	public void cancelUpgrade() {
		if(TextUtils.isEmpty(mFilePathName)){
			return;
		}
		mCancelUpgrade = true;
		FolderUtils.delete(mFilePathName);
		mHandler.removeCallbacksAndMessages(null);
	}
	
	/* 用于更新界面的Handler */
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
						switch (msg.what) {
				case MSG_DOWNLOAD_START:
					break;
				case MSG_DOWNLOAD_LOADING:
					mListener.onDownloadProgressChanged(mDownloadThread.getDownloadProgress());
					break;
				case MSG_DOWNLOAD_FINISH:
					installApk();
					break;
				case MSG_DOWNLOAD_ERROR:
//					ToastDelegate.show(mContext, "更新失败：无法连接远程地址");
					break;
				case MSG_DOWNLOAD_CANCEL:
					mCancelUpgrade = true;
					break;
				}
		}
	};

	private void installApk() {
		Utils.installApk(mContext, mFilePathName);
	}

	public interface onDownloadProgressChangedListener{
		void onDownloadProgressChanged(int downloadProgress);
	}
	
	private class DownloadThread extends Thread {
		private int nFileSize;
		private int nDownloadFileSize;
			
		private static final String CONNECT_TIMEOUT = "sun.net.client.defaultConnectTimeout";
		private static final String READ_TIMEOUT = "sun.net.client.defaultReadTimeout";
		private static final String OUT_TIME = "3000";

		public void run() {
			if(mCancelUpgrade){
				return;
			}
			mHandler.sendEmptyMessage(MSG_DOWNLOAD_START);
			try {
				sleep(10);
				downloadFile();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private int getDownloadProgress(){
			return nDownloadFileSize * 100 / nFileSize;
		}

		private void downloadFile() throws IOException {
			URL myURL = new URL(mApkUrl);
			URLConnection conn = myURL.openConnection();
			System.setProperty(CONNECT_TIMEOUT, OUT_TIME);
			System.setProperty(READ_TIMEOUT, OUT_TIME);
			try {
				conn.connect();
			} catch (Exception e) {
				mHandler.sendEmptyMessage(MSG_DOWNLOAD_ERROR);			
				return ;
			} 
			InputStream is = conn.getInputStream();
			// 根据响应获取文件大小
			nFileSize = conn.getContentLength();
			if (nFileSize <= 0)
				throw new RuntimeException("file size error");
			if (is == null)
				throw new RuntimeException("is is null");
			FileOutputStream fos = new FileOutputStream(mFilePathName);
			// 把数据存入路径+文件名
			byte buf[] = new byte[1024];
			nDownloadFileSize = 0;
			mHandler.sendEmptyMessage(MSG_DOWNLOAD_LOADING);
			do {
				// 循环读取
				int numRead = is.read(buf);
				if (numRead == -1) {
					break;
				}
				fos.write(buf, 0, numRead);
				nDownloadFileSize += numRead;
				mHandler.sendEmptyMessage(MSG_DOWNLOAD_LOADING);
			} while (!mCancelUpgrade);
			try {
				fos.close();
				is.close();
			} catch (Exception e) {				
				e.printStackTrace();
			}
			if (nDownloadFileSize == nFileSize) {
				// 通知下载完成
				mHandler.sendEmptyMessage(MSG_DOWNLOAD_FINISH);
			}
		}
	}
}
