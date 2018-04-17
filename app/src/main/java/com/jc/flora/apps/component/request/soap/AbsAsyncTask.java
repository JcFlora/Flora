package com.jc.flora.apps.component.request.soap;

import android.content.ContentValues;
import android.os.AsyncTask;

import org.ksoap2.serialization.SoapObject;

/**
 * Soap异步请求框架 ，所有的Soap请求都继承自这个抽象类，泛型参数为请求结果
 * compile files('libs/ksoap2-android-assembly-3.1.1-jar-with-dependencies.jar')
 * Created by Shijincheng on 2018/4/17.
 */

public abstract class AbsAsyncTask<Result> extends AsyncTask<Void, Integer, Result> {

    /** 异步请求数据的方法 */
    @Override
    protected Result doInBackground(Void... params) {
        // Soap请求
        return parseResponse(requestSoap());
    }

    /** 请求数据之后执行的操作 */
    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        // 通过抽象方法实现当前界面刷新
        updateUIAfterGetData(result);
    }

    /** 使用HTTP向服务器发送请求的方法*/
    private SoapObject requestSoap() {
        return new SoapMethod(getUrl(), getNameSpace(), getMethodName(), params2ContentValues()).doSoap();
    }

    /** 设置NameSpace */
    protected abstract String getNameSpace();

    /** 设置Url */
    protected abstract String getUrl();

    /** 设置MethodName */
    protected abstract String getMethodName();

    /** Http请求参数的封装方法 */
    protected abstract ContentValues params2ContentValues();

    /** 解析返回的数据 */
    protected abstract Result parseResponse(SoapObject soapObject);

    /** 请求成功后，刷新当前界面的方法 */
    protected abstract void updateUIAfterGetData(Result result);

}