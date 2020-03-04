package com.jc.flora.apps.component.video.delegate;

import android.view.View;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.component.video.widget.BaseVideoGestureCover;

/**
 * Created by Shijincheng on 2020/3/3.
 */

public class VideoFullTouchLockDelegate {

    // 锁定/解锁按钮
    private ImageView mBtnLock;
    // 被锁定的控件集合
    private View[] mLockedWidgets;
    // 手势控制浮层
    private BaseVideoGestureCover mGestureCover;
    // 锁定状态发生变化的监听
    private OnLockChangedListener mOnLockChangedListener;

    private boolean mIsLocked = false;

    public void setBtnLock(ImageView btnLock) {
        mBtnLock = btnLock;
        mBtnLock.setVisibility(View.GONE);
    }

    public void setLockedWidgets(View... lockedWidgets){
        mLockedWidgets = lockedWidgets;
    }

    public void setGestureCover(BaseVideoGestureCover gestureCover) {
        mGestureCover = gestureCover;
    }

    public void setOnLockChangedListener(OnLockChangedListener onLockChangedListener) {
        mOnLockChangedListener = onLockChangedListener;
    }

    /**
     * 初始化
     */
    public void init() {
        mBtnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsLocked) {
                    unlock();
                } else {
                    lock();
                }
                if (mGestureCover != null) {
                    mGestureCover.setGestureEnable(!mIsLocked);
                }
                if (mOnLockChangedListener != null) {
                    mOnLockChangedListener.onLockChanged(mIsLocked);
                }
            }
        });
    }

    /**
     * 设定是否可用，只能在全屏状态下使用，半屏状态下禁用，在横竖屏切换时调用
     * @param lockEnable
     */
    public void setLockEnable(boolean lockEnable){
        mBtnLock.setVisibility(lockEnable ? View.VISIBLE : View.GONE);
    }

    /**
     * 还原，恢复到初始状态，在横竖屏切换时调用
     */
    public void reset(){
        unlock();
    }

    /**
     * 锁定操作
     */
    private void lock(){
        mIsLocked = true;
        mBtnLock.setImageResource(R.drawable.video_unlock);
        if(mLockedWidgets == null){
            return;
        }
        for (View lockedWidget : mLockedWidgets) {
            lockedWidget.setVisibility(View.GONE);
        }
    }

    /**
     * 解锁操作
     */
    private void unlock(){
        mIsLocked = false;
        mBtnLock.setImageResource(R.drawable.video_lock);
        if(mLockedWidgets == null){
            return;
        }
        for (View lockedWidget : mLockedWidgets) {
            lockedWidget.setVisibility(View.VISIBLE);
        }
    }

    public interface OnLockChangedListener{
        void onLockChanged(boolean isLocked);
    }

}