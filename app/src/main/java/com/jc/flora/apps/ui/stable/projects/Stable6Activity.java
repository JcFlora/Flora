package com.jc.flora.apps.ui.stable.projects;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

import com.jc.flora.R;
import com.jc.flora.apps.Project;
import com.jc.flora.apps.ProjectsAdapter;
import com.jc.flora.launcher.NotFoundActivity;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/3/6.
 */
public class Stable6Activity extends AppCompatActivity {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private Toolbar mToolbar;
    private RecyclerView mRvContent;
    private LinearLayoutManager mLayoutManager;

    // 控件距离AutoSwipeRefreshLayout底部距离
    private float mViewY;
    // 动画是否在进行
    private boolean mIsAnimate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stable5);
        findViews();
        initViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
        mRvContent = (RecyclerView) findViewById(R.id.rv_content);
    }

    private void initViews() {
        mToolbar.setTitle("浮动标题（监听滑动+动画）");
        mToolbar.setTitleTextColor(Color.WHITE);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvContent.setLayoutManager(mLayoutManager);
        ProjectsAdapter mAdapter = new ProjectsAdapter(this, getData());
        mRvContent.setAdapter(mAdapter);
        mRvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onScrolledVertically(dy);
            }
        });
    }

    private ArrayList<Project> getData(){
        ArrayList<Project> list = new ArrayList<>();
        Project p = new Project();
        p.projectName = "测试数据";
        p.targetActivity = NotFoundActivity.class;
        for (int i = 0; i < 20; i++) {
            list.add(p);
        }
        return list;
    }

    private void onScrolledVertically(int dy) {
        if (mToolbar.getVisibility() == View.VISIBLE && mViewY == 0) {
            mViewY = mToolbar.getHeight();
        }
        if(!mRvContent.canScrollVertically(1)){
            show();
            return;
        }
        //dy大于0是向上滚动 小于0是向下滚动
        if (dy > 1 && getScrollYDistance() > 200 && !mIsAnimate && mToolbar.getVisibility() == View.VISIBLE) {
            hide();
        } else if (dy < -1 && !mIsAnimate && mToolbar.getVisibility() == View.GONE) {
            show();
        }
    }

    public int getScrollYDistance() {
        int position = mLayoutManager.findFirstVisibleItemPosition();
        View firstVisibleChildView = mLayoutManager.findViewByPosition(position);
        int itemHeight = firstVisibleChildView.getHeight();
        return (position) * itemHeight - firstVisibleChildView.getTop();
    }

    //隐藏时的动画
    private void hide() {
        ViewPropertyAnimator animator = mToolbar.animate().translationY(-mViewY).setInterpolator(INTERPOLATOR).setDuration(600);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                mIsAnimate = true;
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                mToolbar.setVisibility(View.GONE);
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
        ViewPropertyAnimator animator = mToolbar.animate().translationY(0).setInterpolator(INTERPOLATOR).setDuration(600);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                mToolbar.setVisibility(View.VISIBLE);
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

}