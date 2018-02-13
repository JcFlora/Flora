package com.jc.flora.apps.component.request.volley;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * 请求管理器
 * compile 'com.android.volley:volley:1.0.0'
 * compile 'com.google.code.gson:gson:2.4'
 * Created by shijincheng on 2017/3/18.
 */
public class RequestManager {

    /** 请求队列 */
    private RequestQueue mRequestQueue;
    /** 全局上下文 */
    private Context mApplicationContext;

    /**
     * 获取Api管理器
     */
    public static RequestManager getInstance(Context ctx) {
        // 获取全局上下文
        ApiManagerHolder.sInstance.mApplicationContext = ctx.getApplicationContext();
        // 创建请求队列
        ApiManagerHolder.sInstance.mRequestQueue = ApiManagerHolder.sInstance.getRequestQueue();
        return ApiManagerHolder.sInstance;
    }

    private static class ApiManagerHolder {
        /** 静态内部类单例对象 */
        private static RequestManager sInstance = new RequestManager();
    }

    /** Api管理器 */
    private RequestManager() {
    }

    /**
     * 获取请求队列
     *
     * @return 请求队列
     */
    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // 通过Volley创建请求队列
            mRequestQueue = Volley.newRequestQueue(mApplicationContext);
        }
        return mRequestQueue;
    }

    public void createAndAddRequest(int method, String url, Response.Listener<String> listener,
                                        Response.ErrorListener errorListener) {
        // 构建Request对象
        StringRequest request = new StringRequest(method, url, listener, errorListener);
        // 添加到请求队列
        addRequestToQueue(request);
    }

    public <T> void createAndAddRequest(int method, String url, Response.Listener<T> listener,
                                        Response.ErrorListener errorListener, String logTag, Class<T> clazz) {
        // 构建Request对象
        GsonRequest<T> request = new GsonRequest<>(method, url, listener, errorListener);
        // 设置打印日志标记
        request.setLogTag(logTag);
        // 设置返回数据格式
        request.setRespClass(clazz);
        // 添加到请求队列
        addRequestToQueue(request);
    }

    /**
     * 添加请求到队列
     *
     * @param req 请求
     * @param <T> 请求返回数据格式
     */
    public <T> void addRequestToQueue(Request<T> req) {
        getRequestQueue().add(req);
        // 打印请求链接
        L.w(getClass().getSimpleName(), req.getUrl());
    }

    /**
     * Cancels all the request in the Volley queue for a given tag
     *
     * @param tag associated with the Volley requests to be cancelled
     */
    public void cancelAllRequests(String tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}

