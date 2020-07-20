package com.jc.flora.apps.ui.typeface.projects;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.jc.flora.R;

/**
 * Created by Samurai on 2017/9/11.
 */
public class PhoneticPinyin1Activity extends AppCompatActivity {

    private TextView mTvIpa1, mTvIpa2, mTvIpa3, mTvIpa4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("显示汉语拼音（单独输入）");
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
        Typeface face = Typeface.createFromAsset(getAssets(), "font/pinyin.ttf");
        mTvIpa1.setTypeface(face);
        mTvIpa2.setTypeface(face);
        mTvIpa3.setTypeface(face);
        mTvIpa4.setTypeface(face);

        mTvIpa1.setText("é  é  é，qū xiàng xiàng tiān gē。");
        mTvIpa2.setText("鹅，鹅，鹅，曲项向天歌。");
        mTvIpa3.setText("bái máo fú lǜ shuǐ，hóng zhǎng bō qīng bō。");
        mTvIpa4.setText("白毛浮绿水，红掌拨清波。");
    }

}
