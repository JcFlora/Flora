package com.jc.flora.apps.md.toolbar.projects;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.md.toolbar.delegate.ToolbarDelegate3;

/**
 * Created by shijincheng on 2017/6/18.
 */
public class Toolbar3Activity extends AppCompatActivity {

    private ToolbarDelegate3 mToolbarDelegate;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbarDelegate = new ToolbarDelegate3(this);
        // 去掉默认的ActionBar
        mToolbarDelegate.removeActionBar();
        setContentView(R.layout.activity_toolbar3);
        findViews();
        initToolbar();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
    }

    private void initToolbar(){
        mToolbarDelegate.setToolbar(mToolbar);
    }

    public void showCenterTitle(View v){
        mToolbarDelegate.setCenterTitle("自定义居中标题", Color.WHITE, 18);
    }

    public void showLeftButton(View v){
        mToolbarDelegate.setLeftButton(R.mipmap.ic_toolbar_nav, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastDelegate.show(Toolbar3Activity.this,"你点击了左侧按钮");
            }
        });
    }

    public void showRightButton(View v){
        mToolbarDelegate.setRightButton(R.mipmap.ic_toolbar_notifications, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastDelegate.show(Toolbar3Activity.this,"你点击了右侧按钮");
            }
        });
    }

}