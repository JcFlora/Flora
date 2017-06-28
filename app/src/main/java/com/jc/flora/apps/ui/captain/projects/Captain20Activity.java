package com.jc.flora.apps.ui.captain.projects;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.captain.delegate.FragmentDelegate16;

/**
 * Created by shijincheng on 2017/5/27.
 */
public class Captain20Activity extends AppCompatActivity {

    private static final String[] TITLES = {"微信", "通讯录", "发现", "我"};
    private static final int COUNT = 4;
    private static final int START_INDEX = 0;
    private static final int[] ICON_RES = {R.drawable.selector_tab_chats,
            R.drawable.selector_tab_contacts, R.drawable.selector_tab_discover, R.drawable.selector_tab_about_me};

    private ViewPager mVpContainer;
    private TabLayout mLayoutCaptainIndicators;
    private FragmentDelegate16 mFragmentDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用官方TabLayout实现");
        setContentView(R.layout.activity_captain20);
        initViews();
        initFragments();
        initIndicators();
    }

    private void initViews() {
        mVpContainer = (ViewPager) findViewById(R.id.vp_content);
        mLayoutCaptainIndicators = (TabLayout) findViewById(R.id.layout_captain_indicators);
    }

    private void initFragments(){
        Captain16TestFragment[] fragmentList = new Captain16TestFragment[COUNT];
        for (int i = 0; i < COUNT; i++) {
            fragmentList[i] = new Captain16TestFragment();
            fragmentList[i].setTitle(TITLES[i]);
        }
        mFragmentDelegate = new FragmentDelegate16();
        mFragmentDelegate.setVpContainer(mVpContainer);
        mFragmentDelegate.setFragmentList(fragmentList);
        mFragmentDelegate.init(this);
    }

    private void initIndicators() {
        mLayoutCaptainIndicators.setupWithViewPager(mVpContainer);
        mLayoutCaptainIndicators.setSelectedTabIndicatorHeight(0);
        for (int i = 0; i < COUNT; i++) {
            //获得到对应位置的Tab
            TabLayout.Tab itemTab = mLayoutCaptainIndicators.getTabAt(i);
            if(itemTab != null) {
                //设置自定义的标题
                itemTab.setCustomView(R.layout.item_tab);
                TextView textView = (TextView) itemTab.getCustomView().findViewById(R.id.tv_name);
                textView.setText(TITLES[i]);
                ImageView imageView = (ImageView) itemTab.getCustomView().findViewById(R.id.iv_img);
                imageView.setImageResource(ICON_RES[i]);
            }
        }
        mLayoutCaptainIndicators.getTabAt(START_INDEX).getCustomView().setSelected(true);
    }

}