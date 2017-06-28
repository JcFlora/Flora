package com.jc.flora.apps.component.render.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.render.delegate.Render;

/**
 * Created by Samurai on 2017/6/26.
 */
public class RenderTextActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private TextView mTvTextSize12dp;
    private TextView mTvTextSize14dp;
    private TextView mTvTextSize16dp;
    private TextView mTvTextSize18dp;
    private TextView mTvTextSize20dp;

    private TextView mTvTextSize24px;
    private TextView mTvTextSize28px;
    private TextView mTvTextSize32px;
    private TextView mTvTextSize36px;
    private TextView mTvTextSize40px;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉默认的ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_render_text);
        findViews();
        initViews();
    }

    private void findViews(){
        mToolbar = (Toolbar) findViewById(R.id.tb_title);

        mTvTextSize12dp = (TextView) findViewById(R.id.tv_text_size_12dp);
        mTvTextSize14dp = (TextView) findViewById(R.id.tv_text_size_14dp);
        mTvTextSize16dp = (TextView) findViewById(R.id.tv_text_size_16dp);
        mTvTextSize18dp = (TextView) findViewById(R.id.tv_text_size_18dp);
        mTvTextSize20dp = (TextView) findViewById(R.id.tv_text_size_20dp);

        mTvTextSize24px = (TextView) findViewById(R.id.tv_text_size_24px);
        mTvTextSize28px = (TextView) findViewById(R.id.tv_text_size_28px);
        mTvTextSize32px = (TextView) findViewById(R.id.tv_text_size_32px);
        mTvTextSize36px = (TextView) findViewById(R.id.tv_text_size_36px);
        mTvTextSize40px = (TextView) findViewById(R.id.tv_text_size_40px);
    }

    private void initViews() {
        mToolbar.setTitle("字体规范");

        Render render = Render.getInstance(this);
        // 设置Toolbar的颜色
        mToolbar.setTitleTextColor(render.getColor(Render.TextColor.WHITE));
        // 设置固定字体大小
        render.setTextSize(mTvTextSize12dp,Render.TextSize._12_DP);
        render.setTextSize(mTvTextSize14dp,Render.TextSize._14_DP);
        render.setTextSize(mTvTextSize16dp,Render.TextSize._16_DP);
        render.setTextSize(mTvTextSize18dp,Render.TextSize._18_DP);
        render.setTextSize(mTvTextSize20dp,Render.TextSize._20_DP);
        // 设置适配字体大小
        render.setTextSize(mTvTextSize24px,Render.TextSize._24_PX);
        render.setTextSize(mTvTextSize28px,Render.TextSize._28_PX);
        render.setTextSize(mTvTextSize32px,Render.TextSize._32_PX);
        render.setTextSize(mTvTextSize36px,Render.TextSize._36_PX);
        render.setTextSize(mTvTextSize40px,Render.TextSize._40_PX);
    }

}