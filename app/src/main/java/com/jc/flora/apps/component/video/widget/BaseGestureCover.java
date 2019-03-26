/*
 * Copyright 2017 jiajunhui<junhui_jia@163.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.jc.flora.apps.component.video.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.jc.flora.apps.component.video.touch.BaseGestureCallbackHandler;
import com.jc.flora.apps.component.video.touch.ContainerTouchHelper;
import com.jc.flora.apps.component.video.touch.OnTouchGestureListener;

/**
 * Created by Samurai on 2019/3/25.
 */
public class BaseGestureCover extends FrameLayout implements OnTouchGestureListener {

    final String TAG = "BaseGestureCover";

    private ContainerTouchHelper mTouchHelper;

    public BaseGestureCover(@NonNull Context context) {
        super(context);
        initGesture(context);
    }

    public BaseGestureCover(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initGesture(context);
    }

    public BaseGestureCover(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGesture(context);
    }

    protected void initGesture(Context context){
        mTouchHelper = new ContainerTouchHelper(context, getGestureCallBackHandler());
        setGestureEnable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mTouchHelper.onTouch(event);
    }

    protected BaseGestureCallbackHandler getGestureCallBackHandler(){
        return new BaseGestureCallbackHandler(this);
    }

    public void setGestureEnable(boolean enable){
        mTouchHelper.setGestureEnable(enable);
    }

    public void setGestureScrollEnable(boolean enable) {
        mTouchHelper.setGestureScrollEnable(enable);
    }

    //----------------------------------dispatch gesture touch event---------------------------------

    @Override
    public void onSingleTapUp(MotionEvent event) {
    }

    @Override
    public void onDoubleTap(MotionEvent event) {
    }

    @Override
    public void onDown(MotionEvent event) {
    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
    }

    @Override
    public void onEndGesture() {
    }
}
