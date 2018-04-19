package com.jc.flora.apps.component.request.trh;

import com.jc.flora.apps.component.request.NetResponse;

import java.text.MessageFormat;

public class GetArticleListApi {

    private static final String HOST = "http://gank.io/api/";
    private static final String GET_ARTICLE_LIST_ACTION = "search/query/listview/category/{0}/count/{1}/page/{2}";

    private RequestCallback<NetResponse> mCallBack;
    private URLData<NetResponse> mUrlData = new URLData<>("getArticleList", 0, "get", null, NetResponse.class);

    public GetArticleListApi(RequestCallback<NetResponse> callBack) {
        mCallBack = callBack;
    }

    public void sendRequest(String category, int count, int pageIndex) {
        mUrlData.url = HOST + MessageFormat.format(GET_ARTICLE_LIST_ACTION, category, count, pageIndex);
        RequestManager.getInstance().createAndAddRequest(mUrlData, mCallBack);
    }

}