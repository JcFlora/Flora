package com.jc.flora.apps.component.vi.fidelity;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Vi数据投放器
 */
public final class ViDataDelivery {

    /** 实际屏幕宽度 */
    protected int mScreenWidth;
    /** 实际屏幕高度 */
    protected int mScreenHeight;
    /** 实际屏幕宽度与高保真宽度比例 */
    protected double mWidthScale;
    /** 实际屏幕高度与高保真高度比例 */
    protected double mHeightScale;
    /** 像素/点数 */
    protected float mDensity;

    /**
     * Vi数据投放器
     *
     * @param context 当前上下文
     */
    protected ViDataDelivery(Context context) {
        initWidthAndHeight(context);
        initScale();
        checkWidthAndHeight();
    }

    /**
     * 初始化实际屏幕宽度和屏幕高度
     *
     * @param context
     */
    private void initWidthAndHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        mDensity = dm.density;
    }

    /** 初始化实际屏幕宽度比例和屏幕高度比例 */
    private void initScale() {
        mWidthScale = 1.0 * mScreenWidth / ViSettingConstants.HIFI_WIDTH;
        mHeightScale = 1.0 * mScreenHeight / ViSettingConstants.HIFI_HEIGHT;
    }

    /** 检查屏幕宽度高度是否和模式一致 */
    private void checkWidthAndHeight() {
        // 屏幕模式
        boolean isPhone = ViSettingConstants.SCREEN_TYPE == ViSettingConstants.ScreenType.PHONE;
        // 宽度是否小于高度
        boolean isWidthLtHeight = mScreenWidth <= mScreenHeight;
        // 手机模式，宽度小于高度；平板模式，宽度大于高度
        // 如果isPhone和isWidthLtHeight不一致，则交换宽高度和缩放比例
        if (isPhone ^ isWidthLtHeight) {
            swapWidthAndHeight();
            swapScale();
        }
    }

    /** 交换宽高度 */
    private void swapWidthAndHeight() {
        int height = mScreenHeight;
        mScreenHeight = mScreenWidth;
        mScreenWidth = height;
    }

    /** 交换比例 */
    private void swapScale() {
        double heightScale = mHeightScale;
        mHeightScale = mWidthScale;
        mWidthScale = heightScale;
    }

}
