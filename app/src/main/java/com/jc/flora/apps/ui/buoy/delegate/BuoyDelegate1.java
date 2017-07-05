package com.jc.flora.apps.ui.buoy.delegate;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Samurai on 2017/7/4.
 */
public class BuoyDelegate1 {

    public static void setFollowing(View v, final View.OnClickListener listener) {
        if (v == null) {
            return;
        }
        v.setOnTouchListener(new View.OnTouchListener() {
            private int mLastX, mLastY, mDownX, mDownY, mUpX, mUpY;
            private long mDownTimeMillion, mUpTimeMillion;
            int l, t, r, b;
            private int mWidth, mHeight;
            private int mScreenWidth, mScreenHeight, mDisplayHeight;

            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                if (mScreenWidth == 0) {
                    DisplayMetrics dm = v.getContext().getResources()
                            .getDisplayMetrics();
                    mScreenWidth = dm.widthPixels;
                    mScreenHeight = dm.heightPixels;
                }
                if (mDisplayHeight == 0) {
                    Rect frame = new Rect();
                    ((View) v.getParent()).getGlobalVisibleRect(frame);
                    mDisplayHeight = mScreenHeight - frame.top;
                }
                if (mWidth == 0) {
                    mWidth = v.getWidth();
                }
                if (mHeight == 0) {
                    mHeight = v.getHeight();
                }
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        mLastX = rawX;
                        mLastY = rawY;
                        mDownX = (int) event.getRawX();
                        mDownY = (int) event.getRawY();
                        mDownTimeMillion = System.currentTimeMillis();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        int offsetX = rawX - mLastX;
                        int offsetY = rawY - mLastY;
                        l = v.getLeft() + offsetX;
                        t = v.getTop() + offsetY;
                        r = v.getRight() + offsetX;
                        b = v.getBottom() + offsetY;
                        if (l < 0) {
                            l = 0;
                            r = mWidth;
                        }
                        if (r > mScreenWidth) {
                            r = mScreenWidth;
                            l = mScreenWidth - mWidth;
                        }
                        if (t < 0) {
                            t = 0;
                            b = mHeight;
                        }
                        if (b > mDisplayHeight) {
                            b = mDisplayHeight;
                            t = mDisplayHeight - mHeight;
                        }
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                        params.leftMargin = l;
                        params.topMargin = t;
                        v.setLayoutParams(params);
                        mLastX = rawX;
                        mLastY = rawY;
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        mUpX = (int) event.getRawX();
                        mUpY = (int) event.getRawY();
                        mUpTimeMillion = System.currentTimeMillis();
                        if (listener != null && mUpTimeMillion - mDownTimeMillion < 1000
                                && Math.abs(mDownX - mUpX) < 3 && Math.abs(mDownY - mUpY) < 3) {
                            listener.onClick(v);
                        }
                        break;
                }
                return true;
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

}