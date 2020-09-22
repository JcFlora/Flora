package com.jc.flora.apps.component.request.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jc.flora.apps.component.deviceinfo.DeviceUtil;
import com.jc.flora.apps.component.request.retrofit.soap.convert.SoapConverterFactory;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

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
 * implementation "com.squareup.retrofit2:retrofit:2.3.0"
 * implementation "com.squareup.retrofit2:converter-scalars:2.3.0"
 * implementation "com.squareup.retrofit2:converter-gson:2.3.0"
 * implementation "com.squareup.retrofit2:adapter-rxjava2:2.3.0"
 * implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
 * implementation 'io.reactivex.rxjava2:rxjava:2.1.1'
 * Created by shijincheng on 2017/3/20.
 */
public class BaseApi {

    private static final String HOST = "https://gank.io/api/";

    private static final String SOAP_HOST = "http://ws.webxml.com.cn/";

    private static Retrofit sRetrofit = initRetrofit();

    private static Retrofit sSoapRetrofit = initSoapRetrofit();

    private static Retrofit initRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(new GzipRequestInterceptor());
//        initSSLSocketFactory(builder);
        OkHttpClient httpClient = builder.build();
        //使用GsonBuilder创建Gson，统一日期请求格式
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        return new Retrofit.Builder()
                .baseUrl(HOST)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static Retrofit initSoapRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        initSSLSocketFactory(builder);
        OkHttpClient httpClient = builder.build();
        return new Retrofit.Builder()
                .baseUrl(SOAP_HOST)
                .client(httpClient)
                .addConverterFactory(SoapConverterFactory.create(SoapConverterFactory.STRING))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    protected Retrofit getRetrofit() {
        return sRetrofit;
    }

    protected Retrofit getSoapRetrofit() {
        return sSoapRetrofit;
    }

//    /**
//     * 4.4W以下版本，需要适配证书处理
//     * @param builder
//     */
//    private static void initSSLSocketFactory(OkHttpClient.Builder builder) {
//        if (DeviceUtil.isSystemVersionAfterKitkatWatch()) {
//            return;
//        }
//        try {
//            // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
//            X509TrustManager trustAllCert = new X509TrustManager() {
//                @Override
//                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                }
//
//                @Override
//                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                }
//
//                @Override
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return new java.security.cert.X509Certificate[]{};
//                }
//            };
//            SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
//            builder.sslSocketFactory(sslSocketFactory, trustAllCert);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static abstract class ObserverAdapter<T> implements Observer<T> {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
        }

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
        }
    }

}
