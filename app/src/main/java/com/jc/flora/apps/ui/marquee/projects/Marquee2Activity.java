package com.jc.flora.apps.ui.marquee.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.marquee.widget.AutoSwitchTextView;

/**
 * Created by Samurai on 2017/6/8.
 */
public class Marquee2Activity extends AppCompatActivity{

    private AutoSwitchTextView mAutoSwitchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("垂直跑马灯");
        setContentView(R.layout.activity_marquee2);
        initViews();
    }

    private void initViews() {
        mAutoSwitchTextView = (AutoSwitchTextView) findViewById(R.id.asv);
        NoticeSwitchAdapter adapter = new NoticeSwitchAdapter(null, this);
        mAutoSwitchTextView.setAdapter(adapter);
        mAutoSwitchTextView.start();
    }

    @Override
    protected void onDestroy() {
        mAutoSwitchTextView.stop();
        super.onDestroy();
    }

}
