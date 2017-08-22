package com.jc.flora.apps.ui.banner.ec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jc.flora.apps.component.request.retrofit.GzipRequestInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;

/**
 * Api基类
 * compile "com.squareup.retrofit2:retrofit:2.1.0"
 * compile "com.squareup.retrofit2:converter-gson:2.1.0"
 * compile "com.squareup.retrofit2:adapter-rxjava:2.1.0"
 * compile 'io.reactivex:rxandroid:1.2.1'
 * compile 'io.reactivex:rxjava:1.1.6'
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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static abstract class ObserverAdapter<T> implements Observer<T> {
        public void onCompleted(){
        }
        public void onError(Throwable e){
        }
    }

}
