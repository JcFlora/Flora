package com.jc.flora.apps.ui.captain.delegate;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.ui.captain.projects.Captain16TestFragment;
import com.jc.flora.apps.ui.captain.projects.Captain17TestFragment;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.scene.login.delegate.LoginActionDelegate;

/**
 * Created by Samurai on 2017/5/27.
 */
public class CaptainDelegate {

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

    private AppCompatActivity mActivity;

    private FragmentDelegate18 mFragmentDelegate;
    private IndicatorDelegate18 mIndicatorDelegate;
    private LinearLayout mLayoutCaptainIndicators;
    private LoginActionDelegate mLoginDelegate;

    private LinearLayout mLayoutCaptainBar;

    public void setActivity(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void setLayoutCaptainIndicators(LinearLayout layoutCaptainIndicators) {
        mLayoutCaptainIndicators = layoutCaptainIndicators;
    }

    public void setLayoutCaptainBar(LinearLayout layoutCaptainBar) {
        mLayoutCaptainBar = layoutCaptainBar;
    }

    public void init(){
        initLoginDelegate();
        initFragments();
        initIndicators();
        start();
    }

    private void initLoginDelegate(){
        mLoginDelegate = new LoginActionDelegate();
        mLoginDelegate.addToActivity(mActivity,"loginDelegate");
    }

    private void initFragments(){
        CaptainFragment[] fragmentList = new CaptainFragment[COUNT];
        fragmentList[0] = new Captain17TestFragment();
        ((Captain17TestFragment)fragmentList[0]).setView(mLayoutCaptainBar);
        for (int i = 1; i < COUNT; i++) {
            fragmentList[i] = new Captain16TestFragment();
            ((Captain16TestFragment)fragmentList[i]).setTitle(TITLES[i]);
        }
        mFragmentDelegate = new FragmentDelegate18();
        mFragmentDelegate.setContainerViewId(R.id.layout_content);
        mFragmentDelegate.setFragmentList(fragmentList);
    }

    private void initIndicators() {
        mIndicatorDelegate = new IndicatorDelegate18();
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
        mFragmentDelegate.init(mActivity);
        mIndicatorDelegate.init(START_INDEX, true);
        mIndicatorDelegate.setBadgeText(0,"28");
    }

    private IndicatorDelegate18.OnTabIndicatorClickListener mListener =
            new IndicatorDelegate18.OnTabIndicatorClickListener(){
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
            ToastDelegate.show(mActivity, "登录成功");
            mFragmentDelegate.onTabIndicatorCheckedChanged(LOGIN_INDEX);
        }
        @Override
        public void onLoginCancel() {
            ToastDelegate.show(mActivity, "取消登录");
            mIndicatorDelegate.rollback();
        }
    };

    public boolean back2Tab0() {
        return mIndicatorDelegate.back2Tab0();
    }

}
