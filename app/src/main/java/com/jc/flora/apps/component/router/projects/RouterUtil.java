package com.jc.flora.apps.component.router.projects;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.launcher.LauncherActivity;
import com.jc.flora.launcher.NotFoundActivity;

/**
 * Created by shijincheng on 2017/5/19.
 */
public class RouterUtil {

    public static void gotoLauncher(Context context) {
        context.startActivity(new Intent(context, LauncherActivity.class));
    }

    public static void gotoNotFound(Context context) {
        context.startActivity(new Intent(context, NotFoundActivity.class));
    }

    public static void goWithData(Context context, String from) {
        Intent intent = new Intent(context, GetDataTestActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    public static void goAndReceiveData(AppCompatActivity activity, int requestCode) {
        Intent intent = new Intent(activity, ReceiveDataTestActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void gotoLauncher(Fragment fragment) {
        fragment.startActivity(new Intent(fragment.getActivity(), LauncherActivity.class));
    }

    public static void gotoNotFound(Fragment fragment) {
        fragment.startActivity(new Intent(fragment.getActivity(), NotFoundActivity.class));
    }

    public static void goWithData(Fragment fragment, String from) {
        Intent intent = new Intent(fragment.getActivity(), GetDataTestActivity.class);
        intent.putExtra("from", from);
        fragment.startActivity(intent);
    }

    public static void goAndReceiveData(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), ReceiveDataTestActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

}
