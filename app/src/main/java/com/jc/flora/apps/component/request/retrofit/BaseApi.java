package com.jc.flora.apps.component.request.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Api基类
 * compile "com.squareup.retrofit2:retrofit:2.3.0"
 * compile "com.squareup.retrofit2:converter-gson:2.3.0"
 * compile "com.squareup.retrofit2:adapter-rxjava2:2.3.0"
 * compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
 * compile 'io.reactivex.rxjava2:rxjava:2.1.1'
 * Created by shijincheng on 2017/3/20.
 */
public class BaseApi {

    private static final String HOST = "http://gank.io/api/";

    protected Retrofit getStringRetrofit(){
        return sStringRetrofit;
    }

    protected Retrofit getGsonRetrofit(){
        return sGsonRetrofit;
    }

    private static Retrofit sStringRetrofit = initStringRetrofit();

    private static Retrofit sGsonRetrofit = initGsonRetrofit();

    private static Retrofit initStringRetrofit() {
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new GzipRequestInterceptor()).build();
        return new Retrofit.Builder()
                .baseUrl(HOST)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static Retrofit initGsonRetrofit() {
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
