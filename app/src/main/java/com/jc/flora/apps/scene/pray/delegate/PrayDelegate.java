package com.jc.flora.apps.scene.pray.delegate;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Created by Samurai on 2020/9/17.
 */
public class PrayDelegate {

    private AppCompatActivity mActivity;
    private Fragment mFragment;
    private ArrayList<View> mPrayViews = new ArrayList<>();
    private Paint mPrayPaint;

    public PrayDelegate(AppCompatActivity activity) {
        mActivity = activity;
        initPrayPaint();
    }

    public PrayDelegate(Fragment fragment) {
        mFragment = fragment;
        initPrayPaint();
    }

    private void initPrayPaint(){
        mPrayPaint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        mPrayPaint.setColorFilter(new ColorMatrixColorFilter(cm));
    }

    public void registerCurrentActivity(){
        if(mActivity != null){
            mPrayViews.add(mActivity.getWindow().getDecorView());
        }
    }

    public void registerCurrentFragment(){
        if(mFragment != null){
            mPrayViews.add(mFragment.getView());
        }
    }

    public void registerDialogContentView(View v){
        registerView(v);
    }

    public void registerView(View v){
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