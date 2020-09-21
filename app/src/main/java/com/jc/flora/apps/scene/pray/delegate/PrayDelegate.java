package com.jc.flora.apps.scene.pray.delegate;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Samurai on 2020/9/17.
 */
public class PrayDelegate {

    private AppCompatActivity mActivity;
    private ArrayList<View> mPrayViews = new ArrayList<>();
    private Paint mPrayPaint;

    public PrayDelegate(AppCompatActivity activity) {
        mActivity = activity;
        mPrayViews.add(activity.getWindow().getDecorView());
        initPrayPaint();
    }

    private void initPrayPaint(){
        mPrayPaint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        mPrayPaint.setColorFilter(new ColorMatrixColorFilter(cm));
    }

    public void registerDialogContentView(View v){
        if(v != null){
            mPrayViews.add(v);
        }
    }

    public void pray(){
        for (View prayView : mPrayViews) {
            prayView.setLayerType(View.LAYER_TYPE_HARDWARE, mPrayPaint);
        }
    }

    public void clear(){
        for (View prayView : mPrayViews) {
            prayView.setLayerType(View.LAYER_TYPE_NONE, null);
        }
    }

}