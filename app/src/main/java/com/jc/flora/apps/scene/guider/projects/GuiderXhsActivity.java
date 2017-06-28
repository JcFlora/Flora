package com.jc.flora.apps.scene.guider.projects;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.guider.parallaxpager.ParallaxContainer;
import com.jc.flora.apps.ui.stable.delegate.StableDelegate;

/**
 * Created by Samurai on 2017/6/1.
 */
public class GuiderXhsActivity extends Activity {

    private ImageView mIvGirl;
    private ParallaxContainer parallaxContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new StableDelegate(this).hideStatusBar();
        setContentView(R.layout.activity_guider_xhs);
        findViews();
        initViews();
    }

    private void findViews() {
        mIvGirl = (ImageView) findViewById(R.id.iv_girl);
        parallaxContainer = (ParallaxContainer) findViewById(R.id.parallax_container);
    }

    private void initViews() {
        parallaxContainer.setImage(mIvGirl);
        parallaxContainer.setLooping(false);
        mIvGirl.setVisibility(View.VISIBLE);
        parallaxContainer.setupChildren(getLayoutInflater(),
                R.layout.view_intro_1, R.layout.view_intro_2,
                R.layout.view_intro_3, R.layout.view_intro_4,
                R.layout.view_intro_5, R.layout.view_login);
    }

}