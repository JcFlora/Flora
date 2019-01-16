package com.jc.flora.apps.component.router.delegate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.apps.component.router.projects.GetDataTestActivity;
import com.jc.flora.apps.component.router.projects.ReceiveDataTestActivity;
import com.jc.flora.apps.component.router.projects.TestH5Activity;
import com.jc.flora.launcher.LauncherActivity;
import com.jc.flora.launcher.NotFoundActivity;

/**
 * Created by Shijincheng on 2019/1/16.
 */

public class ActivityRouterDelegate {

    private static ActivityRouterDelegate sInstance = new ActivityRouterDelegate();

    public static ActivityRouterDelegate getInstance(){
        return sInstance;
    }

    private ActivityRouterDelegate(){}

    public void gotoLauncher(Context context) {
        context.startActivity(new Intent(context, LauncherActivity.class));
    }

    public void gotoNotFound(Context context) {
        context.startActivity(new Intent(context, NotFoundActivity.class));
    }

    public void gotoNotFound2(Context context) {
        Intent[] intents = new Intent[2];
        intents[0] = new Intent(context, LauncherActivity.class);
        intents[1] = new Intent(context, NotFoundActivity.class);
        context.startActivities(intents);
    }

    public void goWithData(Context context, String from) {
        Intent intent = new Intent(context, GetDataTestActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    public void goAndReceiveData(AppCompatActivity activity, int requestCode) {
        Intent intent = new Intent(activity, ReceiveDataTestActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    public void gotoH5(Context context, String url) {
        Intent intent = new Intent(context, TestH5Activity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

}
