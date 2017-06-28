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
    /** 适配执行器 */
    private ViExecutor mExecutor;
    /** 数据投放器 */
    private ViDataDelivery mDataDelivery;

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
    public void setWidthHeightByScale(View v, double hifiWidthScale,
                                      double hifiHeightScale) {
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
     * 获取适配后的尺寸，单位px
     *
     * @param hifiDimen 高保真中给出的尺寸
     * @return 适配后的尺寸
     */
    public double getViDimen(double hifiDimen) {
        return mExecutor.getViDimen(hifiDimen);
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
