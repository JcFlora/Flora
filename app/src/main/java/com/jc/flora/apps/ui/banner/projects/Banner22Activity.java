package com.jc.flora.apps.ui.banner.projects;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.banner.delegate.BannerDelegate22;
import com.jc.flora.apps.ui.banner.ec.BannerInfo;
import com.jc.flora.apps.ui.banner.ec.BaseApi;
import com.jc.flora.apps.ui.banner.ec.GetBannerListApi;
import com.jc.flora.apps.ui.banner.widget.BannerView21;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.util.ArrayList;

/**
 * Created by Samurai on 2017/8/22.
 */
public class Banner22Activity extends AppCompatActivity {

    /** 下拉刷新布局 */
    private SwipeRefreshLayout mSrlContent;
    private BannerView21 mBv;
    private BannerDelegate22 mBannerDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("电商平台上的应用");
        setContentView(R.layout.activity_banner21);
        findViews();
        initBanner();
        requestBannerInfo();
        initReload();
    }

    private void findViews() {
        mSrlContent = (SwipeRefreshLayout) findViewById(R.id.srl_content);
        mBv = (BannerView21) findViewById(R.id.bv1);
    }

    private void initBanner() {
        mBannerDelegate = new BannerDelegate22(this, mBv);
    }

    private void requestBannerInfo() {
        new GetBannerListApi().getBannerList("").subscribe(new BaseApi.ObserverAdapter<ArrayList<BannerInfo>>() {
            @Override
            public void onNext(ArrayList<BannerInfo> bannerInfos) {
                mBannerDelegate.reload(bannerInfos);
            }
        });
    }

    private void initReload() {
        // 设置下拉刷新色调
        mSrlContent.setColorSchemeResources(
                R.color.colorAccent,
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        // 设置下拉刷新事件
        mSrlContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onReload();
            }
        });
    }

    private void onReload() {
        // 模拟耗时
        mSrlContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 关闭下拉刷新的加载动画，防止接口超时时加载动画一直进行
                mSrlContent.setRefreshing(false);
                ToastDelegate.show(Banner22Activity.this,"刷新数据");
                requestBannerInfo();
            }
        },1500);
    }

}