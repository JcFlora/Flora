package com.jc.flora.apps.component.render.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.jc.flora.R;
import com.jc.flora.apps.component.render.delegate.Render;

/**
 * Created by Samurai on 2017/6/27.
 */
public class RenderDimen2Activity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉默认的ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_render_dimen2);
        findViews();
        initViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_title);
    }

    private void initViews() {
        mToolbar.setTitle("尺寸规范（卡片）");

        Render render = Render.getInstance(this);
        // 设置Toolbar的颜色
        mToolbar.setTitleTextColor(render.getColor(Render.TextColor.WHITE));
    }

}
