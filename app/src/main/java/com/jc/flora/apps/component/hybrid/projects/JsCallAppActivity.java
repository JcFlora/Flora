package com.jc.flora.apps.component.hybrid.projects;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by shijincheng on 2017/1/16.
 */
public class JsCallAppActivity extends AppCompatActivity {

    private static final String TAG = "JsCallAppActivity";
    private static final String PC_USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11";
    private static final String TEST_URL = "http://157.0.31.8:9001/wechat.html";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Js调用App");
        WebView wvTest = new WebView(this);
        setContentView(wvTest);
        setWebViewSettings(wvTest);
        wvTest.loadUrl(TEST_URL);
    }

    private void setWebViewSettings(WebView webView) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, "url : " + url);
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        //webview只是一个承载体，各种内容的渲染（如alert对话框）需要使用webviewChromClient去实现，
        //所以需要set一个默认的基类WebChromeClient
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(this, "Imiland");
        CookieManager.getInstance().setAcceptCookie(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setUserAgentString(PC_USER_AGENT);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(false);
        webSettings.setSupportZoom(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setInitialScale(80);
    }

    @JavascriptInterface
    public String getDeviceId() {
        return "12345";
    }

}
