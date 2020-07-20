package com.jc.flora.apps.md.toolbar.projects;

import android.graphics.Color;
import android.os.Bundle;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2017/6/20.
 */
public class Toolbar6Activity extends AppCompatActivity {

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
        mToolbar.setTitle("溢出菜单添加图标：自定义弹出窗");
        mToolbar.setTitleTextColor(Color.WHITE);
//        // 这个设置的是图标和标题之间的距离
//        mToolbar.setContentInsetStartWithNavigation(0);
        // 设置菜单布局
        mToolbar.inflateMenu(R.menu.toolbar6);
        // 设置菜单点击事件
        mToolbar.setOnMenuItemClickListener(mMenuItemListener);
    }

    private Toolbar.OnMenuItemClickListener mMenuItemListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_more:
                    //这个地方要注意使用这种方式增加actionprovider不然会报错
                    MenuItemCompat.setActionProvider(item, new Toolbar6ActionProvider(Toolbar6Activity.this));
                    break;
            }
            return true;
        }
    };

}