package com.jc.flora.apps.ui.shape.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.shape.widget.PackCornerLayout;

/**
 * Created by Samurai on 2017/7/31.
 */
public class ShapePackCornerActivity extends AppCompatActivity {

    private PackCornerLayout mPackCornerLayout, mPackCornerLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("圆角容器");
        setContentView(R.layout.activity_shape_pack_corner);
        findViews();
        initViews();
    }

    private void findViews() {
        mPackCornerLayout = (PackCornerLayout) findViewById(R.id.layout_pack_corner);
        mPackCornerLayout2 = (PackCornerLayout) findViewById(R.id.layout_pack_corner2);
    }

    private void initViews() {
        mPackCornerLayout.setRadius(60);
        mPackCornerLayout2.setRadius(60);
        // border的实际圆角半径 = radius-width/2
        mPackCornerLayout2.setBorder(0xff33b5e5, 10);
    }

}