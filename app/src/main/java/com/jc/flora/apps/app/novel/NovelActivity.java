package com.jc.flora.apps.app.novel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ProgressBar;

import com.jc.flora.R;

/**
 * Created by Samurai on 2017/6/24.
 */
public class NovelActivity extends AppCompatActivity{

    private ProgressBar mPbLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉默认的ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_novel_splash);
        mPbLoading = (ProgressBar) findViewById(R.id.pb);
        mSplashThread.start();
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private Thread mSplashThread = new Thread() {
        @Override
        public void run() {
            super.run();
            int i = 0;
            while (!(isFinishing()||isDestroyed()) && i <= 100) {
                Message msg = mHandler.obtainMessage();
                msg.arg1 = i;
                i += 2;
                mHandler.sendMessage(msg);
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                }
            }
        }
    };

    private Handler mHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mPbLoading.setProgress(msg.arg1);
            if (msg.arg1 >= 100) {
                Intent intent = new Intent(NovelActivity.this,NovelCategoryActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

}