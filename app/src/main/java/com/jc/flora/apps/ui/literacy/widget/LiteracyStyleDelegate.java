package com.jc.flora.apps.ui.literacy.widget;

import android.text.TextUtils;

/**
 * Created by Samurai on 2018/1/21.
 */

public class LiteracyStyleDelegate {

    /** * 占位控制符，文字有左右间距 */
    private static final String PLACE_X_PADDING = "*";
    /** # 占位控制符，文字无左右间距 */
    private static final String PLACE_NO_PADDING = "#";
    /** [ 占位控制符，文字只有左间距 */
    private static final String PLACE_LEFT_PADDING = "[";
    /** ] 占位控制符，文字只有右间距 */
    private static final String PLACE_RIGHT_PADDING = "]";

    /** - 占位控制符，文字有左右间距，拼音无高度 */
    private static final String PLACE_X_PADDING_NO_HEIGHT = "-";
    /** _ 占位控制符，文字无左右间距，拼音无高度 */
    private static final String PLACE_NO_PADDING_NO_HEIGHT = "_";
    /** < 占位控制符，文字只有左间距，拼音无高度 */
    private static final String PLACE_LEFT_PADDING_NO_HEIGHT = "<";
    /** > 占位控制符，文字只有右间距，拼音无高度 */
    private static final String PLACE_RIGHT_PADDING_NO_HEIGHT = ">";

    /** # 占位符 */
    private static final String PLACE_SHARP = "#";
    /** * 占位符 */
    private static final String PLACE_STAR = "*";
    /** _ 占位符 */
    private static final String PLACE_BLANK = "_";

    /** 拼音占位符数组 */
    private static final String[] PLACE_ARRAY = {
            PLACE_X_PADDING, PLACE_NO_PADDING,
            PLACE_LEFT_PADDING, PLACE_RIGHT_PADDING,
            PLACE_X_PADDING_NO_HEIGHT, PLACE_NO_PADDING_NO_HEIGHT,
            PLACE_LEFT_PADDING_NO_HEIGHT, PLACE_RIGHT_PADDING_NO_HEIGHT
    };

    /** 汉字占位符数组 */
    private static final String[] PLACE_ARRAY2 = {
            PLACE_SHARP, PLACE_STAR, PLACE_BLANK
    };

    /**
     * 根据拼音文本获取间距样式
     * @param pinyin
     * @return
     */
    static PaddingStyle getPaddingStyleByPinyin(String pinyin) {
        if (isEmpty(pinyin)) {
            return PaddingStyle.NO_PADDING;
        }
        for (int i = 0; i < PLACE_ARRAY.length; i++) {
            if (isPlace(pinyin, PLACE_ARRAY[i])) {
                return PaddingStyle.values()[i];
            }
        }
        return PaddingStyle.PIN_YIN;
    }

    /**
     * 根据汉字文本获取字符样式
     * @param text
     * @return
     */
    static CharStyle getCharStyleByText(String text) {
        if (isEmpty(text)) {
            return CharStyle.HALF_SPACE;
        }
        for (int i = 0; i < PLACE_ARRAY2.length; i++) {
            if (isPlace(text, PLACE_ARRAY2[i])) {
                return CharStyle.values()[i];
            }
        }
        return CharStyle.CHAR;
    }

    /**
     * 是否为空
     *
     * @param text
     * @return
     */
    private static boolean isEmpty(String text) {
        return TextUtils.isEmpty(text) || TextUtils.isEmpty(text.trim());
    }

    /**
     * 是否是某个占位符
     *
     * @param text
     * @param place
     * @return
     */
    private static boolean isPlace(String text, String place) {
        return text.trim().equals(place);
    }

    /**
     * 间距样式
     */
    public enum PaddingStyle {
        X_PADDING,
        NO_PADDING,
        LEFT_PADDING,
        RIGHT_PADDING,
        X_PADDING_NO_HEIGHT,
        NO_PADDING_NO_HEIGHT,
        LEFT_PADDING_NO_HEIGHT,
        RIGHT_PADDING_NO_HEIGHT,
        PIN_YIN;
        public boolean hasXPadding(){
            return this == X_PADDING || this == X_PADDING_NO_HEIGHT || this == PIN_YIN;
        }
        public boolean hasLeftPadding(){
            return hasXPadding() || this == LEFT_PADDING || this == LEFT_PADDING_NO_HEIGHT;
        }
        public boolean hasRightPadding(){
            return hasXPadding() || this == RIGHT_PADDING || this == RIGHT_PADDING_NO_HEIGHT;
        }
        public boolean hasPinyinHeight(){
            return this == X_PADDING || this == NO_PADDING || this == LEFT_PADDING || this == RIGHT_PADDING || this == PIN_YIN;
        }
    }

    /**
     * 字符样式
      */
    public enum CharStyle {
        HALF_SPACE, SPACE, BLANK, CHAR
    }

}
