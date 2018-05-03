package com.jc.flora.apps.component.request.retrofit.soap.convert;

import android.support.annotation.IntDef;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * author：wangkezhi
 * date: 2017/5/19 11:17
 * email：454366460@qq.com
 * summary：自定义转换器
 */

public class SoapConverterFactory extends Converter.Factory {

    public static final int STRING = 1;
    public static final int JSON = 2;
    public static final int XML = 3;

    private @ResponseType int mResponseType = 1;

    public static SoapConverterFactory create(@ResponseType int responseType) {
        return new SoapConverterFactory(responseType);
    }

    private SoapConverterFactory(@ResponseType int responseType){
        mResponseType = responseType;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        switch (mResponseType) {
            case STRING:
                return new Soap2StringResponseBodyConverter();
            case JSON:
                return new Soap2JsonResponseBodyConverter<Type>(type);
            case XML:
                return new Soap2XmlResponseBodyConverter<Type>(type);
            default:
                return new Soap2StringResponseBodyConverter();
        }
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new SoapRequestBodyConverter<Type>();
    }

    @IntDef({STRING, JSON, XML})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ResponseType {
    }

}
