package com.jc.flora.apps.component.request.nao;

import com.google.gson.Gson;
import com.jc.flora.apps.component.request.NetResponse;

import java.text.MessageFormat;

public class GetArticleListApi {

    private static final String HOST = "http://gank.io/api/";
    private static final String GET_ARTICLE_LIST_ACTION = "search/query/listview/category/{0}/count/{1}/page/{2}";
    private static boolean MOCK = false;

    private RequestCallback<NetResponse> mCallBack;
    private URLData<NetResponse> mUrlData = new URLData<>("getArticleList", 0, "get", null, NetResponse.class);

    public GetArticleListApi(RequestCallback<NetResponse> callBack) {
        mCallBack = callBack;
    }

    public void sendRequest(String category, int count, int pageIndex) {
        sendRequest(category,count,pageIndex,true);
    }

    public void sendRequest(String category, int count, int pageIndex, boolean useCache) {
        if(MOCK){
            doMock();
        }else {
            if(!useCache){
                mUrlData.expires = 0;
            }
            mUrlData.url = HOST + MessageFormat.format(GET_ARTICLE_LIST_ACTION, category, count, pageIndex);
            RequestManager.getInstance().createAndAddRequest(mUrlData, mCallBack);
        }
    }

    private void doMock() {
//        NetResponse response = new NetResponse();
//        response.count = 1;
//        NetResponse.Result result = new NetResponse.Result();
//        result.desc = "mock描述";
//        result.ganhuoId = "mockID";
//        result.publishedAt = "mock日期";
//        result.type = "Android";
//        result.url = "http://www.baidu.com/";
//        result.who = "mock作者";
//        response.results.add(result);

        String json = "{\"count\": 1,\"error\": false,\"results\": [{\"desc\": \"Android Data Binding Adapter for ListView and RecyclerView.\",\"ganhuo_id\": \"56cc6d23421aa95caa707ac9\",\"publishedAt\": \"2015-07-21T04:10:11.904000\",\"readability\": \"\",\"type\": \"Android\",\"url\": \"https://github.com/evant/binding-collection-adapter\",\"who\": \"mthli\"}]}";
        NetResponse response = new Gson().fromJson(json,NetResponse.class);
        if (mCallBack != null){
            mCallBack.onSuccess(response);
        }
    }

}