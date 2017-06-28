package com.jc.flora.apps.md.toolbar.projects;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/6/20.
 */
public class Toolbar4Activity extends AppCompatActivity {

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
        mToolbar.setTitle("溢出菜单添加图标：xml");
        mToolbar.setTitleTextColor(Color.WHITE);
//        // 这个设置的是图标和标题之间的距离
//        mToolbar.setContentInsetStartWithNavigation(0);
        // 设置菜单布局
        mToolbar.inflateMenu(R.menu.toolbar4);
        // 设置菜单点击事件
        mToolbar.setOnMenuItemClickListener(mMenuItemListener);
    }

    private Toolbar.OnMenuItemClickListener mMenuItemListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_scan:
                    ToastDelegate.show(Toolbar4Activity.this, "你点击了扫一扫");
                    break;
                case R.id.action_chat:
                    ToastDelegate.show(Toolbar4Activity.this, "你点击了发起群聊");
                    break;
                case R.id.action_add_user:
                    ToastDelegate.show(Toolbar4Activity.this, "你点击了添加好友");
                    break;
                case R.id.action_space:
                    ToastDelegate.show(Toolbar4Activity.this, "你点击了云盘");
                    break;
                case R.id.action_mail:
                    ToastDelegate.show(Toolbar4Activity.this, "你点击了邮件");
                    break;
            }
            return true;
        }
    };

}