package com.jc.flora.apps.md.toolbar.projects;

import android.content.Context;
import androidx.core.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Samurai on 2017/6/21.
 */
public class Toolbar6ActionProvider extends ActionProvider {

    private Context mContext;

    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public Toolbar6ActionProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public boolean hasSubMenu() {
        return true;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add("扫一扫").setIcon(R.mipmap.ic_toolbar_more_scan).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ToastDelegate.show(mContext, "你点击了扫一扫");
                return false;
            }
        });
        subMenu.add("发起群聊").setIcon(R.mipmap.ic_toolbar_more_chat).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ToastDelegate.show(mContext, "你点击了发起群聊");
                return false;
            }
        });
        subMenu.add("添加好友").setIcon(R.mipmap.ic_toolbar_more_add_user).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ToastDelegate.show(mContext, "你点击了添加好友");
                return false;
            }
        });
        subMenu.add("云盘").setIcon(R.mipmap.ic_toolbar_more_space).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ToastDelegate.show(mContext, "你点击了云盘");
                return false;
            }
        });
        subMenu.add("邮件").setIcon(R.mipmap.ic_toolbar_more_mail).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ToastDelegate.show(mContext, "你点击了邮件");
                return false;
            }
        });
        super.onPrepareSubMenu(subMenu);
    }
}
