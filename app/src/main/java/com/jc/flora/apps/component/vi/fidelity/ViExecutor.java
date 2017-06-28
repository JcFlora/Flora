package com.jc.flora.apps.component.vi.fidelity;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;

/**
 * 适配执行器
 */
public final class ViExecutor {

    /** Vi数据投放器 */
    private ViDataDelivery mDataDelivery;

    /** 比例 */
    private double mScale;
    /** 边长 */
    private int mSide;

    /**
     * 适配执行器
     *
     * @param dataDelivery Vi数据投放器
     */
    protected ViExecutor(ViDataDelivery dataDelivery) {
        this(dataDelivery, false);
    }

    /**
     * 适配执行器
     *
     * @param dataDelivery Vi数据投放器
     */
    protected ViExecutor(ViDataDelivery dataDelivery, boolean isHeightScaleMode) {
        mDataDelivery = dataDelivery;
        mScale = isHeightScaleMode ? dataDelivery.mHeightScale : dataDelivery.mWidthScale;
        mSide = isHeightScaleMode ? dataDelivery.mScreenHeight : dataDelivery.mScreenWidth;
    }

    private boolean isViEnable(View v) {
        return v != null && v.getLayoutParams() != null;
    }

    private void setWidth(LayoutParams params, double hifiWidth){
        if (hifiWidth < 0 && hifiWidth > -1.1f) {
            params.width = LayoutParams.MATCH_PARENT;
        } else if (hifiWidth < -1.1) {
            params.width = LayoutParams.WRAP_CONTENT;
        } else {
            params.width = (int) (hifiWidth * mScale + 0.5f);
        }
    }

    private void setHeight(LayoutParams params, double hifiHeight){
        if (hifiHeight < 0 && hifiHeight > -1.1f) {
            params.height = LayoutParams.MATCH_PARENT;
        } else if (hifiHeight < -1.1) {
            params.height = LayoutParams.WRAP_CONTENT;
        } else {
            params.height = (int) (hifiHeight * mScale + 0.5f);
        }
    }

    private void setWidthByScale(LayoutParams params, double hifiWidthScale){
        if (hifiWidthScale < 0 && hifiWidthScale > -1.1f) {
            params.width = LayoutParams.MATCH_PARENT;
        } else if (hifiWidthScale < -1.1) {
            params.width = LayoutParams.WRAP_CONTENT;
        } else {
            params.width = (int) (hifiWidthScale * mSide + 0.5f);
        }
    }

    private void setHeightByScale(LayoutParams params, double hifiHeightScale){
        if (hifiHeightScale < 0 && hifiHeightScale > -1.1f) {
            params.height = LayoutParams.MATCH_PARENT;
        } else if (hifiHeightScale < -1.1) {
            params.height = LayoutParams.WRAP_CONTENT;
        } else {
            params.height = (int) (hifiHeightScale * mSide + 0.5f);
        }
    }

    /**
     * 设置宽度
     *
     * @param v         适配控件
     * @param hifiWidth 高保真宽度
     */
    protected void setWidth(View v, double hifiWidth) {
        if (!isViEnable(v)) {
            return;
        }
        LayoutParams params = v.getLayoutParams();
        setWidth(params, hifiWidth);
        v.setLayoutParams(params);
    }

    /**
     * 设置高度
     *
     * @param v          适配控件
     * @param hifiHeight 高保真高度
     */
    protected void setHeight(View v, double hifiHeight) {
        if (!isViEnable(v)) {
            return;
        }
        LayoutParams params = v.getLayoutParams();
        setHeight(params, hifiHeight);
        v.setLayoutParams(params);
    }

    /**
     * 设置宽度和高度
     *
     * @param v          适配控件
     * @param hifiWidth 高保真宽度
     * @param hifiHeight 高保真高度
     */
    protected void setWidthHeight(View v, double hifiWidth, double hifiHeight) {
        if (!isViEnable(v)) {
            return;
        }
        LayoutParams params = v.getLayoutParams();
        setWidth(params, hifiWidth);
        setHeight(params, hifiHeight);
        v.setLayoutParams(params);
    }

    /**
     * 按比例设置宽度
     *
     * @param v              适配控件
     * @param hifiWidthScale 高保真宽度比例
     */
    protected void setWidthByScale(View v, double hifiWidthScale) {
        if (!isViEnable(v)) {
            return;
        }
        LayoutParams params = v.getLayoutParams();
        setWidthByScale(params, hifiWidthScale);
        v.setLayoutParams(params);
    }

    /**
     * 按比例设置高度
     *
     * @param v               适配控件
     * @param hifiHeightScale 高保真高度比例
     */
    protected void setHeightByScale(View v, double hifiHeightScale) {
        if (!isViEnable(v)) {
            return;
        }
        LayoutParams params = v.getLayoutParams();
        setHeightByScale(params, hifiHeightScale);
        v.setLayoutParams(params);
    }

    /**
     * 按比例设置宽度和高度
     *
     * @param v              适配控件
     * @param hifiWidthScale 高保真宽度比例
     * @param hifiHeightScale 高保真高度比例
     */
    protected void setWidthHeightByScale(View v, double hifiWidthScale, double hifiHeightScale) {
        if (!isViEnable(v)) {
            return;
        }
        LayoutParams params = v.getLayoutParams();
        setWidthByScale(params, hifiWidthScale);
        setHeightByScale(params, hifiHeightScale);
        v.setLayoutParams(params);
    }

    /**
     * 动态按比例设置高度
     *
     * @param v                    适配控件
     * @param hifiHeightWidthRatio 高保真宽度高度比例
     */
    @Deprecated
    protected void setHeightOnLayouted(final View v, final double hifiHeightWidthRatio) {
        if (hifiHeightWidthRatio <= 0 || !isViEnable(v)) {
            return;
        }
        final LayoutParams params = v.getLayoutParams();
        // 布局完成观察者
        ViewTreeObserver observer = v.getViewTreeObserver();
        // 添加布局完成后的回调处理监听
        observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 布局完成观察者
                ViewTreeObserver observer = v.getViewTreeObserver();
                // 清除这一次的回调处理的监听
                observer.removeGlobalOnLayoutListener(this);
                // 获取宽度
                int width = v.getMeasuredWidth();
                // 设置高度
                params.height = (int) (hifiHeightWidthRatio * width + 0.5);
                v.setLayoutParams(params);
            }
        });
    }

    /**
     * 设置字体大小
     *
     * @param tv           文本控件
     * @param hifiTextSize 高保真字体大小
     */
    protected void setTextSize(TextView tv, int hifiTextSize) {
        if (tv == null) {
            return;
        }
        int screenTextSize = (int) (hifiTextSize * mScale + 0.5);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, screenTextSize);
    }

    /**
     * 获取适配后的尺寸，单位px
     *
     * @param hifiDimen 高保真中给出的尺寸
     * @return 适配后的尺寸
     */
    protected double getViDimen(double hifiDimen) {
        return hifiDimen * mScale;
    }

    /**
     * dp转换为px
     *
     * @param dpValue dp值
     * @return px值
     */
    protected double dp2px(double dpValue) {
        return mDataDelivery.mDensity * dpValue;
    }

}
