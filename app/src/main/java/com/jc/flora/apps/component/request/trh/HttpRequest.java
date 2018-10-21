package com.jc.flora.apps.component.request.trh;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jc.flora.apps.component.request.http.ILog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.List;

public class HttpRequest<Result> implements Runnable {

	private URLData<Result> mUrlData;
	private List<RequestParameter> mParams;
	private RequestCallback<Result> mRequestCallback;
	private HttpURLConnection mConnection;
	private Handler mHandler;
	private String mUrl;

	HttpRequest(URLData<Result> data, List<RequestParameter> params, RequestCallback<Result> callBack) {
		mUrlData = data;
		mParams = params;
		mRequestCallback = callBack;
		mHandler = new Handler(Looper.getMainLooper());
	}

	void abort(){
		if(mConnection != null){
			mConnection.disconnect();
		}
	}

	@Override
	public void run() {
		try {
			String strResponse;
			if (mUrlData.isGetRequest()) {
				strResponse = doGet();
			} else if (mUrlData.isPostRequest()) {
				strResponse = doPost();
			} else {
				return;
			}

			if (TextUtils.isEmpty(strResponse)) {
				handleNetworkError("网络异常");
			} else if (mRequestCallback != null) {
				// 把成功获取到的数据记录到缓存
				if (mUrlData.isGetRequest() && mUrlData.expires > 0) {
					CacheManager.getInstance().putFileCache(mUrl, strResponse, mUrlData.expires);
				}
				// 解析返回数据
				final Result result = new Gson().fromJson(strResponse, mUrlData.clazz);
				// 设置回调函数
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						mRequestCallback.onSuccess(result);
					}
				});
			}
		} catch (IllegalArgumentException e) {
			handleNetworkError("网络异常");
		}
	}

	private String doGet() {
		mUrl = mUrlData.url.trim().toLowerCase();
		ILog.D("preUrl = " + mUrl);
		if (TextUtils.isEmpty(mUrl) || !(mUrl.startsWith("http://") || mUrl.startsWith("https://"))) {
			return null;
		}

		// 这里要对key进行排序
		if (mUrlData.expires > 0) {
			sortKeys();
		}

		if (mParams != null && mParams.size() > 0) {
			String para = "";
			for (RequestParameter param : mParams) {
				String key = param.name;
				String val = param.value;
				para += "&" + key + "=" + val;
			}
			mUrl += "?" + para.substring(1, para.length());
			ILog.D("url = " + mUrl);
		}

		// 检查缓存
		if (mUrlData.expires > 0) {
			String content = CacheManager.getInstance().getFileCache(mUrl);
			if (content != null) {
				return content;
			}
		}

		try {
			mConnection = (HttpURLConnection) new URL(mUrl).openConnection();
			mConnection.setRequestMethod("GET");
			mConnection.setReadTimeout(5000);
			mConnection.setConnectTimeout(10000);
			int responseCode = mConnection.getResponseCode();
			if(mConnection.getResponseCode() == 200){
				return inputStreamToString(mConnection.getInputStream());
			} else {
				ILog.D("response status is " + responseCode);
			}
		} catch (Exception e) {
			handleNetworkError("网络异常");
		} finally {
			if (mConnection != null) {
				mConnection.disconnect();
			}
		}
		return null;
	}

	private String doPost() {
		mUrl = mUrlData.url.trim().toLowerCase();
		ILog.D("preUrl = " + mUrl);
		if (TextUtils.isEmpty(mUrl) || !(mUrl.startsWith("http://") || mUrl.startsWith("https://"))) {
			return null;
		}

		// post请求的参数
		String data = "";
		if (mParams != null && mParams.size() > 0) {
			String para = "";
			for (RequestParameter param  : mParams) {
				String key = param.name;
				String val = param.value;
				para += "&" + key + "=" + val;
			}
			data = para.substring(1, para.length());
		}

		try {
			// 调用URL的openConnection()方法,获取HttpURLConnection对象
			mConnection = (HttpURLConnection) new URL(mUrl).openConnection();
			// 设置请求方法为post
			mConnection.setRequestMethod("POST");
			// 设置读取超时为5秒
			mConnection.setReadTimeout(5000);
			// 设置连接网络超时为10秒
			mConnection.setConnectTimeout(10000);
			// 设置此方法,允许向服务器输出内容
			mConnection.setDoOutput(true);
			// 获得一个输出流,向服务器写数据,默认情况下,系统不允许向服务器输出内容
			OutputStream out = mConnection.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			// 调用此方法就不必再使用conn.connect()方法
			int responseCode = mConnection.getResponseCode();
			if (responseCode == 200) {
				return inputStreamToString(mConnection.getInputStream());
			} else {
				ILog.D("response status is " + responseCode);
			}
		} catch (Exception e) {
			handleNetworkError("网络异常");
		} finally {
			if (mConnection != null) {
				// 关闭连接
				mConnection.disconnect();
			}
		}
		return null;
	}

	private void sortKeys() {
		for (int i = 1; i < mParams.size(); i++) {
			for (int j = i; j > 0; j--) {
				RequestParameter p1 = mParams.get(j - 1);
				RequestParameter p2 = mParams.get(j);
				if (compare(p1.name, p2.name)) {
					// 交互p1和p2这两个对象，写的超级恶心
					String name = p1.name;
					String value = p1.name;
					p1.name=p2.name;
					p1.value=p2.value;
					p2.name=name;
					p2.value=value;
				}
			}
		}
	}

	// 返回true说明str1大，返回false说明str2大
	private boolean compare(String str1, String str2) {
		String uppStr1 = str1.toUpperCase();
		String uppStr2 = str2.toUpperCase();

		boolean str1IsLonger = true;
		int minLen = 0;

		if (str1.length() < str2.length()) {
			minLen = str1.length();
			str1IsLonger = false;
		} else {
			minLen = str2.length();
			str1IsLonger = true;
		}

		for (int index = 0; index < minLen; index++) {
			char ch1 = uppStr1.charAt(index);
			char ch2 = uppStr2.charAt(index);
			if (ch1 != ch2) {
				if (ch1 > ch2) {
					return true; // str1大
				} else {
					return false; // str2大
				}
			}
		}

		return str1IsLonger;
	}

	private void handleNetworkError(final String errorMsg) {
		if(mRequestCallback == null){
			return;
		}
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				HttpRequest.this.mRequestCallback.onFail(errorMsg);
			}
		});
	}

	private String inputStreamToString(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int line;
		while ((line = is.read()) != -1) {
			baos.write(line);
		}
		return baos.toString();
	}

}