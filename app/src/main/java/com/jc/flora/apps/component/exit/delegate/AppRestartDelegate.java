package com.jc.flora.apps.component.exit.delegate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.jc.flora.apps.scene.login.projects.Login1Activity;

import java.util.LinkedList;

/**
 * Created by Shijincheng on 2018/4/25.
 */

public class AppRestartDelegate {

    public static void push(Activity activity){
        ACTIVITY_STACK.push(activity);
    }

    public static void pop(){
        ACTIVITY_STACK.pop();
    }

    public static void restartApp(Context context){
        for (Activity activity : ACTIVITY_STACK) {
            activity.finish();
        }
        context.startActivity(new Intent(context.getApplicationContext(), Login1Activity.class));
    }

    private static final LinkedList<Activity> ACTIVITY_STACK = new LinkedList<>();

}