package com.jc.flora.apps.component.exit.delegate;

/**
 * Created by Shijincheng on 2018/6/5.
 */

public class DoubleClickDelegate {

    /** 确认退出等待时间 */
    private static final long EXIT_WAIT_TIME = 2000;
    /** 第一次按返回的时间点 */
    private long mExitTime = 0;

    public boolean isDoubleClick(){
        if((System.currentTimeMillis() - mExitTime) > EXIT_WAIT_TIME){
            mExitTime = System.currentTimeMillis();
            return false;
        }
        return true;
    }

}
