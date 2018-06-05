package com.jc.flora.apps.component.render.delegate;

/**
 * Created by Shijincheng on 2018/6/5.
 */

public class DebounceDelegate {

    /** 抖动范围时间 */
    private static final long FAST_DOUBLE_TIME = 800;
    /** 上一次点击的时间点 */
    private long mLastClickTime = 0;

    public boolean isFastDoubleClick(){
        if((System.currentTimeMillis() - mLastClickTime) > FAST_DOUBLE_TIME){
            mLastClickTime = System.currentTimeMillis();
            return false;
        }
        return true;
    }

}
