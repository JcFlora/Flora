package com.jc.flora.apps.ui.transition.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.component.router.projects.RouterUtil;

/**
 * Created by shijincheng on 2018/12/28.
 */
public class FragmentTransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Fragment的转场动画");
        setContentView(R.layout.activity_router_fragment);
        initRootFragment();
    }

    private void initRootFragment(){
        TransitionFragment rootFragment = new TransitionFragment();
        // 设置根控制器
        RouterUtil.setRootFragmentForActivity(this, rootFragment, R.id.layout_fragment);
    }

}