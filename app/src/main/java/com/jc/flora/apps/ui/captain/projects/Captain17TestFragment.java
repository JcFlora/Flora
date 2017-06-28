package com.jc.flora.apps.ui.captain.projects;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

import com.jc.flora.R;
import com.jc.flora.apps.Project;
import com.jc.flora.apps.ProjectsAdapter;
import com.jc.flora.apps.ui.captain.delegate.CaptainFragment;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.ui.reload.widget.AutoSwipeRefreshLayout;
import com.jc.flora.launcher.NotFoundActivity;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/3/6.
 */
public class Captain17TestFragment extends CaptainFragment{

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    /** 下拉刷新布局 */
    private AutoSwipeRefreshLayout mSrlContent;
    private RecyclerView mRvContent;
    private ArrayList<Project> mList = new ArrayList<>();
    private ProjectsAdapter mAdapter;
    private View mView;

    // 控件距离AutoSwipeRefreshLayout底部距离
    private float mViewY;
    // 动画是否在进行
    private boolean mIsAnimate;

    public void setView(View v) {
        mView = v;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_captain17_fragment, container, false);
        mSrlContent = (AutoSwipeRefreshLayout) v.findViewById(R.id.srl_content);
        mRvContent = (RecyclerView) v.findViewById(R.id.rv_content);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initReload();
    }

    private void initViews(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvContent.setLayoutManager(layoutManager);
        mAdapter = new ProjectsAdapter((AppCompatActivity) getActivity(), mList);
        mRvContent.setAdapter(mAdapter);
        mRvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onScrolledVertically(dy);
            }
        });
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
        mSrlContent.setOnRefreshListener(new AutoSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDataAndUi();
            }
        });
        mSrlContent.autoRefresh();
    }

    private void refreshDataAndUi() {
        mSrlContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 关闭下拉刷新的加载动画，防止接口超时时加载动画一直进行
                mSrlContent.setRefreshing(false);
                ToastDelegate.show(getActivity(),"刷新数据");
                setData();
                mAdapter.notifyDataSetChanged();
            }
        },1500);
    }

    private void setData(){
        mList.clear();
        Project p = new Project();
        p.projectName = "测试数据";
        p.targetActivity = NotFoundActivity.class;
        for (int i = 0; i < 20; i++) {
            mList.add(p);
        }
    }

    @Override
    public void onReload(int position) {
        mSrlContent.autoRefresh();
    }

    private void onScrolledVertically(int dy) {
        if(mView == null){
            return;
        }
        if (mView.getVisibility() == View.VISIBLE && mViewY == 0) {
            //获取控件距离父布局（AutoSwipeRefreshLayout）底部距离
            mViewY = mSrlContent.getHeight() - mView.getY();
        }
        if(!mRvContent.canScrollVertically(1)){
            show();
            return;
        }
        //dy大于0是向上滚动 小于0是向下滚动
        if (dy > 1 && !mIsAnimate && mView.getVisibility() == View.VISIBLE) {
            hide();
        } else if (dy < -1 && !mIsAnimate && mView.getVisibility() == View.GONE) {
            show();
        }
    }

    //隐藏时的动画
    private void hide() {
        ViewPropertyAnimator animator = mView.animate().translationY(mViewY).setInterpolator(INTERPOLATOR).setDuration(600);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                mIsAnimate = true;
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                mView.setVisibility(View.GONE);
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
        ViewPropertyAnimator animator = mView.animate().translationY(0).setInterpolator(INTERPOLATOR).setDuration(600);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                mView.setVisibility(View.VISIBLE);
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