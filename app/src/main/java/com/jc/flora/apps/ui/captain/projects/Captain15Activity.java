package com.jc.flora.apps.ui.captain.projects;

import android.graphics.Color;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.ui.captain.delegate.FragmentDelegate13;
import com.jc.flora.apps.ui.captain.delegate.IndicatorDelegate15;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.scene.identity.delegate.LoginActionDelegate;

/**
 * Created by shijincheng on 2017/5/2.
 */
public class Captain15Activity extends AppCompatActivity {

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

    private FragmentDelegate13 mFragmentDelegate;
    private IndicatorDelegate15 mIndicatorDelegate;
    private ViewPager mVpContainer;
    private LinearLayout mLayoutCaptainIndicators;
    private LoginActionDelegate mLoginDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用索引记录进行重构");
        setContentView(R.layout.activity_captain9);
//        setContentView(R.layout.activity_captain10);
        initViews();
        initLoginDelegate();
        initFragments();
        initIndicators();
        start();
    }

    private void initViews() {
//        mVpContainer = (ViewPager) findViewById(R.id.vp_content);
        mLayoutCaptainIndicators = (LinearLayout) findViewById(R.id.layout_captain_indicators);
    }

    private void initLoginDelegate(){
        mLoginDelegate = new LoginActionDelegate();
        mLoginDelegate.addToActivity(this,"loginDelegate");
    }

    private void initFragments(){
        CaptainTestFragment[] fragmentList = new CaptainTestFragment[COUNT];
        for (int i = 0; i < COUNT; i++) {
            fragmentList[i] = new CaptainTestFragment();
            fragmentList[i].setTitle(TITLES[i]);
        }
        mFragmentDelegate = new FragmentDelegate13();
        mFragmentDelegate.setContainerViewId(R.id.layout_content);
//        mFragmentDelegate.setVpContainer(mVpContainer);
        mFragmentDelegate.setFragmentList(fragmentList);
    }

    private void initIndicators() {
        mIndicatorDelegate = new IndicatorDelegate15();
        mIndicatorDelegate.setCount(COUNT);
        mIndicatorDelegate.setTitles(TITLES);
        mIndicatorDelegate.setTextSize(TEXT_SIZE);
        mIndicatorDelegate.setTextColor(COLOR_UNCHECKED, COLOR_CHECKED);
        mIndicatorDelegate.setIconResNormal(ICON_RES_NORMAL);
        mIndicatorDelegate.setIconResFocus(ICON_RES_FOCUS);
        mIndicatorDelegate.setLayoutCaptainIndicators(mLayoutCaptainIndicators);
        mIndicatorDelegate.setOnTabIndicatorCheckedChangeListener(mListener);
        mFragmentDelegate.setOnPageChangeListener(mIndicatorDelegate);
    }

    private void start(){
        mFragmentDelegate.init(this);
        mIndicatorDelegate.init(START_INDEX, true);
        mIndicatorDelegate.setBadgeText(0,"28");
    }

    private IndicatorDelegate15.OnTabIndicatorCheckedChangeListener mListener =
            new IndicatorDelegate15.OnTabIndicatorCheckedChangeListener() {
                @Override
                public void onTabIndicatorCheckedChanged(int checkedPosition) {
                    if (checkedPosition == LOGIN_INDEX) {
                        mLoginDelegate.loginIntercept(mLoginActionCallback);
                    } else {
                        mFragmentDelegate.onTabIndicatorCheckedChanged(checkedPosition);
                    }
                }
            };

    private LoginActionDelegate.LoginActionCallback mLoginActionCallback = new LoginActionDelegate.LoginActionCallback() {
        @Override
        public void isLoggedIn() {
            mFragmentDelegate.onTabIndicatorCheckedChanged(LOGIN_INDEX);
        }
        @Override
        public void onLoginSuccess() {
            ToastDelegate.show(Captain15Activity.this, "登录成功");
            mFragmentDelegate.onTabIndicatorCheckedChanged(LOGIN_INDEX);
        }
        @Override
        public void onLoginCancel() {
            ToastDelegate.show(Captain15Activity.this, "取消登录");
            mIndicatorDelegate.rollback();
        }
    };

}