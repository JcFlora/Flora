package com.jc.flora.apps.component.router.projects;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jc.flora.R;
import com.jc.flora.apps.component.router.delegate.MultiRouterDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Shijincheng on 2019/11/16.
 */
public class Root2Fragment extends Fragment{

    private MultiRouterActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MultiRouterActivity)getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_router_root_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_goto_launcher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiRouterDelegate.getFragmentRouter().gotoLauncher(Root2Fragment.this);
            }
        });
        view.findViewById(R.id.btn_goto_not_found).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiRouterDelegate.getFragmentRouter().gotoNotFound(Root2Fragment.this);
            }
        });
        view.findViewById(R.id.btn_go_with_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiRouterDelegate.getFragmentRouter().goWithData(Root2Fragment.this, "RootFragment");
            }
        });
        view.findViewById(R.id.btn_go_and_receive_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiRouterDelegate.getFragmentRouter().goAndReceiveData(Root2Fragment.this, 1);
            }
        });
        view.findViewById(R.id.btn_goto_not_found_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiRouterDelegate.getFragmentRouter().gotoNotFoundFragment(Root2Fragment.this);
            }
        });
        view.findViewById(R.id.btn_push_not_found_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiRouterDelegate.getFragmentRouter().pushNotFoundFragment(Root2Fragment.this);
            }
        });
        view.findViewById(R.id.btn_push_share_data_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiRouterDelegate.getFragmentRouter().pushShareDataFragment(Root2Fragment.this);
            }
        });
        view.findViewById(R.id.btn_push_2_fragments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiRouterDelegate.getFragmentRouter().push2Fragments(Root2Fragment.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            String s = data.getStringExtra("result");
            ToastDelegate.show(getActivity(),"返回数据"+s);
        }
    }

    /**
     * Fragment路由中，用此方法代替onResume和onPause
     * 但不同的是，fragment首次初始化后添加到Activity的时候，不会调用该方法
     * 所以第一次展示共享数据，需要从生命周期方法里走
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            ToastDelegate.show(mActivity, mActivity.getShareData());
        }
    }

}