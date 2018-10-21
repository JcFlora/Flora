package com.jc.flora.apps.component.request.http;

import android.content.ContentValues;

import com.jc.flora.apps.component.request.NetResponse;

import java.text.MessageFormat;

/**
 * Created by Shijincheng on 2018/4/16.
 */

public abstract class GetArticleListRequest extends AbsAsyncTask<NetResponse>{

    private static final String HOST = "https://gank.io/api/";
    private static final String GET_ARTICLE_LIST_ACTION = "search/query/listview/category/{0}/count/{1}/page/{2}";

    private String mCategory = "Android";
    private int mCount = 2;
    private int mPageIndex = 1;

    public void sendRequest(String category, int count, int pageIndex){
        mCategory = category;
        mCount = count;
        mPageIndex = pageIndex;
        execute();
    }

    @Override
    protected String getBaseUrl() {
        return HOST;
    }

    @Override
    protected String getMethodUrl() {
        return MessageFormat.format(GET_ARTICLE_LIST_ACTION, mCategory, mCount, mPageIndex);
    }

    @Override
    protected String getMethodType() {
        return "GET";
    }

    @Override
    protected ContentValues params2ContentValues() {
        return null;
    }

    @Override
    protected NetResponse parseResponse(String response) {
        return (NetResponse) json2Object(response, NetResponse.class);
    }

}