package com.jc.flora.apps.ui.bgchange.delegate;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.jc.flora.R;

import java.util.Random;

/**
 * 全页面背景切换业务管理：
 * Created by shijincheng on 2017/2/4.
 */
public class BgChange2Delegate {

    /** 切换背景数量 */
    private static final int BG_COUNT = 12;
    /** 图片资源 */
    private static final int[] BG_RES_IDS = new int[]{
            R.drawable.bg_01, R.drawable.bg_02, R.drawable.bg_03,
            R.drawable.bg_04, R.drawable.bg_05, R.drawable.bg_06,
            R.drawable.bg_07, R.drawable.bg_08, R.drawable.bg_09,
            R.drawable.bg_10, R.drawable.bg_11, R.drawable.bg_12};

    /** 背景切换消息标识 */
    private static final int MSG_CHANGE_BG = 0X001;
    /** 背景切换周期 */
    private static final int TIME_INTERVAL = 1000 * 3;
    /** 背景切换渐变过渡效果时长 */
    private static final int TRANSITION_DURATION_MILLIS = 300;

    /** 根视图 */
    private View mContentView;
    /** 当前背景资源索引 */
    private int mCurrentBgIndex = 0;

    private static BgChange2Delegate sInstance = null;

    public static BgChange2Delegate getInstance() {
        if (sInstance == null) {
            sInstance = new BgChange2Delegate();
        }
        return sInstance;
    }

    private BgChange2Delegate() {
        // 启动切换背景的循环任务
        mHandler.sendEmptyMessageDelayed(MSG_CHANGE_BG, TIME_INTERVAL);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CHANGE_BG:
                    // 切换背景，自保持
                    loopBgChange();
            }
        }
    };

    /** 切换背景，自保持 */
    private void loopBgChange() {
        // 随机切换背景，并且确保和上次不同
        if (mContentView != null) {
            // 改变背景资源索引
            changeBgIndex();
            showBgInTransitionMode();
        }
        // 自保持，继续下一次
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(MSG_CHANGE_BG, TIME_INTERVAL);
        }
    }

    /** 改变背景资源索引 */
    private void changeBgIndex(){
        int index = new Random().nextInt(BG_COUNT);
        while (index == mCurrentBgIndex){
            index = new Random().nextInt(BG_COUNT);
        }
        mCurrentBgIndex = index;
    }

    /** 以渐变过渡效果显示新背景 */
    private void showBgInTransitionMode(){
        Drawable oldDrawable = mContentView.getBackground();
        if (oldDrawable == null) {
            oldDrawable = new ColorDrawable(Color.TRANSPARENT);
        } else if (oldDrawable instanceof TransitionDrawable) {
            oldDrawable = ((TransitionDrawable) oldDrawable).getDrawable(1);
        }
        Drawable newDrawable = mContentView.getResources().getDrawable(BG_RES_IDS[mCurrentBgIndex]);
        TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldDrawable, newDrawable});
        mContentView.setBackground(td);
        td.startTransition(TRANSITION_DURATION_MILLIS);
    }

    /**
     * 同步Activity的onResume
     * @param contentView
     */
    public void onResume(View contentView){
        mContentView = contentView;
        mContentView.setBackgroundResource(BG_RES_IDS[mCurrentBgIndex]);
    }

    /** 同步Activity的onPause */
    public void onPause(){
        mContentView = null;
    }

    /** 退出整个任务时，进行销毁工作 */
    public void destroy() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        sInstance = null;
    }

}
