package com.jc.flora.apps.component.render.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.jc.flora.R;
import com.jc.flora.apps.component.render.delegate.DebounceDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.util.Random;

/**
 * Created by Shijincheng on 2018/6/5.
 */

public class RenderDebounceActivity extends AppCompatActivity {

    private DebounceDelegate mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("防抖动");
        setContentView(R.layout.activity_render_debounce);
        mDelegate = new DebounceDelegate();
        initViews();
    }

    private void initViews() {
        Button btn = (Button) findViewById(R.id.btn);
        btn.setText("防抖动测试");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomInt = new Random().nextInt(1_000_000);
                ToastDelegate.show(RenderDebounceActivity.this, "防抖动测试"+randomInt);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (mDelegate != null && mDelegate.isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
