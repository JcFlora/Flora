package com.jc.flora.apps.md.toolbar.projects;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/6/18.
 */
public class Toolbar1Activity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉默认的ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_toolbar1);
        findViews();
        initViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
    }

    private void initViews() {
        mToolbar.setTitle("");
    }

    public void showNavIcon(View v){
        mToolbar.setNavigationIcon(R.mipmap.ic_toolbar_nav);
        // 这个设置的是图标和标题之间的距离
        mToolbar.setContentInsetStartWithNavigation(0);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastDelegate.show(Toolbar1Activity.this,"你点击了导航按钮");
            }
        });
    }

    public void showTitle(View v){
        mToolbar.setTitle("图标和标题的定制");
        mToolbar.setTitleTextColor(Color.WHITE);
    }

    public void showSubtitle(View v){
        mToolbar.setSubtitle("Logo和title、subtitle的间距比较小，看起来不如导航图标与它们两搭配美观");
        mToolbar.setSubtitleTextColor(0xFFCCCCCC);
    }

    public void showLogo(View v){
        mToolbar.setLogo(R.mipmap.ic_toolbar_logo);
    }

}