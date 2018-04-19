package com.jc.flora.apps.component.request.trh;

import android.os.Handler;
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

	HttpRequest(URLData<Result> data, List<RequestParameter> params, RequestCallback<Result> callBack) {
		mUrlData = data;
		mParams = params;
		mRequestCallback = callBack;
		mHandler = new Handler();
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
			if (mUrlData.netType.equals("get")) {
				strResponse = doGet();
			} else if (mUrlData.netType.equals("post")) {
				strResponse = doPost();
			} else {
				return;
			}

			if (TextUtils.isEmpty(strResponse)) {
				handleNetworkError("网络异常");
			} else if (mRequestCallback != null) {
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
		} catch (final IllegalArgumentException e) {
			handleNetworkError("网络异常");
		} catch (final IOException e) {
			handleNetworkError("网络异常");
		}
	}

	private String doGet() throws SocketException {
		String url = mUrlData.url.trim().toLowerCase();
		ILog.D("preUrl = " + url);
		if (TextUtils.isEmpty(url) || !url.startsWith("http://")) {
			return null;
		}

		if (mParams != null && mParams.size() > 0) {
			String para = "";
			for (RequestParameter param : mParams) {
				String key = param.name;
				String val = param.value;
				para += "&" + key + "=" + val;
			}
			url += "?" + para.substring(1, para.length());
			ILog.D("url = " + url);
		}
		try {
			mConnection = (HttpURLConnection) new URL(url).openConnection();
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
			e.printStackTrace();
		} finally {
			if (mConnection != null) {
				mConnection.disconnect();
			}
		}
		return null;
	}

	private String doPost() throws SocketException {
		String url = mUrlData.url.trim().toLowerCase();
		ILog.D("preUrl = " + url);
		if (TextUtils.isEmpty(url) || !url.startsWith("http://")) {
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
			mConnection = (HttpURLConnection) new URL(url).openConnection();
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
			e.printStackTrace();
		} finally {
			if (mConnection != null) {
				// 关闭连接
				mConnection.disconnect();
			}
		}
		return null;
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