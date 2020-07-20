package com.jc.flora.apps.scene.preview.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jc.flora.R;

/**
 * Created by Shijincheng on 2019/4/11.
 */

public class Preview1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("单个简单预览");
        setContentView(R.layout.activity_preview1);
        initViews();
    }

    private void initViews(){
        ImageView ivImage = findViewById(R.id.iv_image);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleEasyPreviewActivity.route(Preview1Activity.this, R.drawable.bg_01);
            }
        });
    }

}