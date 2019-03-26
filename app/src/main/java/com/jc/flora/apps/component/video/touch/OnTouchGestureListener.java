package com.jc.flora.apps.component.video.touch;

import android.view.MotionEvent;

/**
 * Created by Samurai on 2019/3/25.
 */
public interface OnTouchGestureListener {
    /**
     * on gesture single tap up
     * @param event
     */
    void onSingleTapUp(MotionEvent event);

    /**
     * on gesture double tap
     * @param event
     */
    void onDoubleTap(MotionEvent event);

    void onDown(MotionEvent event);

    /**
     * on scroll
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     */
    void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);

    void onEndGesture();
}
