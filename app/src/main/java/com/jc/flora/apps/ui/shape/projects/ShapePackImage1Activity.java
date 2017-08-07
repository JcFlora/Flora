package com.jc.flora.apps.ui.shape.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.shape.widget.XferImageView;

/**
 * Created by Samurai on 2017/8/2.
 */
public class ShapePackImage1Activity extends AppCompatActivity {

    private XferImageView mXferImageView, mXferImageView2,
            mXferImageView3, mXferImageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("图片容器（基于XferMode）");
        setContentView(R.layout.activity_shape_pack_image1);
        findViews();
        initViews();
    }

    private void findViews() {
        mXferImageView = (XferImageView) findViewById(R.id.iv_pack_image);
        mXferImageView2 = (XferImageView) findViewById(R.id.iv_pack_image2);
        mXferImageView3 = (XferImageView) findViewById(R.id.iv_pack_image3);
        mXferImageView4 = (XferImageView) findViewById(R.id.iv_pack_image4);
    }

    private void initViews() {
        mXferImageView.setCornerRadius(60);
        mXferImageView2.setCornerRadius(60);
        mXferImageView2.setBorder(0xff33b5e5, 10);
        mXferImageView3.setBorder(0xff33b5e5, 10);
        mXferImageView4.setBorder(0xff33b5e5, 10);
    }

}