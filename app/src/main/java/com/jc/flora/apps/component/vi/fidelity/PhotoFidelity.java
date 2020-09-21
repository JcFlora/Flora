package com.jc.flora.apps.component.vi.fidelity;

import android.view.View;
import android.view.ViewGroup;

/**
 * 适配图片的高保真组件
 */
public class PhotoFidelity {

    /**
     *
     * @param target                需要适配的图片（或图片容器）
     * @param heightRatioForWidth   图片的高宽比
     */
    public static ViewGroup.LayoutParams fitPhoto(View target, double heightRatioForWidth) {
        return fitPhoto(target, 0d, 0d, 0d, 1d, heightRatioForWidth);
    }

    /**
     *
     * @param target                需要适配的图片（或图片容器）
     * @param spaceUnitWidthDp      被扣除的单个间距宽度的dp值
     * @param spaceMultiplier       被扣除的间距的倍数
     * @param heightRatioForWidth   图片的高宽比
     */
    public static ViewGroup.LayoutParams fitPhoto(View target, double spaceUnitWidthDp, double spaceMultiplier, double heightRatioForWidth) {
        return fitPhoto(target, spaceUnitWidthDp, spaceMultiplier, 0d, 1d, heightRatioForWidth);
    }

    /**
     *
     * @param target                需要适配的图片（或图片容器）
     * @param spaceUnitWidthDp      被扣除的单个间距宽度的dp值
     * @param spaceMultiplier       被扣除的间距的倍数
     * @param divider               分列的倍数
     * @param heightRatioForWidth   图片的高宽比
     */
    public static ViewGroup.LayoutParams fitPhoto(View target, double spaceUnitWidthDp, double spaceMultiplier, double divider, double heightRatioForWidth) {
        return fitPhoto(target, spaceUnitWidthDp, spaceMultiplier, 0d, divider, heightRatioForWidth);
    }

        /**
         *
         * @param target                需要适配的图片（或图片容器）
         * @param spaceUnitWidthDp      被扣除的单个间距宽度的dp值
         * @param spaceMultiplier       被扣除的间距的倍数
         * @param spaceSubWidthDp       被扣除的附加的间距
         * @param divider               分列的倍数
         * @param heightRatioForWidth   图片的高宽比
         */
    public static ViewGroup.LayoutParams fitPhoto(View target, double spaceUnitWidthDp, double spaceMultiplier, double spaceSubWidthDp, double divider, double heightRatioForWidth){
        // 获取Fidelity组件
        Fidelity fidelity = Fidelity.getInstance(target.getContext());
        // 获取屏幕宽度
        int screenWidth = fidelity.getScreenWidth();
        // 转换单个间距的宽度值为px单位
        double spaceUnitWidthPx = fidelity.dp2px(spaceUnitWidthDp);
        // 转换附加间距的宽度值为px单位
        double spaceSubWidthPx = fidelity.dp2px(spaceSubWidthDp);
        // 通过公式计算宽度
        double width = (screenWidth - spaceMultiplier * spaceUnitWidthPx - spaceSubWidthPx) / divider;
        // 宽度乘以高宽比就是高度
        double height = width * heightRatioForWidth;
        // 获取目标的LayoutParams参数
        ViewGroup.LayoutParams params = target.getLayoutParams();
        // 设置目标的宽度
        params.width = (int) (width + 0.5f);
        // 设置目标的高度
        params.height = (int) (height + 0.5f);
        // 刷新目标渲染新的宽度高度
        target.setLayoutParams(params);
        // 返回LayoutParams参数
        return params;
    }

}