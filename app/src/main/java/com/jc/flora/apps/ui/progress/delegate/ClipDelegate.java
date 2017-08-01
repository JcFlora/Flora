package com.jc.flora.apps.ui.progress.delegate;

import android.app.Activity;
import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Samurai on 2017/7/31.
 */
public class ClipDelegate {

    private Activity mActivity;
    private ClipDrawable mClipDrawable;
    private boolean mIsLoading;

    public ClipDelegate(Activity activity) {
        mActivity = activity;
    }

    public void createClipDrawable(ImageView ivClip){
        mClipDrawable = new ClipDrawable(mActivity.getResources().getDrawable(R.drawable.clip_src),
                Gravity.BOTTOM, ClipDrawable.VERTICAL);
        mClipDrawable.setLevel(0);
        ivClip.setImageDrawable(mClipDrawable);
        ivClip.setBackgroundResource(R.drawable.clip_bg);
    }

    public void start() {
        if(mIsLoading){
            return;
        }
        mIsLoading = true;
        mClipThread.start();
    }

    public void stop() {
        if(mIsLoading){
            mIsLoading = false;
        }
    }

    public void destroy() {
        mHandler.removeCallbacksAndMessages(null);
    }

    private Thread mClipThread = new Thread() {
        @Override
        public void run() {
            super.run();
            int i = 0;
            while (mIsLoading && !(mActivity.isFinishing()||mActivity.isDestroyed()) && i <= 10000) {
                Message msg = mHandler.obtainMessage();
                msg.arg1 = i;
                i += 200;
                mHandler.sendMessage(msg);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                }
            }
        }
    };

    private Handler mHandler= new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mClipDrawable.setLevel(msg.arg1);
            if (msg.arg1 >= 10000) {
                mIsLoading = false;
                ToastDelegate.show(mActivity,"加载完毕");
            }
        }
    };

}