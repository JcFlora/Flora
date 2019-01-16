package com.jc.flora.apps.component.router.projects;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shijincheng on 2019/1/12.
 */
public class UrlRouter1Activity extends AppCompatActivity {

    private static final String[] URLS =
            {"https://m.baidu.com/index.html?@target=launcher",
            "https://m.baidu.com/index.html?@target=notFound",
            "https://m.baidu.com/index.html?@target=notFound2",
            "https://m.baidu.com/index.html?@target=withData&from=UrlRouter1Activity",
            "https://m.baidu.com/index.html",
            "https://m.baidu.com/index.html?@target=unknown"};

    private static final String TARGET_KEY = "@target";
    private static final String TARGET_LAUNCHER = "launcher";
    private static final String TARGET_NOT_FOUND = "notFound";
    private static final String TARGET_NOT_FOUND2 = "notFound2";
    private static final String TARGET_WITH_DATA = "withData";

    private TextView mTvUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("拦截Url的路由跳转");
        setContentView(R.layout.activity_router_url);
        initViews();
    }

    private void initViews(){
        mTvUrl = findViewById(R.id.tv_url);
        mTvUrl.setText(URLS[0]);
    }

    public void configUrl(View v){
        showUrlListDialog();
    }

    public void routePage(View v){
        routePage(mTvUrl.getText().toString().trim());
    }

    private void showUrlListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("配置Url");
        builder.setItems(URLS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mTvUrl.setText(URLS[which]);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    private void routePage(String url){
        Map<String, String> params = urlSplit(url);
        if(!params.containsKey(TARGET_KEY)){
            RouterUtil.gotoH5(this, url);
            return;
        }
        String target = params.get(TARGET_KEY);
        switch (target) {
            case TARGET_LAUNCHER:
                RouterUtil.gotoLauncher(this);
                break;
            case TARGET_NOT_FOUND:
                RouterUtil.gotoNotFound(this);
                break;
            case TARGET_NOT_FOUND2:
                RouterUtil.gotoNotFound2(this);
                break;
            case TARGET_WITH_DATA:
                String data = params.containsKey("from") ? params.get("from") : "";
                RouterUtil.goWithData(this, data);
                break;
            default:
                RouterUtil.gotoH5(this, url);
                break;
        }

    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    private static Map<String, String> urlSplit(String URL) {
        Map<String, String> mapRequest = new HashMap<>();
        String[] arrSplit;
        String strUrlParam = truncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual;
            arrSplitEqual = strSplit.split("[=]");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else if (!TextUtils.isEmpty(arrSplitEqual[0])) {
                //只有参数没有值，不加入
                mapRequest.put(arrSplitEqual[0], "");
            }
        }
        return mapRequest;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String truncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit;
        strURL = strURL.trim();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                for (int i = 1; i < arrSplit.length; i++) {
                    strAllParam = arrSplit[i];
                }
            }
        }
        return strAllParam;
    }

}
