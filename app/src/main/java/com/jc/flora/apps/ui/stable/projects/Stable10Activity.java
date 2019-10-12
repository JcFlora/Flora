package com.jc.flora.apps.ui.stable.projects;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2019/9/17.
 */
public class Stable10Activity extends AppCompatActivity {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private Toolbar mToolbar;
    private NestedScrollView mSvContent;
    private View mVBottomBar;

    // 动画移动的距离
    private float mViewY;
    // 动画是否在进行
    private boolean mIsAnimate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stable10);
        findViews();
        initViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
        mSvContent = (NestedScrollView) findViewById(R.id.sv_content);
        mVBottomBar = findViewById(R.id.tv_bottom_bar);
    }

    private void initViews() {
        mToolbar.setTitle("底部浮动浮窗（监听ScrollView滑动+动画）");
        mToolbar.setTitleTextColor(Color.WHITE);
        mSvContent.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                onScrolledVertically(scrollY - oldScrollY);
            }
        });
    }

    private void onScrolledVertically(int dy) {
        if (mVBottomBar.getVisibility() == View.VISIBLE && mViewY == 0) {
            mViewY = ((View)mVBottomBar.getParent()).getHeight();
        }
        //dy大于0是向上滚动 小于0是向下滚动
        if (dy > 1 && !mIsAnimate && mVBottomBar.getVisibility() == View.VISIBLE) {
            hide();
        } else if (dy < -1 && !mIsAnimate && mVBottomBar.getVisibility() == View.GONE) {
            show();
        }
    }

    //隐藏时的动画
    private void hide() {
        ViewPropertyAnimator animator = mVBottomBar.animate().translationY(mViewY).setInterpolator(INTERPOLATOR).setDuration(300);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                mIsAnimate = true;
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                mVBottomBar.setVisibility(View.GONE);
                mIsAnimate = false;
            }
            @Override
            public void onAnimationCancel(Animator animator) {
                show();
            }
        });
        animator.start();
    }

    //显示时的动画
    private void show() {
        ViewPropertyAnimator animator = mVBottomBar.animate().translationY(0).setInterpolator(INTERPOLATOR).setDuration(300);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                mVBottomBar.setVisibility(View.VISIBLE);
                mIsAnimate = true;
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                mIsAnimate = false;
            }
            @Override
            public void onAnimationCancel(Animator animator) {
                hide();
            }
        });
        animator.start();
    }

    private void onScrolledVertically2(int dy) {
        boolean mIsHideAfterScroll = false;
        if(mVBottomBar.getTag() != null){
            mIsHideAfterScroll = (boolean)mVBottomBar.getTag();
        }
        //上滑 并且 正在显示播放条
        if (dy > 0 && !mIsHideAfterScroll) {
            //将Y属性变为播放条容器高度 (相当于隐藏了)
            mVBottomBar.animate().translationY(((View)mVBottomBar.getParent()).getHeight());
            mVBottomBar.setTag(true);
        } else if (dy < 0 && mIsHideAfterScroll) {
            //将Y属性变为0  (相当于显示了)
            mVBottomBar.animate().translationY(0);
            mVBottomBar.setTag(false);
        }
    }

}