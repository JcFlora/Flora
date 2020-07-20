package com.jc.flora.apps.component.router.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2018/12/28.
 */
public class FragmentRouterActivity extends AppCompatActivity {

    private String mShareData = "Test";

    public String getShareData() {
        return mShareData;
    }

    public void setShareData(String shareData) {
        mShareData = shareData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Fragment的路由跳转");
        setContentView(R.layout.activity_router_fragment);
        initRootFragment();
    }

    private void initRootFragment(){
        RootFragment rootFragment = new RootFragment();
        // 设置根控制器
        RouterUtil.setRootFragmentForActivity(this, rootFragment, R.id.layout_fragment);
    }

}