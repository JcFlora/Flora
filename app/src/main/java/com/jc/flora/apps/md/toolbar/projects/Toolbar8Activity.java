package com.jc.flora.apps.md.toolbar.projects;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/6/18.
 */
public class Toolbar8Activity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉默认的ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_toolbar4);
        findViews();
        initViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
    }

    private void initViews() {
        showNavIcon();
        showTitle();
        setMenu();
    }

    private void showNavIcon(){
        mToolbar.setNavigationIcon(R.mipmap.ic_toolbar_nav);
        // 这个设置的是图标和标题之间的距离
        mToolbar.setContentInsetStartWithNavigation(0);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastDelegate.show(Toolbar8Activity.this,"你点击了导航按钮");
            }
        });
    }

    private void showTitle(){
        mToolbar.setTitle("首页");
        mToolbar.setTitleTextColor(Color.WHITE);
    }

    private void setMenu() {
        // 设置菜单布局
        mToolbar.inflateMenu(R.menu.toolbar8);
        // 设置溢出菜单图标
        mToolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.ic_toolbar_overflow));
        // 设置菜单点击事件
        mToolbar.setOnMenuItemClickListener(mMenuItemListener);
    }

    private Toolbar.OnMenuItemClickListener mMenuItemListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_search:
                    ToastDelegate.show(Toolbar8Activity.this, "你点击了搜索");
                    break;
                case R.id.action_notification:
                    ToastDelegate.show(Toolbar8Activity.this, "你点击了通知");
                    break;
                case R.id.action_settings:
                    ToastDelegate.show(Toolbar8Activity.this, "你点击了设置");
                    break;
                case R.id.action_about:
                    ToastDelegate.show(Toolbar8Activity.this, "你点击了关于");
                    break;
            }
            return true;
        }
    };

}