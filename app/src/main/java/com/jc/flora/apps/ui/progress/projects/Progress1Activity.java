package com.jc.flora.apps.ui.progress.projects;

import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.progress.delegate.ClipDelegate;

/**
 * Created by Samurai on 2017/7/27.
 */
public class Progress1Activity extends AppCompatActivity {

    private ImageView mIvClip1, mIvClip2;
    private ClipDrawable mClipDrawable1;
    private ClipDelegate mClipDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用ClipDrawable实现");
        setContentView(R.layout.activity_progress1);
        findViews();
        initViews();
    }

    private void findViews(){
        mIvClip1 = (ImageView) findViewById(R.id.iv_clip1);
        mIvClip2 = (ImageView) findViewById(R.id.iv_clip2);
    }

    private void initViews() {
        mClipDrawable1 = (ClipDrawable) mIvClip1.getDrawable();
        mClipDrawable1.setLevel(0);
        mClipDelegate = new ClipDelegate(this);
        mClipDelegate.createClipDrawable(mIvClip2);
    }

    public void showClipImage(View v) {
        int level = mClipDrawable1.getLevel();
        if(level >= 10000){
            return;
        }
        mClipDrawable1.setLevel(level + 500);
    }

    public void startClipImage(View v) {
        mClipDelegate.start();
    }

    public void stopClipImage(View v) {
        mClipDelegate.stop();
    }

    @Override
    protected void onDestroy() {
        mClipDelegate.destroy();
        super.onDestroy();
    }

}