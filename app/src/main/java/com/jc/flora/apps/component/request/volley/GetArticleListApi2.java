package com.jc.flora.apps.component.request.volley;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.jc.flora.apps.component.request.NetResponse;

import java.text.MessageFormat;

/**
 * 获取文章列表的Api
 * https://gank.io/api/search/query/listview/category/Android/count/2/page/1
 * Created by shijincheng on 2017/3/18.
 */
public class GetArticleListApi2 extends BaseApi2<NetResponse> {

    private static final String HOST = "https://gank.io/api/";
    private static final String GET_ARTICLE_LIST_ACTION = "search/query/listview/category/{0}/count/{1}/page/{2}";

    private String mCategory = "Android";
    private int mCount = 2;
    private int mPageIndex = 1;

    public GetArticleListApi2(AppCompatActivity activity, Response.Listener<NetResponse> l) {
        super(activity, l);
    }

    public GetArticleListApi2(AppCompatActivity activity, Response.Listener<NetResponse> l, Response.ErrorListener el) {
        super(activity, l, el);
    }

    public void sendRequest(String category, int count, int pageIndex){
        mCategory = category;
        mCount = count;
        mPageIndex = pageIndex;
        sendRequest();
    }

    @Override
    protected String getHost() {
        return HOST;
    }

    @Override
    protected String getAction() {
        return MessageFormat.format(GET_ARTICLE_LIST_ACTION, mCategory, mCount, mPageIndex);
    }

//    @Override
//    protected Class<NetResponse> getRespClass() {
//        return NetResponse.class;
//    }
}
