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

    public static void gotoNotFound2(Context context) {
        Intent[] intents = new Intent[2];
        intents[0] = new Intent(context, LauncherActivity.class);
        intents[1] = new Intent(context, NotFoundActivity.class);
        context.startActivities(intents);
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

    public static void gotoH5(Context context, String url) {
        Intent intent = new Intent(context, TestH5Activity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
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

    public static void setRootFragmentForActivity(AppCompatActivity activity, Fragment fragment, int containerViewId){
        if(activity != null){
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(containerViewId, fragment, "rootFragment")
                    .commitAllowingStateLoss();
        }
    }

    public static void gotoNotFoundFragment(Fragment fragment, int containerViewId) {
        NotFoundFragment fg = new NotFoundFragment();
        fragment.getFragmentManager()
                .beginTransaction()
                .hide(fragment)
                .add(containerViewId, fg, "notFoundFragment")
                .commitAllowingStateLoss();
    }

    public static void pushNotFoundFragment(Fragment fragment, int containerViewId) {
        pushFragment(fragment, containerViewId, new NotFoundFragment(), "notFoundFragment", fragment.getActivity().getLocalClassName());
    }

    public static void pushShareDataFragment(Fragment fragment, int containerViewId) {
        pushFragment(fragment, containerViewId, new ShareDataFragment(), "shareDataFragment", fragment.getActivity().getLocalClassName());
    }

    public static void push2Fragments(Fragment fragment, int containerViewId) {
        NotFoundFragment fg1 = new NotFoundFragment();
        ShareDataFragment fg2 = new ShareDataFragment();
        String backStack = fragment.getActivity().getLocalClassName();
        pushFragment(fragment, containerViewId, fg1, "notFoundFragment", backStack);
        pushFragment(fg1, containerViewId, fg2, "shareDataFragment", backStack);
    }

    private static void pushFragment(Fragment fragment, int containerViewId, Fragment targetFragment, String tag, String backStack){
        fragment.getFragmentManager()
                .beginTransaction()
                .hide(fragment)
                .add(containerViewId, targetFragment, tag)
                .addToBackStack(backStack)
                .commitAllowingStateLoss();
    }

    public static void popFragment(Fragment fragment) {
        if(!fragment.getFragmentManager().popBackStackImmediate()){
            fragment.getActivity().onBackPressed();
        }
    }

}
