package com.jc.flora.apps.component.exit.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jc.flora.R;
import com.jc.flora.apps.component.exit.delegate.AppRestartDelegate;

/**
 * Created by Shijincheng on 2018/4/25.
 */

public class AppRestart2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("重启（一键退出+启动首页）");
        setContentView(R.layout.activity_exit_app_restart);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setText("重启并进入登录页面");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartApp();
            }
        });
        AppRestartDelegate.push(this);
    }

    private void restartApp() {
        AppRestartDelegate.restartApp(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppRestartDelegate.pop();
    }

}