package com.jc.flora.apps.component.video.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Samurai on 2019/3/25.
 */
public class BaseVideoGestureCover extends BaseGestureCover{

    public BaseVideoGestureCover(@NonNull Context context) {
        super(context);
    }

    public BaseVideoGestureCover(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseVideoGestureCover(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDuration(int duration) {
    }

    public void setCurrentPosition(int currentPosition) {
    }

    public void setOnSingleTapUpListener(OnSingleTapUpListener l) {
    }

    public void setOnSeekGestureListener(SeekGestureDelegate.OnSeekGestureListener l) {
    }

    public interface OnSingleTapUpListener{
        void onSingleTapUp();
    }

}