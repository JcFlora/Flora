package com.jc.flora.apps.component.render.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.render.delegate.Render;

/**
 * Created by Samurai on 2017/6/22.
 */
public class RenderColorActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private View mLayoutCaptainIndicators;

    private View mVColorPrimary;
    private View mVColorPrimaryDark;
    private View mVColorAccent;
    private View mVColorDividerContent;
    private View mVColorBgItem;
    private View mVColorDividerItem;

    private TextView mTvTextColorGray;
    private TextView mTvTextColorBlack;
    private TextView mTvTextColorPrimary;
    private TextView mTvTextColorHint;
    private TextView mTvTextColorUnable;
    private TextView mTvTextColorPrice;

    private View mVColorTagRed;
    private View mVColorTagPinkDark;
    private View mVColorTagOrange;
    private View mVColorTagGreen;
    private View mVColorTagBlueBright;
    private View mVColorTagPurple;

    private TextView mTvTextColorTagRed;
    private TextView mTvTextColorTagPinkDark;
    private TextView mTvTextColorTagOrange;
    private TextView mTvTextColorTagGreen;
    private TextView mTvTextColorTagBlueBright;
    private TextView mTvTextColorTagPurple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉默认的ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_render_color);
        findViews();
        initViews();
    }

    private void findViews(){
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
        mLayoutCaptainIndicators = findViewById(R.id.layout_captain_indicators);

        mVColorPrimary = findViewById(R.id.v_color_primary);
        mVColorPrimaryDark = findViewById(R.id.v_color_primary_dark);
        mVColorAccent = findViewById(R.id.v_color_accent);
        mVColorDividerContent = findViewById(R.id.v_color_divider_content);
        mVColorBgItem = findViewById(R.id.v_color_bg_item);
        mVColorDividerItem = findViewById(R.id.v_color_divider_item);

        mTvTextColorGray = (TextView) findViewById(R.id.tv_text_color_gray);
        mTvTextColorBlack = (TextView) findViewById(R.id.tv_text_color_black);
        mTvTextColorPrimary = (TextView) findViewById(R.id.tv_text_color_primary);
        mTvTextColorHint = (TextView) findViewById(R.id.tv_text_color_hint);
        mTvTextColorUnable = (TextView) findViewById(R.id.tv_text_color_unable);
        mTvTextColorPrice = (TextView) findViewById(R.id.tv_text_color_price);

        mVColorTagRed = findViewById(R.id.v_color_tag_red);
        mVColorTagPinkDark = findViewById(R.id.v_color_tag_pink_dark);
        mVColorTagOrange = findViewById(R.id.v_color_tag_orange);
        mVColorTagGreen = findViewById(R.id.v_color_tag_green);
        mVColorTagBlueBright = findViewById(R.id.v_color_tag_blue_bright);
        mVColorTagPurple = findViewById(R.id.v_color_tag_purple);

        mTvTextColorTagRed = (TextView) findViewById(R.id.tv_text_color_tag_red);
        mTvTextColorTagPinkDark = (TextView) findViewById(R.id.tv_text_color_tag_pink_dark);
        mTvTextColorTagOrange = (TextView) findViewById(R.id.tv_text_color_tag_orange);
        mTvTextColorTagGreen = (TextView) findViewById(R.id.tv_text_color_tag_green);
        mTvTextColorTagBlueBright = (TextView) findViewById(R.id.tv_text_color_tag_blue_bright);
        mTvTextColorTagPurple = (TextView) findViewById(R.id.tv_text_color_tag_purple);
    }

    private void initViews() {
        mToolbar.setTitle("颜色规范");

        Render render = Render.getInstance(this);
        // 设置Toolbar的颜色
        render.setBgColor(mToolbar, Render.BgColor.BG_BAR);
        mToolbar.setTitleTextColor(render.getColor(Render.TextColor.BLACK));
        // 设置底部Tab栏的背景色
        render.setBgColor(mLayoutCaptainIndicators, Render.BgColor.BG_BAR);
        // 设置背景色
        render.setBgColor(mVColorPrimary, Render.BgColor.PRIMARY);
        render.setBgColor(mVColorPrimaryDark, Render.BgColor.PRIMARY_DARK);
        render.setBgColor(mVColorAccent, Render.BgColor.ACCENT);
        render.setBgColor(mVColorDividerContent, Render.BgColor.DIVIDER_CONTENT);
        render.setBgColor(mVColorBgItem, Render.BgColor.BG_ITEM);
        render.setBgColor(mVColorDividerItem, Render.BgColor.DIVIDER_ITEM);
        // 设置文字颜色
        render.setTextColor(mTvTextColorGray, Render.TextColor.GRAY);
        render.setTextColor(mTvTextColorBlack, Render.TextColor.BLACK);
        render.setTextColor(mTvTextColorPrimary, Render.TextColor.PRIMARY);
        render.setTextColor(mTvTextColorHint, Render.TextColor.HINT);
        render.setTextColor(mTvTextColorUnable, Render.TextColor.UNABLE);
        render.setTextColor(mTvTextColorPrice, Render.TextColor.PRICE);
        // 设置标记色
        render.setBgColor(mVColorTagRed, Render.BgColor.TAG_RED);
        render.setTextColor(mTvTextColorTagRed, Render.TextColor.TAG_RED);
        render.setBgColor(mVColorTagPinkDark, Render.BgColor.TAG_PINK_DARK);
        render.setTextColor(mTvTextColorTagPinkDark, Render.TextColor.TAG_PINK_DARK);
        render.setBgColor(mVColorTagOrange, Render.BgColor.TAG_ORANGE);
        render.setTextColor(mTvTextColorTagOrange, Render.TextColor.TAG_ORANGE);
        render.setBgColor(mVColorTagGreen, Render.BgColor.TAG_GREEN);
        render.setTextColor(mTvTextColorTagGreen, Render.TextColor.TAG_GREEN);
        render.setBgColor(mVColorTagBlueBright, Render.BgColor.TAG_BLUE_BRIGHT);
        render.setTextColor(mTvTextColorTagBlueBright, Render.TextColor.TAG_BLUE_BRIGHT);
        render.setBgColor(mVColorTagPurple, Render.BgColor.TAG_PURPLE);
        render.setTextColor(mTvTextColorTagPurple, Render.TextColor.TAG_PURPLE);
    }

}