package com.jc.flora.apps.scene.splash.delegate;

import java.util.Random;

/**
 * Created by shijincheng on 2017/1/23.
 */
public class TestTaskDelegate {

    private long mDuration;

    /**
     * 模拟异步请求任务
     * @param callback 数据响应回调
     */
    public void startTask(final OnTaskCompletedCallback callback) {
        final long startTime = System.currentTimeMillis();
        new Thread() {
            @Override
            public void run() {
                // 模拟耗时
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                // 计算请求时长
                mDuration = System.currentTimeMillis() - startTime;
                if (callback != null) {
                    // 模拟随机返回数据
                    callback.onResponse(new Random().nextInt(2));
                }
            }
        }.start();
    }

    public long getDuration() {
        return mDuration;
    }

    public interface OnTaskCompletedCallback {
        void onResponse(int data);
    }

}
