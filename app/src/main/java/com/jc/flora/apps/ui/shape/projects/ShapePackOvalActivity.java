package com.jc.flora.apps.ui.shape.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.shape.widget.PackOvalLayout;

/**
 * Created by Samurai on 2017/7/31.
 */
public class ShapePackOvalActivity extends AppCompatActivity {

    private PackOvalLayout mPackOvalLayout, mPackOvalLayout2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("椭圆/圆形容器");
        setContentView(R.layout.activity_shape_pack_oval);
        findViews();
        initViews();
    }

    private void findViews() {
        mPackOvalLayout = (PackOvalLayout) findViewById(R.id.layout_pack_oval);
        mPackOvalLayout2 = (PackOvalLayout) findViewById(R.id.layout_pack_oval2);
    }

    private void initViews() {
        mPackOvalLayout.setBorder(0xff33b5e5, 10);
        mPackOvalLayout2.setBorder(0xff33b5e5, 10);
    }

}