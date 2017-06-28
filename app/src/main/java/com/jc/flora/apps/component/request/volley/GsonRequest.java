package com.jc.flora.apps.component.request.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Gson请求
 * compile 'com.android.volley:volley:1.0.0'
 * compile 'com.google.code.gson:gson:2.4'
 * Created by shijincheng on 2017/3/18.
 */
public class GsonRequest<Resp> extends Request<Resp> {

    /** 返回成功的响应 */
    private final Response.Listener<Resp> mListener;
    /** 返回数据格式 */
    private Class<Resp> mRespClass;
    /** 请求参数集合 */
    private Map<String, String> mParams;
    /** 打印日志标记 */
    private String mLogTag = "baseApi";

    /**
     * Gson请求
     *
     * @param method        请求类型：get OR post
     * @param url           请求url
     * @param listener      返回成功的响应
     * @param errorListener 返回失败的响应
     */
    public GsonRequest(int method, String url, Response.Listener<Resp> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    /**
     * 设置打印日志标记
     *
     * @param tag 打印日志标记
     */
    public void setLogTag(String tag) {
        mLogTag = tag;
    }

    /**
     * 设置返回数据格式
     *
     * @param clazz 返回数据格式
     */
    public void setRespClass(Class<Resp> clazz) {
        mRespClass = clazz;
    }

    /**
     * 添加请求入参
     *
     * @param key   入参的键
     * @param value 入参的值
     */
    public void addParames(String key, String value) {
        if (mParams == null){
            mParams = new HashMap<>();
        }
        mParams.put(key, value);
    }

    @Override
    protected Map<String, String> getParams() {
        return mParams;
    }

    /* (non-Javadoc)
     * @see com.android.volley.Request#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<>();
        }
//        SysUtil.setAppVersion(YouDiApplication.getInstance());
        // 设备类型（1：android版； 2：IOS）
        headers.put("type", "1");
        // 设备具体的相关信息
        headers.put("desc", android.os.Build.MODEL);
//        // 应用的版本号
//        headers.put("ver", DeviceUtil.getAppVersionName(YouDiApplication.getInstance()));
//        // 请求时的时间毫秒值
//        headers.put("stime", Calendar.getInstance().getTimeInMillis() + "");
//        // 添加Cookie
//        YouDiApplication.getInstance().addSessionCookie(headers);
        return headers;
    }

    @Override
    protected Response<Resp> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
//            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            // 写死"UTF-8"，防止因为服务器头返回的编码和其本身内容的编码不一致，导致解析成乱码
            parsed = new String(response.data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        // 打印返回json
        L.json(mLogTag, parsed);
        // 使用Gson解析返回的Json
        Resp resp = new Gson().fromJson(parsed, mRespClass);
        // 返回响应数据
        return Response.success(resp, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(Resp response) {
        // 数据返回后会回调响应方法
        mListener.onResponse(response);
    }
}
