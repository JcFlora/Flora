package com.jc.flora.apps.component.video.touch;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Samurai on 2019/3/25.
 */
public class ContainerTouchHelper {

    private GestureDetector mGestureDetector;
    private BaseGestureCallbackHandler mGestureCallback;

    public ContainerTouchHelper(Context context, BaseGestureCallbackHandler gestureCallback){
        this.mGestureCallback = gestureCallback;
        mGestureDetector = new GestureDetector(context, gestureCallback);
        //取消长按，不然会影响滑动
        mGestureDetector.setIsLongpressEnabled(false);
    }

    public boolean onTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                onEndGesture(event);
                break;
        }
        return mGestureDetector.onTouchEvent(event);
    }

    public void setGestureEnable(boolean enable) {
        this.mGestureCallback.setGestureEnable(enable);
    }

    public void setGestureScrollEnable(boolean enable) {
        this.mGestureCallback.setGestureScrollEnable(enable);
    }

    public void onEndGesture(MotionEvent event) {
        mGestureCallback.onEndGesture(event);
    }

}