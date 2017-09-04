package com.jc.flora.apps.ui.list.model;

/**
 * Created by Samurai on 2017/9/5.
 */
public class Slip3 {

    public int iconResId;
    public String title;
    public String subTitle;
    public boolean isChecked;
    public SlipType slipType = SlipType.BASIC;

    public enum SlipType {
        BASIC, END_SUBTITLE, BOTTOM_SUBTITLE, SWITCH
    }

}
