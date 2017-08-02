package com.jc.flora.apps.ui.shape.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.shape.delegate.ShapeDelegate;
import com.jc.flora.apps.ui.shape.widget.PackCornerLayout;

/**
 * Created by Samurai on 2017/7/31.
 */
public class ShapePackCornerActivity extends AppCompatActivity {

    private PackCornerLayout mPackCornerLayout,mPackCornerLayout2;
    private View mVPackCornerBorder;

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
        mVPackCornerBorder = findViewById(R.id.v_pack_corner_border);
    }

    private void initViews() {
        // 在前景上覆盖一个圆角线框，注意这个背景的圆角半径必须要比PackCornerLayout的略小
        mVPackCornerBorder.setBackground(ShapeDelegate.getStrokeCornerDrawable(50, 15, 0xff33b5e5));
        mPackCornerLayout.setRadius(60);
        mPackCornerLayout2.setRadius(60);
    }

}