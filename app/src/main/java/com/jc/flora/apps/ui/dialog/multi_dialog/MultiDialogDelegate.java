package com.jc.flora.apps.ui.dialog.multi_dialog;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by shijincheng on 2020/9/29.
 */
public class MultiDialogDelegate {

    private static final String TAG = "MultiDialogDelegate";
    /** 是否在push时自动弹窗 */
    private boolean mAutoShowWhenPush = false;
    /** 弹窗队列(线程安全) */
    private ConcurrentLinkedQueue<MultipleShow> mQueue = new ConcurrentLinkedQueue<>();
    /** 当前正在显示的弹窗 */
    private MultipleShow mMultipleShow;

    public void setAutoShowWhenPush(boolean autoShowWhenPush) {
        mAutoShowWhenPush = autoShowWhenPush;
    }

    /**
     * 每次弹窗调用push方法
     *
     * @param multipleShow
     */
    public void push(MultipleShow multipleShow) {
        //添加到队列中
        if (multipleShow != null) {
            multipleShow.setOnDisMissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    nextTask();
                }
            });
            mQueue.add(multipleShow);
            //只有当前队列数量为1时才能进行下一步操作
            if (mAutoShowWhenPush && canShow()) {
                startNextIf();
            }
        }
    }

    /**
     * 开始展示队列里的弹窗
     */
    public void startToShow(){
        startNextIf();
    }

    /**
     * 清除对象
     */
    public void clear() {
        mQueue.clear();
        if(mMultipleShow != null){
            mMultipleShow.dismiss();
            mMultipleShow = null;
        }
    }

    /**
     * 初始为0,push后基数必然为1,所以小于2即可
     *
     * @return
     */
    private boolean canShow() {
        return mQueue.size() == 1;
    }

    /**
     * 显示下一个弹窗任务
     */
    private void startNextIf() {
        if (mQueue.isEmpty()) {
            return;
        }
        mMultipleShow = mQueue.element();
        if (mMultipleShow != null) {
            mMultipleShow.show();
        }
    }

    /**
     * 提供外部下一个任务的方法,在弹窗消失时候调用
     */
    private void nextTask() {
        removeTopTask();
        startNextIf();
    }

    /**
     * 移除队列的头,获取最新队列头
     */
    private void removeTopTask() {
        mQueue.poll();
    }

}