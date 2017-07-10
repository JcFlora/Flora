package com.jc.flora.apps.ui.shape.delegate;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;

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

    public static GradientDrawable getDashLineDrawable
            (@ColorInt int strokeColor, float dashWidth, float dashGap) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.LINE);
        gd.setStroke(1, strokeColor, dashWidth, dashGap);
        return gd;
    }

}