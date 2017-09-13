package com.jc.flora.apps.ui.typeface.projects;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jc.flora.R;

/**
 * Created by Samurai on 2017/9/13.
 */
public class PhoneticPinyin2Activity extends AppCompatActivity {

    private TextView mTvIpa1, mTvIpa2, mTvIpa3, mTvIpa4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("显示带拼音文字（基于字库）");
        setContentView(R.layout.activity_phonetic_ipa);
        findViews();
        initViews();
    }

    private void findViews() {
        mTvIpa1 = (TextView) findViewById(R.id.tv_ipa1);
        mTvIpa2 = (TextView) findViewById(R.id.tv_ipa2);
        mTvIpa3 = (TextView) findViewById(R.id.tv_ipa3);
        mTvIpa4 = (TextView) findViewById(R.id.tv_ipa4);
    }

    private void initViews() {
        Typeface face = Typeface.createFromAsset(getAssets(), "font/fzpy.ttc");
        mTvIpa1.setTypeface(face);
        mTvIpa2.setTypeface(face);
        mTvIpa3.setTypeface(face);
        mTvIpa4.setTypeface(face);

        mTvIpa1.setText("鹅，鹅，鹅，");
        mTvIpa2.setText("曲项向天歌。");
        mTvIpa3.setText("白毛浮绿水，");
        mTvIpa4.setText("红掌拨清波。");
    }

}