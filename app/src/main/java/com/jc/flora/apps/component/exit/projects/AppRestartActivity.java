package com.jc.flora.apps.component.exit.projects;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jc.flora.R;
import com.jc.flora.apps.component.exit.delegate.AppRestartDelegate;

/**
 * Created by Shijincheng on 2018/4/25.
 */

public class AppRestartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("重启（一键退出+启动首页）");
        setContentView(R.layout.activity_exit_app_restart);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setText("进入第二个页面");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNextActivity();
            }
        });
        AppRestartDelegate.push(this);
    }

    private void gotoNextActivity() {
        startActivity(new Intent(this, AppRestart2Activity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppRestartDelegate.pop();
    }
}