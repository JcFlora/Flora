package com.jc.flora.apps.component.cache.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.cache.delegate.Cacher;
import com.jc.flora.apps.component.cache.delegate.UserCacher;

/**
 * Created by shijincheng on 2017/1/25.
 */
public class Cacher1Activity extends AppCompatActivity {

    private EditText mEtCache;
    private TextView mTvCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_cacher1);
        setTitle("基本数据类型的静态变量缓存");
        mEtCache = (EditText) findViewById(R.id.et_cache);
        mTvCache = (TextView) findViewById(R.id.tv_cache);
        Cacher.init(this);
        showCache();
    }

    public void saveCache(View v) {
        String cache = mEtCache.getText().toString().trim();
        UserCacher.writeUserName(cache);
    }

    public void showCache(View v) {
        showCache();
    }

    public void clearCache(View v) {
        UserCacher.clearUserName();
    }

    private void showCache(){
        String cache = UserCacher.readUserName();
        cache = TextUtils.isEmpty(cache) ? "没有缓存" : cache;
        mTvCache.setText("最新的static缓存：" + cache);
    }

}
