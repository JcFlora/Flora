package com.jc.flora.apps.component.vi.fidelity;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Vi数据投放器
 */
final class ViDataDelivery {

    /** 高保真宽度 */
    static int sHifiWidth = 720;
    /** 高保真高度 */
    static int sHifiHeight = 1280;
    /** 是否是手机屏幕（高保真宽度小于高保真高度） */
    static boolean sIsPhone = true;

    /** 实际屏幕宽度 */
    int mScreenWidth;
    /** 实际屏幕高度 */
    int mScreenHeight;
    /** 实际屏幕宽度与高保真宽度比例 */
    double mWidthScale;
    /** 实际屏幕高度与高保真高度比例 */
    double mHeightScale;
    /** 像素/点数 */
    float mDensity;

    public static void initStatic(int hifiWidth, int hifiHeight){
        if(hifiWidth <= 0 || hifiHeight <= 0){
            return;
        }
        sHifiWidth = hifiWidth;
        sHifiHeight = hifiHeight;
        sIsPhone = hifiWidth <= hifiHeight;
    }

    /**
     * Vi数据投放器
     *
     * @param context 当前上下文
     */
    ViDataDelivery(Context context) {
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
        mWidthScale = 1.0 * mScreenWidth / sHifiWidth;
        mHeightScale = 1.0 * mScreenHeight / sHifiHeight;
    }

    /** 检查实际屏幕宽度高度是否和高保真模式一致 */
    private void checkWidthAndHeight() {
        // 宽度是否小于高度
        boolean isWidthLtHeight = mScreenWidth <= mScreenHeight;
        // 手机模式，宽度小于高度；平板模式，宽度大于高度
        // 如果sIsPhone和isWidthLtHeight不一致，则交换宽高度和缩放比例
        if (sIsPhone ^ isWidthLtHeight) {
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
