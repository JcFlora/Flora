package com.jc.flora.apps.md.toolbar.delegate;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Samurai on 2017/6/19.
 */
public class ToolbarDelegate3 {

    private Activity mActivity;
    private Toolbar mToolbar;
    private RelativeLayout mLayoutTitle;
    private TextView mTvCenterTitle;
    private ImageView mBtnLeft;
    private ImageView mBtnRight;

    public ToolbarDelegate3(Activity activity) {
        mActivity = activity;
    }

    public void removeActionBar() {
        if (mActivity == null) {
            return;
        }
        if (mActivity instanceof AppCompatActivity) {
            ((AppCompatActivity) mActivity).supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        } else {
            mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    public void setToolbar(Toolbar toolbar) {
        mToolbar = toolbar;
        initToolbar();
        createTitleLayout();
    }

    public void setCenterTitle(CharSequence text, int color, float textSize) {
        if (mTvCenterTitle == null) {
            createCenterTitle();
        }
        if (!TextUtils.isEmpty(text)) {
            mTvCenterTitle.setText(text);
        }
        mTvCenterTitle.setTextColor(color);
        if(textSize > 0){
            mTvCenterTitle.setTextSize(textSize);
        }
    }

    public void setLeftButton(int resId, View.OnClickListener l) {
        if (resId <= 0) {
            return;
        }
        if(mBtnLeft == null){
            createLeftButton();
        }
        mBtnLeft.setImageResource(resId);
        if (l == null) {
            return;
        }
        mBtnLeft.setOnClickListener(l);
    }

    public void setRightButton(int resId, View.OnClickListener l) {
        if (resId <= 0) {
            return;
        }
        if(mBtnRight == null){
            createRightButton();
        }
        mBtnRight.setImageResource(resId);
        if (l == null) {
            return;
        }
        mBtnRight.setOnClickListener(l);
    }

    private void initToolbar(){
        mToolbar.setTitle("");
        mToolbar.setContentInsetsAbsolute(0, 0);
    }

    private void createTitleLayout(){
        mLayoutTitle = new RelativeLayout(mActivity);
        int matchParent = Toolbar.LayoutParams.MATCH_PARENT;
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(matchParent, matchParent);
        mToolbar.addView(mLayoutTitle, params);
    }

    private void createCenterTitle() {
        mTvCenterTitle = new TextView(mActivity);
        mTvCenterTitle.setGravity(Gravity.CENTER);
        int matchParent = Toolbar.LayoutParams.MATCH_PARENT;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(matchParent, matchParent);
        mLayoutTitle.addView(mTvCenterTitle, params);
    }

    private void createLeftButton() {
        mBtnLeft = new ImageView(mActivity);
        mBtnLeft.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        // todo 替换成toolbar的图标设计规范
        mBtnLeft.setPadding(48, 0, 48, 0);
        int matchParent = Toolbar.LayoutParams.MATCH_PARENT;
        int wrapContent = Toolbar.LayoutParams.WRAP_CONTENT;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(wrapContent, matchParent);
        mLayoutTitle.addView(mBtnLeft, params);
    }

    private void createRightButton() {
        mBtnRight = new ImageView(mActivity);
        mBtnRight.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        // todo 替换成toolbar的图标设计规范
        mBtnRight.setPadding(48, 0, 48, 0);
        int matchParent = Toolbar.LayoutParams.MATCH_PARENT;
        int wrapContent = Toolbar.LayoutParams.WRAP_CONTENT;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(wrapContent, matchParent);
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        mLayoutTitle.addView(mBtnRight, params);
    }

}