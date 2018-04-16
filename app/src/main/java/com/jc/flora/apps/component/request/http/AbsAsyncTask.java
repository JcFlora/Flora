package com.jc.flora.apps.component.request.http;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.net.SocketException;

/**
 * 异步请求框架 ，所有的请求都继承自这个抽象类，泛型参数分别为请求参数，请求进度，请求结果
 * Created by Shijincheng on 2018/4/16.
 */

public abstract class AbsAsyncTask<Result> extends AsyncTask<Void, Integer, Result> {

    /** 异步请求数据的方法 */
    @Override
    protected Result doInBackground(Void... params) {
        // Http请求
        return parseResponse(requestHttp());
    }

    /** 请求数据之后执行的操作 */
    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        // 通过抽象方法实现当前界面刷新
        updateUIAfterGetData(result);
    }

    /** 使用HTTP向服务器发送请求的方法*/
    private String requestHttp() {
        String json = null;
        try {
            String url = getBaseUrl() + getMethodUrl();
            ContentValues params = params2ContentValues();
            if ("GET".equals(getMethodType())) {
                json = new HttpGetMethod(url, params).doGet();
            } else if ("POST".equals(getMethodType())) {
                json = new HttpPostMethod(url, params).doPost();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return json;
    }

    /** 设置BaseUrl */
    protected abstract String getBaseUrl();

    /** 设置MethodUrl */
    protected abstract String getMethodUrl();

    /** 设置MethodType */
    protected abstract String getMethodType();

    /** Http请求参数的封装方法 */
    protected abstract ContentValues params2ContentValues();

    /** 解析返回的数据 */
    protected abstract Result parseResponse(String json);

    /** 请求成功后，刷新当前界面的方法 */
    protected abstract void updateUIAfterGetData(Result result);

    protected Object json2Object(String jsonStr, Class destClass) {
        Object rstObj;
        try {
            if (!TextUtils.isEmpty(jsonStr)) {
                rstObj = new Gson().fromJson(jsonStr, destClass);
                return rstObj;
            }
        } catch (Exception e) {
            if (null != e.getMessage())
                e.printStackTrace();
            ILog.D(e.getMessage());
        }
        return null;
    }

}