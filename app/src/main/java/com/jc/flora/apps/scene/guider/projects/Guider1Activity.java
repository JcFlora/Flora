package com.jc.flora.apps.scene.guider.projects;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.ui.stable.delegate.StableDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samurai on 2017/5/28.
 */
public class Guider1Activity extends AppCompatActivity {

    private ViewPager mVpGuider;
    private List<View> mViews;
    private View mBtnPass;
    private View mBtnStart;

    private ImageView[] mIvIndicators = new ImageView[5];
    private static final int[] INDICATOR_RES_IDS = {R.id.iv_indicator0,
            R.id.iv_indicator1, R.id.iv_indicator2, R.id.iv_indicator3, R.id.iv_indicator4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StableDelegate(this).hideStatusBar();
        setContentView(R.layout.activity_guider1);
        findViews();
        initViews();
        initData();
        setAdapter();
    }

    private void findViews() {
        mVpGuider = (ViewPager) findViewById(R.id.vp_guider);
        mBtnPass = findViewById(R.id.btn_pass);
        mBtnStart = findViewById(R.id.btn_start);
        for (int i = 0; i < 5 ; i++) {
            mIvIndicators[i] = (ImageView) findViewById(INDICATOR_RES_IDS[i]);
        }
    }

    private void initViews() {
        mVpGuider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                setIndicatorChecked(position);
                mBtnPass.setVisibility(position == 4 ? View.GONE : View.VISIBLE);
                mBtnStart.setVisibility(position == 4 ? View.VISIBLE : View.GONE);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mBtnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastDelegate.show(Guider1Activity.this,"你点击了跳过");
            }
        });
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastDelegate.show(Guider1Activity.this,"你点击了开始");
            }
        });
    }

    private void initData() {
        mViews = new ArrayList<>();
        ImageView iv1 = new ImageView(this);
        ImageView iv2 = new ImageView(this);
        ImageView iv3 = new ImageView(this);
        ImageView iv4 = new ImageView(this);
        ImageView iv5 = new ImageView(this);
        iv1.setImageResource(R.drawable.guider_page1);
        iv2.setImageResource(R.drawable.guider_page2);
        iv3.setImageResource(R.drawable.guider_page3);
        iv4.setImageResource(R.drawable.guider_page4);
        iv5.setImageResource(R.drawable.guider_page5);
        iv1.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv2.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv3.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv4.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv5.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mViews.add(iv1);
        mViews.add(iv2);
        mViews.add(iv3);
        mViews.add(iv4);
        mViews.add(iv5);
    }

    private void setAdapter() {
        GuiderAdapter adapter = new GuiderAdapter(mViews);
        mVpGuider.setAdapter(adapter);
    }

    private void setIndicatorChecked(int position) {
        for (ImageView mIvIndicator : mIvIndicators) {
            mIvIndicator.setImageResource(R.drawable.guider_star_default);
        }
        mIvIndicators[position].setImageResource(R.drawable.guider_star_light);
    }

}