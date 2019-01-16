package com.jc.flora.apps.component.router.delegate;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.apps.component.router.projects.GetDataTestActivity;
import com.jc.flora.apps.component.router.projects.NotFoundFragment;
import com.jc.flora.apps.component.router.projects.ReceiveDataTestActivity;
import com.jc.flora.apps.component.router.projects.ShareData2Fragment;
import com.jc.flora.launcher.LauncherActivity;
import com.jc.flora.launcher.NotFoundActivity;

/**
 * Created by Shijincheng on 2019/1/16.
 */

public class FragmentRouterDelegate {

    private static FragmentRouterDelegate sInstance = new FragmentRouterDelegate();

    public static FragmentRouterDelegate getInstance(){
        return sInstance;
    }

    private FragmentRouterDelegate(){}

    public void gotoLauncher(Fragment fragment) {
        fragment.startActivity(new Intent(fragment.getActivity(), LauncherActivity.class));
    }

    public void gotoNotFound(Fragment fragment) {
        fragment.startActivity(new Intent(fragment.getActivity(), NotFoundActivity.class));
    }

    public void goWithData(Fragment fragment, String from) {
        Intent intent = new Intent(fragment.getActivity(), GetDataTestActivity.class);
        intent.putExtra("from", from);
        fragment.startActivity(intent);
    }

    public void goAndReceiveData(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), ReceiveDataTestActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    public void setRootFragmentForActivity(AppCompatActivity activity, Fragment fragment, int containerViewId){
        if(activity != null){
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(containerViewId, fragment, "rootFragment")
                    .commitAllowingStateLoss();
        }
    }

    public void gotoNotFoundFragment(Fragment fragment, int containerViewId) {
        NotFoundFragment fg = new NotFoundFragment();
        fragment.getFragmentManager()
                .beginTransaction()
                .hide(fragment)
                .add(containerViewId, fg, "notFoundFragment")
                .commitAllowingStateLoss();
    }

    public void pushNotFoundFragment(Fragment fragment, int containerViewId) {
        pushFragment(fragment, containerViewId, new NotFoundFragment(), "notFoundFragment", fragment.getActivity().getLocalClassName());
    }

    public void pushShareDataFragment(Fragment fragment, int containerViewId) {
        pushFragment(fragment, containerViewId, new ShareData2Fragment(), "shareDataFragment", fragment.getActivity().getLocalClassName());
    }

    public void push2Fragments(Fragment fragment, int containerViewId) {
        NotFoundFragment fg1 = new NotFoundFragment();
        ShareData2Fragment fg2 = new ShareData2Fragment();
        String backStack = fragment.getActivity().getLocalClassName();
        pushFragment(fragment, containerViewId, fg1, "notFoundFragment", backStack);
        pushFragment(fg1, containerViewId, fg2, "shareDataFragment", backStack);
    }

    private void pushFragment(Fragment fragment, int containerViewId, Fragment targetFragment, String tag, String backStack){
        fragment.getFragmentManager()
                .beginTransaction()
                .hide(fragment)
                .add(containerViewId, targetFragment, tag)
                .addToBackStack(backStack)
                .commitAllowingStateLoss();
    }

    public void popFragment(Fragment fragment) {
        if(!fragment.getFragmentManager().popBackStackImmediate()){
            fragment.getActivity().onBackPressed();
        }
    }

}
