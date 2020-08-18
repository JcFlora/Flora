package com.jc.flora.apps.ui.captain.projects;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.ui.captain.delegate.CaptainFragment;
import com.jc.flora.apps.ui.captain.delegate.FragmentDelegate16;
import com.jc.flora.apps.ui.captain.delegate.IndicatorDelegate16;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.scene.identity.delegate.LoginActionDelegate;

/**
 * Created by shijincheng on 2017/5/2.
 */
public class Captain17Activity extends AppCompatActivity {

    private static final String[] TITLES = {"微信", "通讯录", "发现", "我"};
    private static final int COUNT = 4;
    private static final int START_INDEX = 0;
    private static final int LOGIN_INDEX = 3;
    private static final float TEXT_SIZE = 11;
    private static final int COLOR_UNCHECKED = Color.parseColor("#999999");
    private static final int COLOR_CHECKED = Color.parseColor("#45C01A");
    private static final int[] ICON_RES_NORMAL = {R.drawable.wx_chats,
            R.drawable.wx_contacts, R.drawable.wx_discover, R.drawable.wx_about_me};
    private static final int[] ICON_RES_FOCUS = {R.drawable.wx_chats_green,
            R.drawable.wx_contacts_green, R.drawable.wx_discover_green,R.drawable.wx_about_me_green};

    private FragmentDelegate16 mFragmentDelegate;
    private IndicatorDelegate16 mIndicatorDelegate;
    private LinearLayout mLayoutCaptainIndicators;
    private LoginActionDelegate mLoginDelegate;

    private LinearLayout mLayoutCaptainBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("浮动导航条（沉浸列表）");
        setContentView(R.layout.activity_captain17);
        initViews();
        initLoginDelegate();
        initFragments();
        initIndicators();
        start();
    }

    private void initViews() {
        mLayoutCaptainIndicators = (LinearLayout) findViewById(R.id.layout_captain_indicators);
        mLayoutCaptainBar = (LinearLayout) findViewById(R.id.layout_captain_bar);
    }

    private void initLoginDelegate(){
        mLoginDelegate = new LoginActionDelegate();
        mLoginDelegate.addToActivity(this,"loginDelegate");
    }

    private void initFragments(){
        CaptainFragment[] fragmentList = new CaptainFragment[COUNT];
        fragmentList[0] = new Captain17TestFragment();
        ((Captain17TestFragment)fragmentList[0]).setView(mLayoutCaptainBar);
        for (int i = 1; i < COUNT; i++) {
            fragmentList[i] = new Captain16TestFragment();
            ((Captain16TestFragment)fragmentList[i]).setTitle(TITLES[i]);
        }
        mFragmentDelegate = new FragmentDelegate16();
        mFragmentDelegate.setContainerViewId(R.id.layout_content);
        mFragmentDelegate.setFragmentList(fragmentList);
    }

    private void initIndicators() {
        mIndicatorDelegate = new IndicatorDelegate16();
        mIndicatorDelegate.setCount(COUNT);
        mIndicatorDelegate.setTitles(TITLES);
        mIndicatorDelegate.setTextSize(TEXT_SIZE);
        mIndicatorDelegate.setTextColor(COLOR_UNCHECKED, COLOR_CHECKED);
        mIndicatorDelegate.setIconResNormal(ICON_RES_NORMAL);
        mIndicatorDelegate.setIconResFocus(ICON_RES_FOCUS);
        mIndicatorDelegate.setLayoutCaptainIndicators(mLayoutCaptainIndicators);
        mIndicatorDelegate.setOnTabIndicatorClickListener(mListener);
    }

    private void start(){
        mFragmentDelegate.init(this);
        mIndicatorDelegate.init(START_INDEX, true);
        mIndicatorDelegate.setBadgeText(0,"28");
    }

    private IndicatorDelegate16.OnTabIndicatorClickListener mListener =
            new IndicatorDelegate16.OnTabIndicatorClickListener(){
                @Override
                public void onTabIndicatorCheckedChanged(int checkedPosition) {
                    if (checkedPosition == LOGIN_INDEX) {
                        mLoginDelegate.loginIntercept(mLoginActionCallback);
                    } else {
                        mFragmentDelegate.onTabIndicatorCheckedChanged(checkedPosition);
                    }
                }
                @Override
                public void onTabIndicatorReClick(int position) {
                    mFragmentDelegate.onTabIndicatorReClick(position);
                }
            };

    private LoginActionDelegate.LoginActionCallback mLoginActionCallback = new LoginActionDelegate.LoginActionCallback() {
        @Override
        public void isLoggedIn() {
            mFragmentDelegate.onTabIndicatorCheckedChanged(LOGIN_INDEX);
        }
        @Override
        public void onLoginSuccess() {
            ToastDelegate.show(Captain17Activity.this, "登录成功");
            mFragmentDelegate.onTabIndicatorCheckedChanged(LOGIN_INDEX);
        }
        @Override
        public void onLoginCancel() {
            ToastDelegate.show(Captain17Activity.this, "取消登录");
            mIndicatorDelegate.rollback();
        }
    };

}