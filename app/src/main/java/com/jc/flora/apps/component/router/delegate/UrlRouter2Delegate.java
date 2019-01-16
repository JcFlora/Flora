package com.jc.flora.apps.component.router.delegate;

import android.content.Context;
import android.text.TextUtils;

import com.jc.flora.apps.component.router.projects.RouterUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shijincheng on 2019/1/14.
 */

public class UrlRouter2Delegate {

    private static final String TARGET_KEY = "@target";
    private static final String TARGET_LAUNCHER = "launcher";
    private static final String TARGET_NOT_FOUND = "notFound";
    private static final String TARGET_NOT_FOUND2 = "notFound2";
    private static final String TARGET_WITH_DATA = "withData";

    public static void routePage(Context context, String url){
        Map<String, String> params = urlSplit(url);
        if(!params.containsKey(TARGET_KEY)){
            RouterUtil.gotoH5(context, url);
            return;
        }
        String target = params.get(TARGET_KEY);
        switch (target) {
            case TARGET_LAUNCHER:
                RouterUtil.gotoLauncher(context);
                break;
            case TARGET_NOT_FOUND:
                RouterUtil.gotoNotFound(context);
                break;
            case TARGET_NOT_FOUND2:
                RouterUtil.gotoNotFound2(context);
                break;
            case TARGET_WITH_DATA:
                String data = params.containsKey("from") ? params.get("from") : "";
                RouterUtil.goWithData(context, data);
                break;
            default:
                RouterUtil.gotoH5(context, url);
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
