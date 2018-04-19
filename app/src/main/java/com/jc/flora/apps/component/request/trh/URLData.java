package com.jc.flora.apps.component.request.trh;

public class URLData<T> {
    public String key;
    public long expires;
    public String netType;
    public String url;
    public Class<T> clazz;

    public URLData(String key, long expires, String netType, String url, Class<T> clazz) {
        this.key = key;
        this.expires = expires;
        this.netType = netType;
        this.url = url;
        this.clazz = clazz;
    }

}