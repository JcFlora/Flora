package com.jc.flora.apps.md.toolbar.projects;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.lang.reflect.Method;

/**
 * Created by shijincheng on 2017/6/20.
 */
public class Toolbar5Activity extends AppCompatActivity {

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
        mToolbar.setTitle("溢出菜单添加图标：反射");
        mToolbar.setTitleTextColor(Color.WHITE);
//        // 这个设置的是图标和标题之间的距离
//        mToolbar.setContentInsetStartWithNavigation(0);
        // 替换ActionBar，从而回调onCreateOptionsMenu
        setSupportActionBar(mToolbar);
        // 设置溢出菜单图标
        mToolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.ic_toolbar_overflow));
        // 设置菜单点击事件
        mToolbar.setOnMenuItemClickListener(mMenuItemListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar5, menu);
        return true;
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try{
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    private Toolbar.OnMenuItemClickListener mMenuItemListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_scan:
                    ToastDelegate.show(Toolbar5Activity.this, "你点击了扫一扫");
                    break;
                case R.id.action_chat:
                    ToastDelegate.show(Toolbar5Activity.this, "你点击了发起群聊");
                    break;
                case R.id.action_add_user:
                    ToastDelegate.show(Toolbar5Activity.this, "你点击了添加好友");
                    break;
                case R.id.action_space:
                    ToastDelegate.show(Toolbar5Activity.this, "你点击了云盘");
                    break;
                case R.id.action_mail:
                    ToastDelegate.show(Toolbar5Activity.this, "你点击了邮件");
                    break;
            }
            return true;
        }
    };

}