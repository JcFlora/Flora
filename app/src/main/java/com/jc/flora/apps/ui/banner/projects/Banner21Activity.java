package com.jc.flora.apps.ui.banner.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jc.flora.R;
import com.jc.flora.apps.ui.banner.transformer.TransitionEffect;
import com.jc.flora.apps.ui.banner.widget.BannerView21;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Samurai on 2017/8/21.
 */
public class Banner21Activity extends AppCompatActivity {

    private static final String[] IMAGE_URIS =
            {"https://img.zcool.cn/community/011fc259a36272a801211d25e148bc.jpg",
                    "https://img.zcool.cn/community/0196e859a36274a8012028a94e08d3.jpg",
                    "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
                    "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
                    "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg"};

    /** 下拉刷新布局 */
    private SwipeRefreshLayout mSrlContent;
    /** 刷新数据的任务 */
    private Runnable mStopReloadTask;
    private BannerView21 mBv1, mBv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加数据投放器，支持刷新");
        setContentView(R.layout.activity_banner21);
        findViews();
        initReload();
        initBanner1();
        initBanner2();
    }

    private void findViews() {
        mSrlContent = (SwipeRefreshLayout) findViewById(R.id.srl_content);
        mBv1 = (BannerView21) findViewById(R.id.bv1);
        mBv2 = (BannerView21) findViewById(R.id.bv2);
    }

    private void initReload(){
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
                refreshDataAndUi();
            }
        });
    }

    private void initBanner1(){
        mBv1.setIsAutoPlay(true);
        mBv1.setAutoPlayDuration(3000);
        mBv1.setAnimDuration(1800);
        mBv1.setIndicatorPosition(BannerView21.Position.CENTER_BOTTOM);
        mBv1.setIndicatorsLayoutXMargin(25);
        mBv1.setIndicatorsLayoutYMargin(25);
        mBv1.setIndicatorsLayoutPadding(10);
        mBv1.setIndicatorFloated(false);
        mBv1.setIndicatorResId(R.drawable.dot_unselected, R.drawable.dot_selected);
        mBv1.setIndicatorSpace(20);
        // 添加数据投放器，修改数据投放方式，支持任意方式加载数据
        mBv1.setBannerDataDelivery(new BannerView21.BannerDataDelivery() {
            @Override
            public int getCount() {
                return IMAGE_URIS.length;
            }
            @Override
            public void deliverImage(ImageView imageView, int position) {
                Glide.with(Banner21Activity.this).load(IMAGE_URIS[position]).into(imageView);
            }
        });
        mBv1.setOnBannerItemClickListener(new BannerView21.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastDelegate.show(Banner21Activity.this, "你点击了第" + (position + 1) + "个广告");
            }
        });
        //修改后setPageTransformer要在start前调用
        mBv1.setPageTransformer(TransitionEffect.Flip);
        mBv1.start();
    }

    private void initBanner2(){
        mBv2.setIsAutoPlay(true);
        mBv2.setAutoPlayDuration(4000);
        mBv2.setAnimDuration(1200);
        mBv2.setIndicatorPosition(BannerView21.Position.CENTER_BOTTOM);
        mBv2.setIndicatorsLayoutXMargin(25);
        mBv2.setIndicatorsLayoutYMargin(25);
        mBv2.setIndicatorsLayoutPadding(10);
        mBv2.setIndicatorFloated(true);
        mBv2.setIndicatorResId(R.drawable.dot_unselected, R.drawable.dot_selected);
        mBv2.setIndicatorSpace(20);
        // 添加数据投放器，修改数据投放方式，支持任意方式加载数据
        mBv2.setBannerDataDelivery(new BannerView21.BannerDataDelivery() {
            @Override
            public int getCount() {
                return IMAGE_URIS.length;
            }
            @Override
            public void deliverImage(ImageView imageView, int position) {
                Glide.with(Banner21Activity.this).load(IMAGE_URIS[position]).into(imageView);
            }
        });
        mBv2.setOnBannerItemClickListener(new BannerView21.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastDelegate.show(Banner21Activity.this, "你点击了第" + (position + 1) + "个广告");
            }
        });
        //修改后setPageTransformer要在start前调用
        mBv2.setPageTransformer(TransitionEffect.Accordion);
        mBv2.start();
    }

    private void refreshDataAndUi() {
        mStopReloadTask = new Runnable() {
            @Override
            public void run() {
                onReload();
            }
        };
        mSrlContent.postDelayed(mStopReloadTask, 1500);
    }

    private void onReload(){
        // 关闭下拉刷新的加载动画，防止接口超时时加载动画一直进行
        mSrlContent.setRefreshing(false);
        ToastDelegate.show(Banner21Activity.this,"刷新数据");
        // 添加数据投放器，修改数据投放方式，支持任意方式加载数据
        mBv1.setBannerDataDelivery(new BannerView21.BannerDataDelivery() {
            @Override
            public int getCount() {
                return IMAGE_URIS.length;
            }
            @Override
            public void deliverImage(ImageView imageView, int position) {
                Glide.with(Banner21Activity.this).load(IMAGE_URIS[position]).into(imageView);
            }
        });
        // 添加数据投放器，修改数据投放方式，支持任意方式加载数据
        mBv2.setBannerDataDelivery(new BannerView21.BannerDataDelivery() {
            @Override
            public int getCount() {
                return IMAGE_URIS.length;
            }
            @Override
            public void deliverImage(ImageView imageView, int position) {
                Glide.with(Banner21Activity.this).load(IMAGE_URIS[position]).into(imageView);
            }
        });
        mBv1.reload();
        mBv2.reload();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSrlContent.removeCallbacks(mStopReloadTask);
    }

}