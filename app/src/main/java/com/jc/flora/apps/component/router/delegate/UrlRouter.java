package com.jc.flora.apps.component.router.delegate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

/**
 * 抽取出来的Router对象
 * Created by Shijincheng on 2019/1/14.
 */

public class UrlRouter {

    private Class<? extends Activity>[] mActivities;

    @SafeVarargs
    public static UrlRouter newInstance(@NotNull Class<? extends Activity>... activities){
        return new UrlRouter(activities);
    }

    @SafeVarargs
    private UrlRouter(@NotNull Class<? extends Activity>... activities) {
        mActivities = activities;
    }

    public void route(Context context, String url){
        if(mActivities == null || mActivities.length == 0){
            return;
        }
        if(mActivities.length == 1){
            Intent intent = new Intent(context, mActivities[0]);
            if(!TextUtils.isEmpty(url)){
                Bundle bundle = UrlParamsUtil.getParamsFromUrl(url);
                intent.putExtras(bundle);
            }
            context.startActivity(intent);
            return;
        }
        Intent[] intents = new Intent[mActivities.length];
        for (int i = 0; i < intents.length; i++) {
            intents[i] = new Intent(context, mActivities[i]);
            if(!TextUtils.isEmpty(url)){
                Bundle bundle = UrlParamsUtil.getParamsFromUrl(url);
                intents[i].putExtras(bundle);
            }
        }
        context.startActivities(intents);
    }

}
