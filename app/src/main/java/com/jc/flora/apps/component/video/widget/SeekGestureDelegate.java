package com.jc.flora.apps.component.video.widget;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Shijincheng on 2019/3/26.
 */

public class SeekGestureDelegate {

    //进度条下面的当前进度文字，将毫秒化为mm:ss格式
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("mm:ss", Locale.getDefault());

    private View mLayoutSeekBox;
    private TextView mTvStepTime;
    private TextView mTvProgressTime;

    private int mDuration = 0;
    private int mCurrentPosition = -1;
    private int mSeekProgress = -1;
    private long mNewPosition;

    private boolean mHorizontalMove;

    private OnSeekGestureListener mOnSeekGestureListener;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public void setLayoutSeekBox(View layoutSeekBox) {
        mLayoutSeekBox = layoutSeekBox;
    }

    public void setTvStepTime(TextView tvStepTime) {
        mTvStepTime = tvStepTime;
    }

    public void setTvProgressTime(TextView tvProgressTime) {
        mTvProgressTime = tvProgressTime;
    }

    public void setDuration(int duration) {
        if(!mHorizontalMove){
            mDuration = duration;
        }
    }

    public void setCurrentPosition(int currentPosition) {
        if(!mHorizontalMove){
            mCurrentPosition = currentPosition;
        }
    }

    public void setHorizontalMove(boolean horizontalMove) {
        mHorizontalMove = horizontalMove;
    }

    public void setOnSeekGestureListener(OnSeekGestureListener l) {
        mOnSeekGestureListener = l;
    }

    public void setFastForwardState(boolean state) {
        mLayoutSeekBox.setVisibility(state? View.VISIBLE: View.GONE);
    }

    public void onHorizontalSlide(float percent){
        if(mDuration <= 0){
            return;
        }
        mHorizontalMove = true;
        long position = mCurrentPosition;
        long duration = mDuration;
        long deltaMax = Math.min(mDuration/2, duration - position);
        long delta = (long) (deltaMax * percent);
        mNewPosition = delta + position;
        if (mNewPosition > duration) {
            mNewPosition = duration;
        } else if (mNewPosition <= 0) {
            mNewPosition = 0;
            delta=-position;
        }
        int showDelta = (int) delta / 1000;
        if (showDelta != 0) {
            setFastForwardState(true);
            String text = showDelta > 0 ? ("+" + showDelta) : "" + showDelta;
            mTvStepTime.setText(text + "s");
            String progressText = FORMAT.format(mNewPosition)+"/" + FORMAT.format(duration);
            mTvProgressTime.setText(progressText);
        }
    }

    public void onEndGesture() {
        setFastForwardState(false);
        if(mNewPosition >= 0 && mHorizontalMove){
            sendSeekEvent((int) mNewPosition);
            mNewPosition = 0;
        }
        mHorizontalMove = false;
    }

    private void sendSeekEvent(int progress){
        mSeekProgress = progress;
        mHandler.removeCallbacks(mSeekEventRunnable);
        mHandler.postDelayed(mSeekEventRunnable, 100);
    }

    private Runnable mSeekEventRunnable = new Runnable() {
        @Override
        public void run() {
            if(mSeekProgress >= 0 && mOnSeekGestureListener != null){
                mOnSeekGestureListener.onSeekGestureEnd(mSeekProgress);
            }
        }
    };

    public interface OnSeekGestureListener{
        void onSeekGestureEnd(int progress);
    }

}