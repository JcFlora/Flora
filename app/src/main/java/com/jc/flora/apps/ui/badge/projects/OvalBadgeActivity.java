package com.jc.flora.apps.ui.badge.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.badge.widget.BadgeView;

/**
 * Created by shijincheng on 2017/5/18.
 */
public class OvalBadgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("腰圆角标");
        setContentView(R.layout.activity_badge_oval);

        ImageView ivTarget1 = (ImageView) findViewById(R.id.iv_badge_target);
        BadgeView bv = new BadgeView(this, ivTarget1);
        bv.setBadgeNumberText(5);

        TextView ivTarget2 = (TextView) findViewById(R.id.iv_badge_target2);
        BadgeView bv2 = new BadgeView(this, ivTarget2);
        bv2.setBadgeNumberText(28);
    }

}
