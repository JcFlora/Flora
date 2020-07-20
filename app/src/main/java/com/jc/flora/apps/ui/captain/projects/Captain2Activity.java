package com.jc.flora.apps.ui.captain.projects;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2017/5/2.
 */
public class Captain2Activity extends AppCompatActivity {

    private static final int COLOR_CHECKED = Color.parseColor("#d12147");
    private static final int COUNT = 5;
    private static final int[] TAB_RES = {R.id.btn_tab_home, R.id.btn_tab_category,
            R.id.btn_tab_discovery, R.id.btn_tab_cart, R.id.btn_tab_kaola};
    private static final int[] ICON_RES_NORMAL = {R.drawable.captain_home_normal,
            R.drawable.captain_category_normal, R.drawable.captain_discovery_normal,
            R.drawable.captain_cart_normal, R.drawable.captain_kaola_normal};
    private static final int[] ICON_RES_FOCUS = {R.drawable.captain_home_focus,
            R.drawable.captain_category_focus, R.drawable.captain_discovery_focus,
            R.drawable.captain_cart_focus, R.drawable.captain_kaola_focus};
    private TextView[] mBtnTabs = new TextView[COUNT];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("导航按钮状态切换");
        setContentView(R.layout.activity_captain1);
        initViews();
    }

    private void initViews(){
        for (int i = 0; i < COUNT; i++) {
            mBtnTabs[i] = (TextView) findViewById(TAB_RES[i]);
            final int j = i;
            mBtnTabs[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearAllChecked();
                    onChecked(j);
                }
            });
        }
    }

    private void clearAllChecked() {
        for (int i = 0; i < COUNT; i++) {
            mBtnTabs[i].setTextColor(Color.BLACK);
            mBtnTabs[i].setCompoundDrawablesWithIntrinsicBounds(0, ICON_RES_NORMAL[i], 0, 0);
        }
    }

    private void onChecked(int position) {
        mBtnTabs[position].setTextColor(COLOR_CHECKED);
        mBtnTabs[position].setCompoundDrawablesWithIntrinsicBounds(0, ICON_RES_FOCUS[position], 0, 0);
    }

}
