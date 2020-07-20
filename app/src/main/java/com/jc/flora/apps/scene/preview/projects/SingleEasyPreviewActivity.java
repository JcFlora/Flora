package com.jc.flora.apps.scene.preview.projects;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Shijincheng on 2019/4/11.
 */

public class SingleEasyPreviewActivity extends AppCompatActivity {

    private Uri mUri;
    private int mResId;

    public static void route(Context context, Uri uri){
        Intent intent = new Intent(context, SingleEasyPreviewActivity.class);
        intent.putExtra("uri", uri);
        context.startActivity(intent);
    }

    public static void route(Context context, int resId){
        Intent intent = new Intent(context, SingleEasyPreviewActivity.class);
        intent.putExtra("resId", resId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initViews();
    }

    private void initData(){
        mUri = getIntent().getParcelableExtra("uri");
        mResId = getIntent().getIntExtra("resId", 0);
    }

    private void initViews(){
        ImageView iv = new ImageView(this);
        iv.setBackgroundColor(Color.WHITE);
        if(mUri != null){
            Glide.with(iv.getContext())
                    .asBitmap()
                    .load(mUri)
                    .apply(new RequestOptions().fitCenter())
                    .into(iv);
        }else if(mResId > 0){
            iv.setImageResource(mResId);
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setContentView(iv);
    }

}
