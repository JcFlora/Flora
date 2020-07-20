package com.jc.flora.apps.component.hybrid.projects;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/1/16.
 */
public class JsCallAppActivity extends AppCompatActivity {

    private static final String TAG = "JsCallAppActivity";
    private static final String PC_USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11";
    private static final String TEST_URL = "file:///android_asset/h5/index.html";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Js调用App");
        WebView wvTest = new WebView(this);
        setContentView(wvTest);
        setWebViewSettings(wvTest);
        wvTest.loadUrl(TEST_URL);
    }

    @SuppressLint("SetJavaScriptEnabled")
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
        // WebView只是一个承载体，各种内容的渲染（如alert对话框）需要使用WebChromeClient去实现，
        // 所以需要set一个默认的基类WebChromeClient
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(this, "sso");
        CookieManager.getInstance().setAcceptCookie(true);
        WebSettings webSettings = webView.getSettings();
        // 是否允许访问文件，默认允许。注意，这里只是允许或禁止对文件系统的访问，
        // Assets 和 resources 文件使用file:///android_asset和file:///android_res仍是可访问的。
        webSettings.setAllowFileAccess(true);
        // 应用缓存API是否可用，默认值false, 结合setAppCachePath(String)使用
        webSettings.setAppCacheEnabled(true);
        // 重写使用缓存的方式，默认值LOAD_DEFAULT。
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 让JavaScript自动打开窗口，默认false。适用于JavaScript方法window.open()。
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置WebView是否允许执行JavaScript脚本，默认false，不允许。
        webSettings.setJavaScriptEnabled(true);
        // 设置布局，会引起WebView的重新布局（relayout）,默认值NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 是否允许WebView度超出以概览的方式载入页面，默认false。即缩小内容以适应屏幕宽度。
        // 该项设置在内容宽度超出WebView控件的宽度时生效，例如当getUseWideViewPort() 返回true时。
        webSettings.setLoadWithOverviewMode(true);
        // WebView是否支持使用屏幕上的缩放控件和手势进行缩放，默认值true。
        webSettings.setSupportZoom(false);
        // WebView是否支持HTML的“viewport”标签或者使用wide viewport。
        // 设置值为true时，布局的宽度总是与WebView控件上的设备无关像素（device-dependent pixels）宽度一致。
        // 当值为true且页面包含viewport标记，将使用标签指定的宽度。
        // 如果页面不包含标签或者标签没有提供宽度，那就使用wide viewport。
        webSettings.setUseWideViewPort(false);
        // 设置WebView的用户代理字符串。如果字符串为null或者empty，将使用系统默认值。
        // 注意从KITKAT版本开始，加载网页时改变用户代理会让WebView再次初始化加载。
        webSettings.setUserAgentString(PC_USER_AGENT);
//        webView.setInitialScale(100);
    }

    @JavascriptInterface
    public void funCallback(String username, String pwd) {
        if("zhangsan".equals(username) && "123456".equals(pwd)){
            ToastDelegate.show(this, "zhangsan，恭喜你登录成功");
        }else{
            ToastDelegate.show(this, "用户名或密码错误");
        }
    }

}
