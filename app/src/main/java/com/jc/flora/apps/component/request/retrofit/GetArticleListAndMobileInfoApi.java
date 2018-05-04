package com.jc.flora.apps.component.request.retrofit;

import com.jc.flora.apps.component.request.NetResponse;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 获取文章列表的Api
 * http://gank.io/api/search/query/listview/category/Android/count/2/page/1
 * Created by shijincheng on 2017/3/20.
 */
public class GetArticleListAndMobileInfoApi {

    public Observable<String> getBySerial(String category, int count, int pageIndex, final String mobileCode) {
        return new GetArticleListApi().getArticleList2(category, count, pageIndex).concatMap(new Function<NetResponse, Observable<String>>() {
            @Override
            public Observable<String> apply(NetResponse response) throws Exception {
                return new GetMobileInfoApi().getMobileInfo(mobileCode);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Object> getByConcat(String category, int count, int pageIndex, String mobileCode) {
        Observable<NetResponse> observable1 = new GetArticleListApi().getArticleList2(category, count, pageIndex);
        Observable<String> observable2 = new GetMobileInfoApi().getMobileInfo(mobileCode);
        return Observable.concat(observable1,observable2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ArrayList<Object>> getByZip(String category, int count, int pageIndex, final String mobileCode) {
        Observable<NetResponse> observable1 = new GetArticleListApi().getArticleList2(category, count, pageIndex);
        Observable<String> observable2 = new GetMobileInfoApi().getMobileInfo(mobileCode);
        return Observable.zip(observable1, observable2, new BiFunction<NetResponse, String, ArrayList<Object>>() {
            @Override
            public ArrayList<Object> apply(NetResponse netResponse, String s) throws Exception {
                ArrayList<Object> arrayList = new ArrayList<>();
                arrayList.add(netResponse);
                arrayList.add(s);
                return arrayList;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}