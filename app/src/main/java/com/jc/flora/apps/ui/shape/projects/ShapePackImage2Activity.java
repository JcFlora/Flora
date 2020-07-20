package com.jc.flora.apps.ui.shape.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.shape.widget.ShaderImageView;

/**
 * Created by Samurai on 2017/8/2.
 */
public class ShapePackImage2Activity extends AppCompatActivity {

    private ShaderImageView mShaderImageView, mShaderImageView2,
            mShaderImageView3, mShaderImageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("图片容器（基于BitmapShader）");
        setContentView(R.layout.activity_shape_pack_image2);
        findViews();
        initViews();
    }

    private void findViews() {
        mShaderImageView = (ShaderImageView) findViewById(R.id.iv_pack_image);
        mShaderImageView2 = (ShaderImageView) findViewById(R.id.iv_pack_image2);
        mShaderImageView3 = (ShaderImageView) findViewById(R.id.iv_pack_image3);
        mShaderImageView4 = (ShaderImageView) findViewById(R.id.iv_pack_image4);
    }

    private void initViews() {
        mShaderImageView.setCornerRadius(60);
        mShaderImageView2.setCornerRadius(60);
        mShaderImageView2.setBorder(0xff33b5e5, 10);
        mShaderImageView3.setBorder(0xff33b5e5, 10);
        mShaderImageView4.setBorder(0xff33b5e5, 10);
    }

}