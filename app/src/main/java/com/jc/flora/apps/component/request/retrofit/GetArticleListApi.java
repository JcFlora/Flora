package com.jc.flora.apps.component.request.retrofit;

import com.jc.flora.apps.component.request.NetResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 获取文章列表的Api
 * http://gank.io/api/search/query/listview/category/Android/count/2/page/1
 * Created by shijincheng on 2017/3/20.
 */
public class GetArticleListApi extends BaseApi {

    private static final String GET_ARTICLE_LIST_ACTION = "search/query/listview/category/{category}/count/{count}/page/{pageIndex}";

    private interface GetArticleListService {
        @GET(GET_ARTICLE_LIST_ACTION)
        Observable<String> getArticleList(@Path("category") String category,
                                                @Path("count") int count,
                                                @Path("pageIndex") int pageIndex);
        @GET(GET_ARTICLE_LIST_ACTION)
        Observable<NetResponse> getArticleList2(@Path("category") String category,
                                               @Path("count") int count,
                                               @Path("pageIndex") int pageIndex);
    }

    public Observable<String> getArticleList(String category, int count, int pageIndex) {
        return getStringRetrofit().create(GetArticleListService.class).getArticleList(category, count, pageIndex)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<NetResponse> getArticleList2(String category, int count, int pageIndex) {
        return getGsonRetrofit().create(GetArticleListService.class).getArticleList2(category, count, pageIndex)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}