package com.jc.flora.apps.component.request.retrofit;

import com.jc.flora.apps.component.request.volley.L;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.GzipSink;
import okio.Okio;

/**
 * Created by shijincheng on 2017/3/20.
 */
public class GzipRequestInterceptor implements Interceptor {
    private static final String TAG = "request";
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String requestUrl = originalRequest.url().toString();
        Response response;
        if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
            response = chain.proceed(originalRequest);
        }else{
            Request compressedRequest = originalRequest.newBuilder()
                    .header("Content-Encoding", "gzip")
                    .method(originalRequest.method(), forceContentLength(gzip(originalRequest.body())))
                    .build();
            response = chain.proceed(compressedRequest);
        }

        try {
            BufferedSource source = response.body().source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            L.d(TAG, new StringBuffer("request:\n").append(requestUrl).append("\n")
                    .append("code:").append(response.code()).append("\n"));
            L.json(TAG, buffer.clone().readString(UTF8));
        }catch (Exception e){
            L.d(TAG, "log request failed: url is " + requestUrl);
        }
        return response;
    }

    private RequestBody forceContentLength(final RequestBody requestBody) throws IOException {
        final Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return requestBody.contentType();
            }

            @Override
            public long contentLength() {
                return buffer.size();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(buffer.snapshot());
            }
        };
    }

    private RequestBody gzip(final RequestBody body) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return body.contentType();
            }

            @Override
            public long contentLength() {
                return -1;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                body.writeTo(gzipSink);
                gzipSink.close();
            }
        };
    }
}