package com.jc.flora.apps.ui.banner.ec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jc.flora.apps.component.request.retrofit.GzipRequestInterceptor;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Api基类
 * compile "com.squareup.retrofit2:retrofit:2.3.0"
 * compile "com.squareup.retrofit2:converter-gson:2.3.0"
 * compile "com.squareup.retrofit2:adapter-rxjava2:2.3.0"
 * compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
 * compile 'io.reactivex.rxjava2:rxjava:2.1.1'
 * Created by shijincheng on 2017/8/22.
 */
public class BaseApi {

    private static final String HOST = "http://apiv4.yangkeduo.com/";

    protected Retrofit getRetrofit(){
        return sRetrofit;
    }

    private static Retrofit sRetrofit = initRetrofit();

    private static Retrofit initRetrofit() {
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new GzipRequestInterceptor()).build();
        //使用GsonBuilder创建Gson，统一日期请求格式
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        return new Retrofit.Builder()
                .baseUrl(HOST)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static abstract class ObserverAdapter<T> implements Observer<T> {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
        }
        @Override
        public void onComplete(){
        }
        @Override
        public void onError(Throwable e){
        }
    }

}
