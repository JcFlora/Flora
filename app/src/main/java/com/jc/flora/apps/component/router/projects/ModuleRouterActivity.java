package com.jc.flora.apps.component.router.projects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.component.router.delegate.ModuleRouterDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2019/1/15.
 */
public class ModuleRouterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("下沉接口实现组件间跳转路由（适应组件化开发）");
        setContentView(R.layout.activity_router_easy);
        initRouter();
    }

    private void initRouter(){
        // 路由组件注册，真实项目中请在各个Module的Application的onCreate()方法中调用
        ModuleRouterDelegate moduleRouterDelegate = ModuleRouterDelegate.getInstance();
        moduleRouterDelegate.setLauncherRouter(new ModuleRouterDelegate.LauncherRouter() {
            @Override
            public void gotoLauncher(Context context) {
                RouterUtil.gotoLauncher(context);
            }
        });
        moduleRouterDelegate.setNotFoundRouter(new ModuleRouterDelegate.NotFoundRouter() {
            @Override
            public void gotoNotFound(Context context) {
                RouterUtil.gotoNotFound(context);
            }

            @Override
            public void gotoNotFound2(Context context) {
                RouterUtil.gotoNotFound2(context);
            }
        });
        moduleRouterDelegate.setWithDataRouter(new ModuleRouterDelegate.WithDataRouter() {
            @Override
            public void goWithData(Context context, String from) {
                RouterUtil.goWithData(context, from);
            }
        });
        moduleRouterDelegate.setReceiveDataRouter(new ModuleRouterDelegate.ReceiveDataRouter() {
            @Override
            public void goAndReceiveData(AppCompatActivity activity, int requestCode) {
                RouterUtil.goAndReceiveData(activity, requestCode);
            }
        });
        moduleRouterDelegate.setH5Router(new ModuleRouterDelegate.H5Router() {
            @Override
            public void gotoH5(Context context, String url) {
                RouterUtil.gotoH5(context, url);
            }
        });
    }

    public void gotoLauncher(View v){
        ModuleRouterDelegate.getInstance().gotoLauncher(this);
    }

    public void gotoNotFound(View v){
        ModuleRouterDelegate.getInstance().gotoNotFound(this);
    }

    public void gotoNotFound2(View v){
        ModuleRouterDelegate.getInstance().gotoNotFound2(this);
    }

    public void goWithData(View view) {
        ModuleRouterDelegate.getInstance().goWithData(this, "ModuleRouterActivity");
    }

    public void goAndReceiveData(View view) {
        ModuleRouterDelegate.getInstance().goAndReceiveData(this, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            String s = data.getStringExtra("result");
            ToastDelegate.show(this,"返回数据"+s);
        }
    }
}
