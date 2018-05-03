package com.jc.flora.apps.component.request.retrofit.soap.ksoap2;

import java.util.Map;

/**
 * author：wangkezhi
 * date: 2017/5/22 11:51
 * email：454366460@qq.com
 * summary：ksoap请求模型
 */

public class SoapParameter {

    /**
     * 请求头
     */
    private Map<String, String> mHeaderMap;

    /**
     * 请求数据
     */
    private byte[] mRequestData;

    public SoapParameter(Map<String, String> headerMap, byte[] requestData) {
        mHeaderMap = headerMap;
        mRequestData = requestData;
    }

    public Map<String, String> getHeaderMap() {
        return mHeaderMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        mHeaderMap = headerMap;
    }

    public byte[] getRequestData() {
        return mRequestData;
    }

    public void setRequestData(byte[] requestData) {
        mRequestData = requestData;
    }

    public SoapParameter() {
    }

}
