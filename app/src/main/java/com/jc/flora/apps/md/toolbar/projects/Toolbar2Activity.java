package com.jc.flora.apps.md.toolbar.projects;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/6/18.
 */
public class Toolbar2Activity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉默认的ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_toolbar2);
        findViews();
        initViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
    }

    private void initViews() {
        mToolbar.setTitle("菜单定制");
        mToolbar.setTitleTextColor(Color.WHITE);
//        // 这个设置的是图标和标题之间的距离
//        mToolbar.setContentInsetStartWithNavigation(0);
    }

    public void addMenuByXml(View v) {
        // 清空菜单
        mToolbar.getMenu().clear();
        // 设置菜单布局
        mToolbar.inflateMenu(R.menu.toolbar2);
        // 设置溢出菜单图标
        mToolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.ic_toolbar_overflow));
        // 设置菜单点击事件
        mToolbar.setOnMenuItemClickListener(mMenuItemListener);
    }

    public void changeMenu(View v) {
        if(mToolbar.getMenu().size() == 0){
            return;
        }
        // 修改第一个菜单的图标
        mToolbar.getMenu().getItem(0).setIcon(R.mipmap.ic_toolbar_nav);
        mToolbar.getMenu().getItem(1).setVisible(false);
        mToolbar.getMenu().getItem(2).setVisible(false);
        mToolbar.getMenu().getItem(3).setVisible(false);
    }

    private Toolbar.OnMenuItemClickListener mMenuItemListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_search:
                    ToastDelegate.show(Toolbar2Activity.this, "你点击了搜索菜单");
                    break;
                case R.id.action_notification:
                    ToastDelegate.show(Toolbar2Activity.this, "你点击了通知菜单");
                    break;
                case R.id.action_item1:
                    ToastDelegate.show(Toolbar2Activity.this, "你点击了item1菜单");
                    break;
                case R.id.action_item2:
                    ToastDelegate.show(Toolbar2Activity.this, "你点击了item2菜单");
                    break;
            }
            return true;
        }
    };

}