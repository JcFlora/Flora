package com.jc.flora.apps.component.router.projects;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.router.delegate.ModuleRouterDelegate;
import com.jc.flora.apps.component.router.delegate.MultiRouterDelegate;
import com.jc.flora.apps.component.router.delegate.UrlRouter3Delegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2019/1/14.
 */
public class MultiRouterActivity extends AppCompatActivity {

    private static final String[] ROUTER_TYPES =
            {"ModuleRouter（组件路由）", "ActivityRouter（基础路由）",
            "FragmentRouter（Fragment路由）", "UrlRouter（Url路由）"};

    private static final String[] URLS =
            {"https://m.baidu.com/index.html?@target=launcher",
            "https://m.baidu.com/index.html?@target=notFound",
            "https://m.baidu.com/index.html?@target=notFound2",
            "https://m.baidu.com/index.html?@target=withData&from=UrlRouter3Activity",
            "https://m.baidu.com/index.html",
            "https://m.baidu.com/index.html?@target=unknown"};

    private TextView mTvRouterType, mTvUrl;
    private View[] mLayouts = new View[4];

    private String mShareData = "Test";

    public String getShareData() {
        return mShareData;
    }

    public void setShareData(String shareData) {
        mShareData = shareData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("合并多种路由");
        setContentView(R.layout.activity_router_multi);
        initRouter();
        findViews();
        initViews();
        initRootFragment();
    }

    private void initRouter(){
        // 路由组件注册，真实项目中请在各个Module的Application的onCreate()方法中调用
        ModuleRouterDelegate moduleRouterDelegate = MultiRouterDelegate.getModuleRouter();
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
        // 路由组件初始化，真实项目中请在Application的onCreate()方法中调用
        UrlRouter3Delegate.init();
    }

    private void findViews(){
        mTvRouterType = findViewById(R.id.tv_router_type);
        mTvUrl = findViewById(R.id.tv_url);
        mLayouts[0] = findViewById(R.id.layout_module);
        mLayouts[1] = findViewById(R.id.layout_activity);
        mLayouts[2] = findViewById(R.id.layout_fragment);
        mLayouts[3] = findViewById(R.id.layout_url);
    }

    private void initViews(){
        mTvRouterType.setText("当前路由类型：" + ROUTER_TYPES[0]);
        mTvUrl.setText(URLS[0]);
    }

    private void initRootFragment(){
        Root2Fragment rootFragment = new Root2Fragment();
        // 设置根控制器
        MultiRouterDelegate.getFragmentRouter().setRootFragmentForActivity(this, rootFragment, R.id.layout_fragment);
    }

    public void switchRouteType(View v){
        showRouterTypeListDialog();
    }

    private void showRouterTypeListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("切换路由类型");
        builder.setItems(ROUTER_TYPES, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mTvRouterType.setText("当前路由类型：" + ROUTER_TYPES[which]);
                for (View layout : mLayouts) {
                    layout.setVisibility(View.GONE);
                }
                mLayouts[which].setVisibility(View.VISIBLE);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    /**************************************************组件路由**************************************************************/

    public void gotoLauncherForModule(View v){
        MultiRouterDelegate.getModuleRouter().gotoLauncher(this);
    }

    public void gotoNotFoundForModule(View v){
        MultiRouterDelegate.getModuleRouter().gotoNotFound(this);
    }

    public void gotoNotFound2ForModule(View v){
        MultiRouterDelegate.getModuleRouter().gotoNotFound2(this);
    }

    public void goWithDataForModule(View view) {
        MultiRouterDelegate.getModuleRouter().goWithData(this, "MultiRouterActivity");
    }

    public void goAndReceiveDataForModule(View view) {
        MultiRouterDelegate.getModuleRouter().goAndReceiveData(this, 1);
    }

    /**************************************************基础路由**************************************************************/

    public void gotoLauncherForActivity(View v){
        MultiRouterDelegate.getActivityRouter().gotoLauncher(this);
    }

    public void gotoNotFoundForActivity(View v){
        MultiRouterDelegate.getActivityRouter().gotoNotFound(this);
    }

    public void gotoNotFound2ForActivity(View v){
        MultiRouterDelegate.getActivityRouter().gotoNotFound2(this);
    }

    public void goWithDataForActivity(View view) {
        MultiRouterDelegate.getActivityRouter().goWithData(this, "MultiRouterActivity");
    }

    public void goAndReceiveDataForActivity(View view) {
        MultiRouterDelegate.getActivityRouter().goAndReceiveData(this, 1);
    }

    /**************************************************Url路由**************************************************************/

    public void configUrl(View v){
        showUrlListDialog();
    }

    public void routePageWithUrl(View v){
        MultiRouterDelegate.getUrlRouter().routePage(this, mTvUrl.getText().toString().trim());
    }

    private void showUrlListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("配置Url");
        builder.setItems(URLS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mTvUrl.setText(URLS[which]);
            }
        });
        builder.setCancelable(true);
        builder.show();
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
