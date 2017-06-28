package com.jc.flora.apps.component.router.projects;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/5/16.
 */
public class EasyRouterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("基础路由跳转");
        setContentView(R.layout.activity_router_easy);
    }

    public void gotoLauncher(View v){
        RouterUtil.gotoLauncher(this);
    }

    public void gotoNotFound(View v){
        RouterUtil.gotoNotFound(this);
    }

    public void goWithData(View view) {
        RouterUtil.goWithData(this, "EasyRouterActivity");
    }

    public void goAndReceiveData(View view) {
        RouterUtil.goAndReceiveData(this, 1);
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
