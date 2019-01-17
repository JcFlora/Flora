package com.jc.flora.apps.component.router.delegate;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

    public void gotoNotFoundFragment(Fragment fragment) {
        NotFoundFragment fg = new NotFoundFragment();
        fragment.getFragmentManager()
                .beginTransaction()
                .hide(fragment)
                .add(getContainerViewIdByFragment(fragment), fg, "notFoundFragment")
                .commitAllowingStateLoss();
    }

    public void pushNotFoundFragment(Fragment fragment) {
        pushFragment(fragment, new NotFoundFragment(), "notFoundFragment");
    }

    public void pushShareDataFragment(Fragment fragment) {
        pushFragment(fragment, new ShareData2Fragment(), "shareDataFragment");
    }

    public void push2Fragments(Fragment fragment) {
        // push第一个
        NotFoundFragment fg1 = new NotFoundFragment();
        pushFragment(fragment, fg1, "notFoundFragment");
        // push第二个
        ShareData2Fragment fg2 = new ShareData2Fragment();
        int containerViewId = getContainerViewIdByFragment(fragment);
        String backStack = getBackStackNameByFragment(fragment);
        pushFragment(fg1, containerViewId, fg2, "shareDataFragment", backStack);
    }

    private void pushFragment(Fragment fragment, Fragment targetFragment, String tag){
        int containerViewId = getContainerViewIdByFragment(fragment);
        String backStack = getBackStackNameByFragment(fragment);
        pushFragment(fragment, containerViewId, targetFragment, tag, backStack);
    }

    private String getBackStackNameByFragment(Fragment fragment){
        return fragment.getActivity().getLocalClassName();
    }

    private int getContainerViewIdByFragment(Fragment fragment){
        return ((View)fragment.getView().getParent()).getId();
    }

    private void pushFragment(Fragment fragment, int containerViewId, Fragment targetFragment, String tag, String backStack){
        // 注意：addToBackStack和commitNow不能一起使用。
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
