package com.jc.flora.apps.ui.shape.delegate;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.view.View;

import com.jc.flora.apps.component.deviceinfo.DeviceUtil;

/**
 * Created by Samurai on 2017/7/8.
 */
public class ShapeDelegate {

    public static GradientDrawable getSolidCornerDrawable
            (int radius, @ColorInt int solidColor) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(radius);
        gd.setColor(solidColor);
        return gd;
    }

    public static GradientDrawable getStrokeCornerDrawable
            (int radius, int width, @ColorInt int strokeColor) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(radius);
        gd.setStroke(width, strokeColor);
        return gd;
    }

    public static GradientDrawable getStrokeCornerDrawable
            (int radius, int width, @ColorInt int strokeColor,
             @ColorInt int solidColor) {
        GradientDrawable gd = getStrokeCornerDrawable(radius, width, strokeColor);
        gd.setColor(solidColor);
        return gd;
    }

    public static GradientDrawable getOvalDrawable(@ColorInt int solidColor) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(solidColor);
        return gd;
    }

    public static GradientDrawable getDashCornerDrawable
            (int radius, int width, @ColorInt int strokeColor,
             float dashWidth, float dashGap) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(radius);
        gd.setStroke(width, strokeColor, dashWidth, dashGap);
        return gd;
    }

    public static GradientDrawable getDashCornerDrawable
            (int radius, int width, @ColorInt int strokeColor,
             float dashWidth, float dashGap, @ColorInt int solidColor) {
        GradientDrawable gd = getDashCornerDrawable(radius, width, strokeColor, dashWidth, dashGap);
        gd.setColor(solidColor);
        return gd;
    }

    public static GradientDrawable getGradientDrawable(@ColorInt int[] colors) {
        return new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
    }

    public static GradientDrawable getGradientCornerDrawable
            (int radius, @ColorInt int[] colors) {
        GradientDrawable gd = getGradientDrawable(colors);
        gd.setCornerRadius(radius);
        return gd;
    }

    public static InsetDrawable getLineDrawable
            (@ColorInt int strokeColor,int insetLeft, int insetTop, int insetRight, int insetBottom) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.LINE);
        gd.setStroke(1, strokeColor);
        return new InsetDrawable(gd, insetLeft, insetTop, insetRight, insetBottom);
    }

    public static GradientDrawable getDashLineDrawable
            (@ColorInt int strokeColor, float dashWidth, float dashGap) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.LINE);
        gd.setStroke(1, strokeColor, dashWidth, dashGap);
        return gd;
    }

    public static InsetDrawable getDashLineDrawable
            (@ColorInt int strokeColor, float dashWidth, float dashGap,
             int insetLeft, int insetTop, int insetRight, int insetBottom) {
        return new InsetDrawable(getDashLineDrawable(strokeColor, dashWidth, dashGap),
                insetLeft, insetTop, insetRight, insetBottom);
    }

    public static ColorStateList getColorStateList(int normalColor, int pressedColor){
        int[] colors = {pressedColor, normalColor};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{};
        return new ColorStateList(states, colors);
    }

    public static StateListDrawable getPressedSelectorDrawable
            (Drawable drawable, Drawable pressedDrawable) {
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        sd.addState(new int[]{}, drawable);
        return sd;
    }

    public static boolean setRippleDrawable(View v, @ColorInt int color, @Nullable Drawable content) {
        if (v == null) {
            return true;
        }
        if (DeviceUtil.isSystemVersionAfterLollipop()) {
            v.setBackground(new RippleDrawable(ColorStateList.valueOf(color), content, null));
            return true;
        }
        return false;
    }

}