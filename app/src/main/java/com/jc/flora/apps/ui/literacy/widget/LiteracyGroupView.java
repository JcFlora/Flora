package com.jc.flora.apps.ui.literacy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.flora.R;

/**
 * 识字卡片组
 * Created by Samurai on 2018/1/21.
 */

public class LiteracyGroupView extends LinearLayout {

    /** 通用换行符：| */
    private static final String LINE_SPLIT = "\\|";
    /** 仅在左对齐模式下有效的换行符：$ */
    private static final String LEFT_LINE_SPLIT = "\\$";
    /** 拼音的分隔符：（空格） */
    private static final String PIN_YIN_SPLIT = "\\s+";
    /** 标点符号占位符 */
    private static final String PLACE_BIAO_DIAN = "#";
    /** 汉字占位符 */
    private static final String PLACE_CHAR = "\\*";

    /** 当前上下文 */
    private Context mContext;
    /** 识字卡片二维数组，每一个拼音+汉字都是一个识字卡片 */
    private LiteracyCardView[][] mCardGroup;
    /** 当前对齐模式 */
    private int mRowGravity = Gravity.CENTER_HORIZONTAL;
    /** 汉字缓存 */
    private String mCharText;
    /** 拼音缓存 */
    private String mPinyinText;

    public LiteracyGroupView(Context context) {
        super(context);
        init();
    }

    public LiteracyGroupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LiteracyGroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mContext = getContext();
        setOrientation(VERTICAL);
    }

    /**
     * 设置行内对齐方式，只支持居中和非居中（左对齐）
     * @param gravity
     */
    public void setRowGravity(int gravity) {
        mRowGravity = gravity;
    }

    /**
     * 设置数据源格式，内容中的特殊字符用于控制换行、分隔
     * @param charText 汉字内容 eg "悯农|锄禾日当午，|汗滴禾下土，|谁知盘中餐，|粒粒皆辛苦。"
     * @param pinyinText 拼音 eg "mǐn nóng|chú hé rì dāng wǔ #|hàn dī hé xià tǔ #|shuí zhī pán zhōng cān #|lì lì jiē xīn kǔ #";
     */
    public void setData(String charText, String pinyinText) {
        // 检查是否为空
        if(charText == null || pinyinText == null){
            return;
        }
        // 检查是否和缓存相同
        if(charText.equals(mCharText) && pinyinText.equals(mPinyinText)){
            return;
        }
        // 转换为数组
        String[][] charArray = convertCharText(charText);
        String[][] pinyinArray = convertPinyinText(pinyinText);
        // 检查数组是否为空
        if (charArray == null || pinyinArray == null || charArray.length == 0 || pinyinArray.length == 0) {
            return;
        }
        // 检查长度是否一致
        if(charArray.length != pinyinArray.length){
            Log.e("长度不一致","汉字和拼音长度不一致，请检查---->"+charText+"-----"+pinyinText);
            // todo 测试环境代码，上线前注释掉
//            throw new RuntimeException("汉字和拼音长度不一致，请检查---->"+charText+"-----"+pinyinText);
        }
        mCharText = charText;
        mPinyinText = pinyinText;
        removeAllViews();
        int rows = charArray.length < pinyinArray.length ? charArray.length : pinyinArray.length;
        mCardGroup = new LiteracyCardView[rows][];
        for (int row = 0; row < rows; row++) {
            LinearLayout layoutRow = new LinearLayout(mContext);
            layoutRow.setOrientation(HORIZONTAL);
            layoutRow.setGravity(mRowGravity);
            addView(layoutRow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            if(charArray[row].length != pinyinArray[row].length){
                Log.e("长度不一致","汉字和拼音长度不一致，请检查---->"+charText+"-----"+pinyinText);
                // todo 测试环境代码，上线前注释掉
//                throw new RuntimeException("汉字和拼音长度不一致，请检查---->"+charText+"-----"+pinyinText);
            }
            int columns = charArray[row].length < pinyinArray[row].length ? charArray[row].length : pinyinArray[row].length;
            mCardGroup[row] = new LiteracyCardView[columns];
            for (int column = 0; column < columns; column++) {
                mCardGroup[row][column] = new LiteracyCardView(mContext);
                layoutRow.addView(mCardGroup[row][column], LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                mCardGroup[row][column].setPinyinText(pinyinArray[row][column]);
                mCardGroup[row][column].setCharText(charArray[row][column]);
            }
        }
    }

    /**
     * 转换汉字为二维数组
     * @param charText
     * @return
     */
    private String[][] convertCharText(String charText) {
        if (TextUtils.isEmpty(charText) || TextUtils.isEmpty(charText.trim())) {
            return null;
        }
        charText = charText.replaceAll(LEFT_LINE_SPLIT, "|");
        String[] rowText = charText.trim().split(LINE_SPLIT);
        int rows = rowText.length;
        if (rows == 0) {
            return null;
        }
        String[][] charArray = new String[rows][];
        for (int row = 0; row < rows; row++) {
            int columns = rowText[row].length();
            charArray[row] = new String[columns];
            for (int column = 0; column < columns; column++) {
                charArray[row][column] = rowText[row].trim().toCharArray()[column]+"";
            }
        }
        return charArray;
    }

    /**
     * 转换拼音为二维数组
     * @param pinyinText
     * @return
     */
    private String[][] convertPinyinText(String pinyinText) {
        if (TextUtils.isEmpty(pinyinText) || TextUtils.isEmpty(pinyinText.trim())) {
            return null;
        }
        pinyinText = pinyinText.replaceAll(LEFT_LINE_SPLIT, "|");
        String[] rowText = pinyinText.trim().split(LINE_SPLIT);
        int rows = rowText.length;
        if (rows == 0) {
            return null;
        }
        String[][] pinyinArray = new String[rows][];
        for (int row = 0; row < rows; row++) {
            String[] columnArray = rowText[row].trim().split(PIN_YIN_SPLIT);
            int columns = columnArray.length;
            pinyinArray[row] = new String[columns];
            System.arraycopy(columnArray, 0, pinyinArray[row], 0, columns);
        }
        return pinyinArray;
    }

    /**
     * 打开拼音显示
     */
    public void setPinyinOn() {
        for (LiteracyCardView[] literacyRow : mCardGroup) {
            for (LiteracyCardView literacyCard : literacyRow) {
                literacyCard.setPinyinOn();
            }
        }
        requestLayout();
    }

    /**
     * 关闭拼音显示
     */
    public void setPinyinOff() {
        for (LiteracyCardView[] literacyRow : mCardGroup) {
            for (LiteracyCardView literacyCard : literacyRow) {
                literacyCard.setPinyinOff();
            }
        }
        requestLayout();
    }

    /**
     * 设置被选中
     */
    public void setSelectedOn(){
        for (LiteracyCardView[] literacyRow : mCardGroup) {
            for (LiteracyCardView literacyCard : literacyRow) {
                literacyCard.setSelectedOn();
            }
        }
    }

    /**
     * 设置未被选中
     */
    public void setSelectedOff(){
        for (LiteracyCardView[] literacyRow : mCardGroup) {
            for (LiteracyCardView literacyCard : literacyRow) {
                literacyCard.setSelectedOff();
            }
        }
    }

    /**
     * 用于在普通文本控件中过滤特殊字符
     * @param tv
     * @param charText
     */
    public static void filterCharText(TextView tv, String charText) {
        charText = charText.replaceAll(LINE_SPLIT, "\n")
                .replaceAll(LEFT_LINE_SPLIT, "")
                .replaceAll(PLACE_CHAR, tv.getResources().getString(R.string.app_space))
                .replaceAll(PLACE_BIAO_DIAN, tv.getResources().getString(R.string.app_half_space));
        tv.setText(charText);
    }

}