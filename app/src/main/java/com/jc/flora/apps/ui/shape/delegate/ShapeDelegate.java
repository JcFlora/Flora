package com.jc.flora.apps.ui.shape.delegate;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;

/**
 * Created by Samurai on 2017/7/8.
 */
public class ShapeDelegate {

    public static GradientDrawable getSolidCornerDrawable(int radius, @ColorInt int solidColor){
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(radius);
        gd.setColor(solidColor);
        return gd;
    }

    public static GradientDrawable getStrokeCornerDrawable(int radius, int width, @ColorInt int strokeColor){
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(radius);
        gd.setStroke(width, strokeColor);
        return gd;
    }

    public static GradientDrawable getStrokeCornerDrawable(int radius, int width, @ColorInt int strokeColor, @ColorInt int solidColor){
        GradientDrawable gd = getStrokeCornerDrawable(radius, width, strokeColor);
        gd.setColor(solidColor);
        return gd;
    }

}
