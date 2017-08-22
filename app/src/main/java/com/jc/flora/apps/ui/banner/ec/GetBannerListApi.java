package com.jc.flora.apps.ui.banner.ec;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 获取轮播广告的Api
 * http://apiv4.yangkeduo.com/subjects?pdduid=
 * Created by shijincheng on 2017/8/22.
 */
public class GetBannerListApi extends BaseApi{

    private static final String GET_BANNER_LIST_ACTION = "subjects";

    private interface GetBannerListService {
        @GET(GET_BANNER_LIST_ACTION)
        Observable<ArrayList<BannerInfo>> getBannerList(@Query("pdduid") String id);
    }

    public Observable<ArrayList<BannerInfo>> getBannerList(String id) {
        return getRetrofit().create(GetBannerListService.class).getBannerList(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
