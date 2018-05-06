package com.jc.flora.apps.component.request.nao;

import com.google.gson.Gson;

import org.ksoap2.serialization.SoapObject;

public class URLData<T> {
    public String key;
    public long expires;
    public String netType;
    public String url;
    public Class<T> clazz;
    public String soapNameSpace;
    public String soapMethodName;

    public URLData(String key, long expires, String netType, String url, Class<T> clazz) {
        this.key = key;
        this.expires = expires;
        this.netType = netType;
        this.url = url;
        this.clazz = clazz;
    }

    public T parseResponse(String strResponse){
        return new Gson().fromJson(strResponse, clazz);
    }

    public T parseResponse(SoapObject soapObject){
        return null;
    }

    public boolean isGetRequest(){
        return "get".equals(netType);
    }

    public boolean isPostRequest(){
        return "post".equals(netType);
    }

    public boolean isSoapRequest(){
        return "soap".equals(netType);
    }

}