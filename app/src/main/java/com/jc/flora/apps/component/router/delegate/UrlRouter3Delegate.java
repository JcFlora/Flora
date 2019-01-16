package com.jc.flora.apps.component.router.delegate;

import android.content.Context;

import com.jc.flora.apps.component.router.projects.GetDataTestActivity;
import com.jc.flora.apps.component.router.projects.TestH5Activity;
import com.jc.flora.launcher.LauncherActivity;
import com.jc.flora.launcher.NotFoundActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shijincheng on 2019/1/14.
 */

public class UrlRouter3Delegate {

    private static final String TARGET_KEY = "@target";
    private static final String TARGET_DEFAULT= "default";
    private static final String TARGET_LAUNCHER = "launcher";
    private static final String TARGET_NOT_FOUND = "notFound";
    private static final String TARGET_NOT_FOUND2 = "notFound2";
    private static final String TARGET_WITH_DATA = "withData";

    private static final HashMap<String, UrlRouter> REGISTER_URL_ROUTER_MAP = new HashMap<>();
    private static UrlRouter3Delegate sInstance = new UrlRouter3Delegate();

    public static UrlRouter3Delegate getInstance(){
        return sInstance;
    }

    private UrlRouter3Delegate(){}

    public static void init(){
        // 注册默认页面的路由
        REGISTER_URL_ROUTER_MAP.put(TARGET_DEFAULT, UrlRouter.newInstance(TestH5Activity.class));
        // 注册主页面的路由
        REGISTER_URL_ROUTER_MAP.put(TARGET_LAUNCHER, UrlRouter.newInstance(LauncherActivity.class));
        // 注册空页面的路由
        REGISTER_URL_ROUTER_MAP.put(TARGET_NOT_FOUND, UrlRouter.newInstance(NotFoundActivity.class));
        // 注册空页面2的路由
        REGISTER_URL_ROUTER_MAP.put(TARGET_NOT_FOUND2, UrlRouter.newInstance(LauncherActivity.class, NotFoundActivity.class));
        // 注册带数据页面的路由
        REGISTER_URL_ROUTER_MAP.put(TARGET_WITH_DATA, UrlRouter.newInstance(GetDataTestActivity.class));
    }

    public void routePage(Context context, String url){
        Map<String, String> params = UrlParamsUtil.urlSplit(url);
        if(!params.containsKey(TARGET_KEY)){
            routeDefault(context, url);
            return;
        }
        String target = params.get(TARGET_KEY);
        if(!REGISTER_URL_ROUTER_MAP.containsKey(target)){
            routeDefault(context, url);
            return;
        }
        UrlRouter urlRouter = REGISTER_URL_ROUTER_MAP.get(target);
        urlRouter.route(context, url);
    }

    private void routeDefault(Context context, String url){
        if(REGISTER_URL_ROUTER_MAP.containsKey(TARGET_DEFAULT)){
            REGISTER_URL_ROUTER_MAP.get(TARGET_DEFAULT).route(context, url);
        }
    }

}
