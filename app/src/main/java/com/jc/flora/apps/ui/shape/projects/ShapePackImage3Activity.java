package com.jc.flora.apps.ui.shape.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.shape.widget.ClipImageView;

/**
 * Created by Samurai on 2017/8/9.
 */
public class ShapePackImage3Activity extends AppCompatActivity {

    private ClipImageView mShaderImageView, mShaderImageView2,
            mShaderImageView3, mShaderImageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("图片容器（基于ClipPath）");
        setContentView(R.layout.activity_shape_pack_image3);
        findViews();
        initViews();
    }

    private void findViews() {
        mShaderImageView = (ClipImageView) findViewById(R.id.iv_pack_image);
        mShaderImageView2 = (ClipImageView) findViewById(R.id.iv_pack_image2);
        mShaderImageView3 = (ClipImageView) findViewById(R.id.iv_pack_image3);
        mShaderImageView4 = (ClipImageView) findViewById(R.id.iv_pack_image4);
    }

    private void initViews() {
        mShaderImageView.setCornerRadius(60);
        mShaderImageView2.setCornerRadius(60);
        mShaderImageView2.setBorder(0xff33b5e5, 10);
        mShaderImageView3.setBorder(0xff33b5e5, 10);
        mShaderImageView4.setBorder(0xff33b5e5, 10);
    }

}