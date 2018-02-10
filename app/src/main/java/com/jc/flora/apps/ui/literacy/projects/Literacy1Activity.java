package com.jc.flora.apps.ui.literacy.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.literacy.widget.LiteracyGroupView;

/**
 * Created by Samurai on 2018/1/21.
 */
public class Literacy1Activity extends AppCompatActivity {

    private static final String TEXT = "古诗一首|悯农|锄禾日当午，|汗滴禾下土，|谁知盘中餐，|粒粒皆辛苦。";
    private static final String PIN_YIN = "< _ _ >|mǐn nóng|chú hé rì dāng wǔ #|hàn dī hé xià tǔ #|shuí zhī pán zhōng cān #|lì lì jiē xīn kǔ #";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("拼音居中对齐");
        setContentView(R.layout.activity_literacy1);
        initViews();
    }

    private void initViews() {
        LiteracyGroupView lg = (LiteracyGroupView) findViewById(R.id.lg_text);
        lg.setRowGravity(Gravity.CENTER);
        lg.setData(TEXT, PIN_YIN);
        lg.setPinyinOn();
    }

}
