package com.jc.flora.apps.ui.literacy.widget;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.flora.R;

/**
 * 识字卡片
 * Created by Samurai on 2018/1/21.
 */

public class LiteracyCardView extends LinearLayout{

    /** 显示拼音的字库路径 */
    private static final String FONT_ASSET_PATH = "font/pinyin.ttf";

    /** 拼音字体大小，单位dp */
    private static final int PIN_YIN_TEXT_SIZE = 13;
    /** 汉字字体大小，单位dp */
    private static final int CHAR_TEXT_SIZE = 15;

    /** 选中字体颜色 */
    private static final int SELECTED_TEXT_COLOR = 0xFF20A6FE;
    /** 未选中拼音字体颜色 */
    private static final int UNSELECTED_PIN_YIN_TEXT_COLOR = 0xFF999999;
    /** 未选中汉字字体颜色 */
    private static final int UNSELECTED_CHAR_TEXT_COLOR = 0xFF000000;

    /** 拼音左右间距，单位dp */
    private static final int PIN_YIN_PADDING_DP_X = 0;
    /** 拼音上下间距，单位dp */
    private static final int PIN_YIN_PADDING_DP_Y = 1;
    /** 不显示拼音时的汉字左右间距，单位dp */
    private static final int CHAR_PADDING_DP_X = 0;
    /** 显示拼音时的汉字左右间距，单位dp */
    private static final int CHAR_ON_PADDING_DP_X = 15;
    /** 汉字上下间距，单位dp */
    private static final int CHAR_PADDING_DP_Y = 0;

    /** 拼音左右间距，单位px */
    private int mPinyinPaddingX = 0;
    /** 拼音上下间距，单位px */
    private int mPinyinPaddingY = 0;
    /** 不显示拼音时的汉字左右间距，单位px */
    private int mCharPaddingX = 0;
    /** 显示拼音时的汉字左右间距，单位px */
    private int mCharOnPaddingX = 0;
    /** 汉字上下间距，单位px */
    private int mCharPaddingY = 0;

    /** 间距样式 */
    private LiteracyStyleDelegate.PaddingStyle mPaddingStyle;

    /** 拼音显示控件 */
    private TextView mTvPinyin;
    /** 汉字显示控件 */
    private TextView mTvChar;

    public LiteracyCardView(Context context) {
        super(context);
        init();
    }

    public LiteracyCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LiteracyCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        float density = dm.density;
        mPinyinPaddingX = (int)(PIN_YIN_PADDING_DP_X * density);
        mPinyinPaddingY = (int)(PIN_YIN_PADDING_DP_Y * density);
        mCharPaddingX = (int)(CHAR_PADDING_DP_X * density);
        mCharOnPaddingX = (int)(CHAR_ON_PADDING_DP_X * density);
        mCharPaddingY = (int) (CHAR_PADDING_DP_Y * density);
        addPinyinTextView();
        addCharTextView();
    }

    /**
     * 添加拼音显示控件
     */
    private void addPinyinTextView() {
        mTvPinyin = new TextView(getContext());
        mTvPinyin.setTextSize(TypedValue.COMPLEX_UNIT_DIP, PIN_YIN_TEXT_SIZE);
        mTvPinyin.setTextColor(UNSELECTED_PIN_YIN_TEXT_COLOR);
        setPadding(mTvPinyin, mPinyinPaddingX, mPinyinPaddingY);
        Typeface face = Typeface.createFromAsset(getResources().getAssets(), FONT_ASSET_PATH);
        mTvPinyin.setTypeface(face);
        addView(mTvPinyin, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * 添加汉字显示控件
     */
    private void addCharTextView() {
        mTvChar = new TextView(getContext());
        mTvChar.setTextSize(TypedValue.COMPLEX_UNIT_DIP, CHAR_TEXT_SIZE);
        mTvChar.setTextColor(UNSELECTED_CHAR_TEXT_COLOR);
        setPadding(mTvChar, mCharPaddingX, mCharPaddingY);
        addView(mTvChar, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置拼音显示文本
     * 是否有拼音宽度占位由拼音文本决定
     * @param pinyin 拼音显示文本
     */
    protected void setPinyinText(String pinyin) {
        mPaddingStyle = LiteracyStyleDelegate.getPaddingStyleByPinyin(pinyin);
        if (mPaddingStyle != LiteracyStyleDelegate.PaddingStyle.PIN_YIN) {
            // 替换为空格显示
            pinyin = " ";
        }
        mTvPinyin.setText(pinyin);
    }

    /**
     * 设置汉字显示文本
     * @param text 汉字显示文本
     */
    protected void setCharText(String text){
        LiteracyStyleDelegate.CharStyle charStyle = LiteracyStyleDelegate.getCharStyleByText(text);
        switch (charStyle) {
            case HALF_SPACE:
                // 替换为半个中文空格显示
                text = getResources().getString(R.string.app_half_space);
                break;
            case SPACE:
                // 替换为一个中文空格显示
                text = getResources().getString(R.string.app_space);
                break;
            default:
                break;
        }
        mTvChar.setText(text);
    }

    /**
     * 打开拼音显示
     */
    protected void setPinyinOn() {
        mTvPinyin.setVisibility(mPaddingStyle.hasPinyinHeight() ? View.VISIBLE : View.GONE);
        int paddingLeft = mPaddingStyle.hasLeftPadding() ? mCharOnPaddingX : mCharPaddingX;
        int paddingRight = mPaddingStyle.hasRightPadding() ? mCharOnPaddingX : mCharPaddingX;
        mTvChar.setPadding(paddingLeft, mCharPaddingY, paddingRight, mCharPaddingY);
    }

    /**
     * 关闭拼音显示
     */
    protected void setPinyinOff() {
        mTvPinyin.setVisibility(View.GONE);
        setPadding(mTvChar, mCharPaddingX, mCharPaddingY);
    }

    /**
     * 设置选中
     */
    protected void setSelectedOn(){
        mTvPinyin.setTextColor(SELECTED_TEXT_COLOR);
        mTvChar.setTextColor(SELECTED_TEXT_COLOR);
    }

    /**
     * 设置不选中
     */
    protected void setSelectedOff(){
        mTvPinyin.setTextColor(UNSELECTED_PIN_YIN_TEXT_COLOR);
        mTvChar.setTextColor(UNSELECTED_CHAR_TEXT_COLOR);
    }

    /**
     * 设置padding间距
     * @param v
     * @param paddingX
     * @param paddingY
     */
    private void setPadding(View v, int paddingX, int paddingY){
        v.setPadding(paddingX, paddingY, paddingX, paddingY);
    }

}