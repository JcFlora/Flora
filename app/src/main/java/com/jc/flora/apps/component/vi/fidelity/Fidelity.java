package com.jc.flora.apps.component.vi.fidelity;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * 高保真组件，单例外观类，对外提供简单Api
 */
public final class Fidelity {

    /** 高保真组件 */
    private static volatile Fidelity sWidthScaleInstance;
    /** 高保真组件 */
    private static volatile Fidelity sHeightScaleInstance;
    /** 高保真组件守护器，在系统配置发生变化时，重启高保真组件 */
    private static ViDaemon sDaemon;
    /** 适配执行器 */
    private ViExecutor mExecutor;
    /** 数据投放器 */
    private ViDataDelivery mDataDelivery;

    /**
     * 初始化，建议在Application的onCreate()方法中调用
     * @param context 上下文
     */
    public static void init(Context context){
        sWidthScaleInstance = null;
        sHeightScaleInstance = null;
        if(sDaemon == null){
            sDaemon = new ViDaemon(context);
            sDaemon.registerReceiver();
        }
    }

    /**
     * 初始化，建议在Application的onCreate()方法中调用
     * @param context 上下文
     * @param hifiWidth 高保真宽度
     * @param hifiHeight 高保真高度
     */
    public static void init(Context context, int hifiWidth, int hifiHeight){
        init(context);
        ViDataDelivery.initStatic(hifiWidth,hifiHeight);
    }

    /**
     * 释放资源，建议在Application的onDestroy()方法中调用
     */
    public static void destroy(){
        if(sDaemon != null){
            sDaemon.unregisterReceiver();
            sDaemon = null;
        }
        sWidthScaleInstance = null;
        sHeightScaleInstance = null;
    }

    /**
     * 获取高保真组件
     *
     * @param context 当前上下文
     * @return 高保真组件
     */
    public static Fidelity getInstance(Context context) {
        return getWidthScaleInstance(context);
    }

    /**
     * 获取高保真组件
     *
     * @param context 当前上下文
     * @return 高保真组件
     */
    public static Fidelity getWidthScaleInstance(Context context) {
        if (sWidthScaleInstance == null) {
            synchronized (Fidelity.class) {
                if (sWidthScaleInstance == null) {
                    sWidthScaleInstance = new Fidelity(context.getApplicationContext(), false);
                }
            }
        }
        return sWidthScaleInstance;
    }

    /**
     * 获取高保真组件
     *
     * @param context 当前上下文
     * @return 高保真组件
     */
    public static Fidelity getHeightScaleInstance(Context context) {
        if (sHeightScaleInstance == null) {
            synchronized (Fidelity.class) {
                if (sHeightScaleInstance == null) {
                    sHeightScaleInstance = new Fidelity(context.getApplicationContext(), true);
                }
            }
        }
        return sHeightScaleInstance;
    }

    /**
     * 高保真组件，单例外观类，对外提供简单Api
     *
     * @param context 当前上下文
     */
    private Fidelity(Context context, boolean isHeightScaleMode) {
        mDataDelivery = new ViDataDelivery(context);
        mExecutor = new ViExecutor(mDataDelivery, isHeightScaleMode);
    }

    /**
     * 设置宽度
     *
     * @param v         适配控件
     * @param hifiWidth 高保真宽度
     */
    public void setWidth(View v, double hifiWidth) {
        mExecutor.setWidth(v, hifiWidth);
    }

    /**
     * 设置高度
     *
     * @param v          适配控件
     * @param hifiHeight 高保真高度
     */
    public void setHeight(View v, double hifiHeight) {
        mExecutor.setHeight(v, hifiHeight);
    }

    /**
     * 设置宽度高度
     *
     * @param v          适配控件
     * @param hifiWidth  高保真宽度
     * @param hifiHeight 高保真高度
     */
    public void setWidthHeight(View v, double hifiWidth, double hifiHeight) {
        mExecutor.setWidthHeight(v, hifiWidth, hifiHeight);
    }

    /**
     * 按比例设置宽度
     *
     * @param v              适配控件
     * @param hifiWidthScale 高保真宽度比例
     */
    public void setWidthByScale(View v, double hifiWidthScale) {
        mExecutor.setWidthByScale(v, hifiWidthScale);
    }

    /**
     * 按比例设置高度
     *
     * @param v               适配控件
     * @param hifiHeightScale 高保真高度比例
     */
    public void setHeightByScale(View v, double hifiHeightScale) {
        mExecutor.setHeightByScale(v, hifiHeightScale);
    }

    /**
     * 按比例设置高度高度
     *
     * @param v               适配控件
     * @param hifiWidthScale  高保真宽度比例
     * @param hifiHeightScale 高保真高度比例
     */
    public void setWidthHeightByScale(View v, double hifiWidthScale, double hifiHeightScale) {
        mExecutor.setWidthHeightByScale(v, hifiWidthScale, hifiHeightScale);
    }

    /**
     * 动态按比例设置高度（不推荐使用）
     *
     * @param v                    适配控件
     * @param hifiHeightWidthRatio 高保真宽度高度比例
     */
    @Deprecated
    public void setHeightOnLayouted(View v, double hifiHeightWidthRatio) {
        mExecutor.setHeightOnLayouted(v, hifiHeightWidthRatio);
    }

    /**
     * 设置字体大小
     *
     * @param tv           适配控件
     * @param hifiTextSize 高保真字体大小
     */
    public void setTextSize(TextView tv, int hifiTextSize) {
        mExecutor.setTextSize(tv, hifiTextSize);
    }

    /**
     * hifi转换为px
     *
     * @param hifiValue 高保真中给出的尺寸
     * @return 适配后的尺寸
     */
    public double hifi2px(double hifiValue) {
        return mExecutor.hifi2px(hifiValue);
    }

    /**
     * 获取实际屏幕宽度
     *
     * @return 实际屏幕宽度
     */
    public int getScreenWidth() {
        return mDataDelivery.mScreenWidth;
    }

    /**
     * 获取实际屏幕高度
     *
     * @return 实际屏幕高度
     */
    public int getScreenHeight() {
        return mDataDelivery.mScreenHeight;
    }

    /**
     * dp转换为px
     *
     * @param dpValue dp值
     * @return px值
     */
    public double dp2px(double dpValue) {
        return mExecutor.dp2px(dpValue);
    }

}
