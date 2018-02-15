package com.jc.flora.apps.component.render.delegate;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.vi.fidelity.Fidelity;

/**
 * Created by Samurai on 2017/6/22.
 */
public class Render {

    /**
     * 文字颜色资源
     */
    private static final int[] TEXT_COLOR_RES = {
            R.color.app_color_text_primary, R.color.app_color_text_black,
            R.color.app_color_text_gray, R.color.app_color_text_hint,
            R.color.app_color_text_unable, R.color.app_color_text_white,
            R.color.app_color_text_price,
            R.color.app_color_tag_red, R.color.app_color_tag_pink_dark,
            R.color.app_color_tag_orange, R.color.app_color_tag_green,
            R.color.app_color_tag_blue_bright, R.color.app_color_tag_purple
    };

    /**
     * 颜色资源
     */
    private static final int[] BG_COLOR_RES = {
            R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent,
            R.color.app_color_bg_primary, R.color.app_color_bg_content,
            R.color.app_color_bg_bar, R.color.app_color_bg_item,
            R.color.app_color_divider_content, R.color.app_color_divider_item,
            R.color.app_color_tag_red, R.color.app_color_tag_pink_dark,
            R.color.app_color_tag_orange, R.color.app_color_tag_green,
            R.color.app_color_tag_blue_bright, R.color.app_color_tag_purple
    };

    private static final int[] TEXT_SIZE_RES = {
            R.dimen.app_text_size_12dp,R.dimen.app_text_size_14dp,
            R.dimen.app_text_size_16dp,R.dimen.app_text_size_18dp,
            R.dimen.app_text_size_20dp,
            R.dimen.app_text_size_24px,R.dimen.app_text_size_28px,
            R.dimen.app_text_size_32px,R.dimen.app_text_size_36px,
            R.dimen.app_text_size_40px
    };

    private static Render sInstance;
    private Resources mResources;
    private Fidelity mFidelity;

    public static Render getInstance(Context context) {
        if (sInstance == null) {
            synchronized (Render.class) {
                if (sInstance == null) {
                    sInstance = new Render(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private Render(Context context) {
        mResources = context.getResources();
        mFidelity = Fidelity.getInstance(context);
    }

    public void setTextColor(TextView tv, TextColor textColor) {
        if (tv != null) {
            tv.setTextColor(mResources.getColor(TEXT_COLOR_RES[textColor.ordinal()]));
        }
    }

    public void setBgColor(View v, BgColor bgColor) {
        if (v != null) {
            v.setBackgroundColor(mResources.getColor(BG_COLOR_RES[bgColor.ordinal()]));
        }
    }

    public void setTextSize(TextView tv, TextSize textSize){
        if(tv != null){
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize(textSize));
        }
    }

    public int getColor(TextColor textColor) {
        return mResources.getColor(TEXT_COLOR_RES[textColor.ordinal()]);
    }

    public int getColor(BgColor bgColor) {
        return mResources.getColor(BG_COLOR_RES[bgColor.ordinal()]);
    }

    public int getColorRes(TextColor textColor) {
        return TEXT_COLOR_RES[textColor.ordinal()];
    }

    public int getColorRes(BgColor bgColor) {
        return BG_COLOR_RES[bgColor.ordinal()];
    }

    public int getTextSize(TextSize textSize){
        int index = textSize.ordinal();
        int px = mResources.getDimensionPixelSize(TEXT_SIZE_RES[index]);
        if(index >= 5){
            px = (int) mFidelity.hifi2px(px);
        }
        return px;
    }

    public enum TextColor {
        PRIMARY, BLACK, GRAY, HINT, UNABLE, WHITE, PRICE,
        TAG_RED, TAG_PINK_DARK, TAG_ORANGE, TAG_GREEN, TAG_BLUE_BRIGHT, TAG_PURPLE
    }

    public enum BgColor {
        PRIMARY, PRIMARY_DARK, ACCENT, BG_PRIMARY, BG_CONTENT, BG_BAR, BG_ITEM, DIVIDER_CONTENT, DIVIDER_ITEM,
        TAG_RED, TAG_PINK_DARK, TAG_ORANGE, TAG_GREEN, TAG_BLUE_BRIGHT, TAG_PURPLE
    }

    public enum TextSize {
        _12_DP,_14_DP,_16_DP,_18_DP,_20_DP,
        _24_PX,_28_PX,_32_PX,_36_PX,_40_PX
    }

}
