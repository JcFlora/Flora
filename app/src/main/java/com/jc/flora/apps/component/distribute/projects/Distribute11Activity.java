package com.jc.flora.apps.component.distribute.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.component.distribute.delegate.AppForegroundDelegate;
import com.jc.flora.apps.component.distribute.delegate.Distribute11Delegate;
import com.jc.flora.launcher.NotFoundActivity;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Shijincheng on 2018/9/1.
 */
public class Distribute11Activity extends AppCompatActivity {

    private static final String FRAGMENTS_TAG = "android:fragments";
    private static final String SUPPORT_FRAGMENTS_TAG = "android:support:fragments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 前台感知组件初始化，真实项目中请在Application的onCreate()方法中调用
        AppForegroundDelegate.init(getApplication());
        // 由于页面中使用了Fragment作为业务分发，为了防止后台内存释放后，fragment从缓存中恢复时成员变量为空
        // 特地去掉fragment的自动缓存，这样后台内存释放后，fragment进行重新加载，不会有成员变量为空的问题
        if(savedInstanceState != null){
            savedInstanceState.putParcelable(FRAGMENTS_TAG, null);
            savedInstanceState.putParcelable(SUPPORT_FRAGMENTS_TAG, null);
        }
        super.onCreate(savedInstanceState);
        setTitle("分发一个需要感知前后台生命周期的业务功能");
        setContentView(R.layout.activity_distribute11);
        initDelegate();
    }

    /**
     * 感知前后台生命周期的业务功能，从后台切回前台时会进行业务实现
     */
    private void initDelegate(){
        Distribute11Delegate delegate = new Distribute11Delegate();
        delegate.addToActivity(this,"myDelegate");
    }

    public void gotoNextPage(View v){
        startActivity(new Intent(this, NotFoundActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放资源，真实项目中请在Application的onDestroy()方法中调用
        AppForegroundDelegate.destroy(getApplication());
    }
}