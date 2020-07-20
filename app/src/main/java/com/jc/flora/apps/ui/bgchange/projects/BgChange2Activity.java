package com.jc.flora.apps.ui.bgchange.projects;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.bgchange.delegate.BgChange2Delegate;

/**
 * Created by shijincheng on 2017/2/4.
 */
public class BgChange2Activity extends AppCompatActivity {

    private View mVContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgchange1);
        mVContent = findViewById(R.id.layout_content);
        BgChange2Delegate.getInstance();
        findViewById(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BgChange2Activity.this,TestBgChange2Activity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BgChange2Delegate.getInstance().onResume(mVContent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BgChange2Delegate.getInstance().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BgChange2Delegate.getInstance().destroy();
    }
}