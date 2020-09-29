package com.jc.flora.apps.scene.pray.elete;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * Created by Samurai on 2020/9/18.
 */
public class PrayCmdExecutor extends Fragment {

    /**
     * 置灰状态数据源
     */
    private static PrayInfoDataSource sPrayInfoDataSource;

    private AppCompatActivity mActivity;
    private Fragment mFragment;
    private ArrayList<View> mPrayViews = new ArrayList<>();
    private Paint mPrayPaint;

    /**
     * 设置置灰状态信息读写的数据源
     * @param prayInfoDataSource
     */
    static void setPrayInfoDataSource(PrayInfoDataSource prayInfoDataSource) {
        sPrayInfoDataSource = prayInfoDataSource;
    }

    /**
     * 全局置灰调用此方法
     *
     * @param activity
     */
    static void pray(Activity activity) {
        // 发送本地广播，通知所有页面置灰
        sendPrayBroadcast(activity, true);
    }

    /**
     * 全局还原调用此方法
     *
     * @param activity
     */
    static void clear(Activity activity) {
        // 发送本地广播，通知所有页面还原
        sendPrayBroadcast(activity, false);
    }

    /**
     * 发送置灰命令的本地广播
     * @param context
     * @param isPrayOn
     */
    private static void sendPrayBroadcast(Context context, boolean isPrayOn) {
        Intent intent = new Intent(PRAY_CMD_FEEDBACK);
        intent.putExtra(PRAY_ON, isPrayOn);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * 创建置灰执行器
     * @param activity
     * @param tag
     * @return
     */
    static PrayCmdExecutor createExecutor(AppCompatActivity activity, String tag){
        PrayCmdExecutor executor = (PrayCmdExecutor) activity.getSupportFragmentManager().findFragmentByTag(tag);
        if(executor == null){
            executor = new PrayCmdExecutor();
            executor.addToActivity(activity, tag);
        }
        return executor;
    }

    /**
     * 创建置灰执行器
     * @param fragment
     * @param tag
     * @return
     */
    static PrayCmdExecutor createExecutor(Fragment fragment, String tag){
        PrayCmdExecutor executor = (PrayCmdExecutor) fragment.getChildFragmentManager().findFragmentByTag(tag);
        if(executor == null){
            executor = new PrayCmdExecutor();
            executor.addToFragment(fragment, tag);
        }
        return executor;
    }

    protected PrayCmdExecutor() {
        initPrayPaint();
    }

    private void initPrayPaint(){
        mPrayPaint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        mPrayPaint.setColorFilter(new ColorMatrixColorFilter(cm));
    }

    /**
     * 添加到Activity
     *
     * @param activity
     * @param tag
     */
    void addToActivity(AppCompatActivity activity, String tag) {
        if (activity != null) {
            mActivity = activity;
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitNowAllowingStateLoss();
        }
    }

    /**
     * 添加到Fragment
     *
     * @param fragment
     * @param tag
     */
    void addToFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            mFragment = fragment;
            fragment.getChildFragmentManager().beginTransaction().add(this, tag).commitNowAllowingStateLoss();
        }
    }

    public void registerCurrentActivity(){
        if(mActivity != null){
            mPrayViews.add(mActivity.getWindow().getDecorView());
        }
    }

    public void registerCurrentFragment(){
        if(mFragment != null){
            mPrayViews.add(mFragment.getView());
        }
    }

    public void registerDialogContentView(View v){
        registerView(v);
    }

    public void registerView(View v){
        if(v != null){
            mPrayViews.add(v);
        }
    }

    public void syncPrayStatus(){
        if(sPrayInfoDataSource == null){
            return;
        }
        if(sPrayInfoDataSource.readPrayStatus()){
            pray();
        }else{
            clear();
        }
    }

    /** 置灰命令广播的Action */
    private static final String PRAY_CMD_FEEDBACK = "PRAY_CMD_FEEDBACK";
    /** 置灰命令广播的携带参数 */
    private static final String PRAY_ON = "prayOn";
    /** 置灰命令广播的IntentFilter */
    private IntentFilter mIntentFilter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerPrayReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterPrayReceiver();
    }

    /**
     * 注册置灰命令监听的接收器
     */
    private void registerPrayReceiver() {
        if (mPrayReceiver == null) {
            return;
        }
        if (mIntentFilter == null) {
            mIntentFilter = new IntentFilter();
            mIntentFilter.addAction(PRAY_CMD_FEEDBACK);
        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mPrayReceiver, mIntentFilter);
    }

    /**
     * 反注册置灰命令监听的接收器
     */
    private void unregisterPrayReceiver() {
        if (mPrayReceiver != null) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mPrayReceiver);
        }
    }

    /**
     * 置灰命令监听的接收器
     */
    private BroadcastReceiver mPrayReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && PRAY_CMD_FEEDBACK.equals(intent.getAction())) {
                boolean isPray = intent.getBooleanExtra(PRAY_ON, false);
                if (isPray) {
                    pray();
                } else {
                    clear();
                }
            }
        }
    };

    private void pray(){
        for (View prayView : mPrayViews) {
            prayView.setLayerType(View.LAYER_TYPE_HARDWARE, mPrayPaint);
        }
        if(sPrayInfoDataSource != null){
            sPrayInfoDataSource.writePrayStatus(true);
        }
    }

    private void clear(){
        for (View prayView : mPrayViews) {
            prayView.setLayerType(View.LAYER_TYPE_NONE, null);
        }
        if(sPrayInfoDataSource != null){
            sPrayInfoDataSource.writePrayStatus(false);
        }
    }

}