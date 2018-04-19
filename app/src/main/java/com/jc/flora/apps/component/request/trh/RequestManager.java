package com.jc.flora.apps.component.request.trh;

import java.util.LinkedList;
import java.util.List;

public class RequestManager {

	/** 异步请求列表 */
	private LinkedList<HttpRequest> mRequestList = new LinkedList<>();

	/**
	 * 获取请求管理器
	 */
	public static RequestManager getInstance() {
		return RequestManager.RequestManagerHolder.sInstance;
	}

	private static class RequestManagerHolder {
		/** 静态内部类单例对象 */
		private static RequestManager sInstance = new RequestManager();
	}

	private RequestManager() {
	}

	/**
	 * 无参数调用
	 */
	public <T> void createAndAddRequest(URLData<T> urlData, RequestCallback<T> requestCallback) {
		createAndAddRequest(urlData, null, requestCallback);
	}

	/**
	 * 有参数调用
	 */
	public <T> void createAndAddRequest(URLData<T> urlData, List<RequestParameter> params,
			RequestCallback<T> requestCallback) {
		HttpRequest<T> request = new HttpRequest<>(urlData, params, requestCallback);
		addRequest(request);
	}

	/**
	 * 添加Request到列表
	 */
	public void addRequest(HttpRequest request) {
		mRequestList.add(request);
		DefaultThreadPool.getInstance().execute(request);
	}

	/**
	 * 取消所有网络请求
	 */
	public void cancelAllRequests() {
		if ((mRequestList != null) && (mRequestList.size() > 0)) {
			for (final HttpRequest request : mRequestList) {
				request.abort();
			}
			mRequestList.clear();
			DefaultThreadPool.getInstance().removeAllTask();
		}
	}

}