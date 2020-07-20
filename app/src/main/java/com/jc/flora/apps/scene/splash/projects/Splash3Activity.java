package com.jc.flora.apps.scene.splash.projects;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.splash.delegate.Splash3Delegate;
import com.jc.flora.apps.scene.splash.delegate.TestTaskDelegate;

/**
 * Created by shijincheng on 2017/1/17.
 */
public class Splash3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        startTaskThenSplash();
    }

    private void initView(){
        ImageView iv = new ImageView(this);
        setContentView(iv);
        iv.setBackgroundResource(R.drawable.splash_bg);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        iv.startAnimation(animation);
    }

    private void startTaskThenSplash(){
        // 示例：数据请求任务业务管理
        final TestTaskDelegate testTaskDelegate = new TestTaskDelegate();
        // 示例：闪屏业务管理
        final Splash3Delegate splashDelegate = new Splash3Delegate();
        splashDelegate.addToActivity(this, "splashDelegate");
        testTaskDelegate.startTask(new TestTaskDelegate.OnTaskCompletedCallback() {
            @Override
            public void onResponse(int data) {
                splashDelegate.setTargetActivity(data == 0 ? TestMainActivity.class : TestLoginActivity.class);
                splashDelegate.startForwardWhenTaskCompleted(testTaskDelegate.getDuration());
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 直接返回true，是为了屏蔽back键
        return true;
    }

}
