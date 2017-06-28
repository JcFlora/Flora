package com.jc.flora.apps.ui.bgchange.delegate;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.jc.flora.R;

import java.util.Random;

/**
 * 全页面背景切换业务管理
 * Created by shijincheng on 2017/2/4.
 */
public class BgChange1Delegate {

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

    /** 根视图 */
    private View mContentView;
    /** 当前背景资源索引 */
    private int mCurrentBgIndex = 0;

    private static BgChange1Delegate sInstance = null;

    public static BgChange1Delegate getInstance() {
        if (sInstance == null) {
            sInstance = new BgChange1Delegate();
        }
        return sInstance;
    }

    private BgChange1Delegate() {
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
        // 随机切换背景
        if (mContentView != null) {
            mCurrentBgIndex = new Random().nextInt(12);
            mContentView.setBackgroundResource(BG_RES_IDS[mCurrentBgIndex]);
        }
        // 自保持，继续下一次
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(MSG_CHANGE_BG, TIME_INTERVAL);
        }
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
