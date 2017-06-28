package com.jc.flora.apps.component.vi.fidelity;

/**
 * 适配常量类
 */
public interface ViSettingConstants {
    /** 高保真宽度 */
    int HIFI_WIDTH = 720;
    /** 高保真高度 */
    int HIFI_HEIGHT = 1280;
    /** 屏幕模式 */
    ScreenType SCREEN_TYPE = ScreenType.PHONE;

    /**
     * 屏幕模式
     * <br>PHONE对应手机模式；PAD_OR_TV对应平板或电视模式
     */
    enum ScreenType {
        PHONE, PAD_OR_TV
    }

}
