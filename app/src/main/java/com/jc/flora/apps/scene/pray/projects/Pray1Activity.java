package com.jc.flora.apps.scene.pray.projects;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.jc.flora.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/9/17.
 */
public class Pray1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("单页面置灰");
        setContentView(R.layout.activity_pray1);
    }

    public void openGrayFilter(View v){
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }

    public void closeGrayFilter(View v){
        getWindow().getDecorView().setLayerType(View.LAYER_TYPE_NONE, null);
    }

}