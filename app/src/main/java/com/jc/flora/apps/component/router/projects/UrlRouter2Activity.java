package com.jc.flora.apps.component.router.projects;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.router.delegate.UrlRouter2Delegate;

/**
 * Created by shijincheng on 2019/1/14.
 */
public class UrlRouter2Activity extends AppCompatActivity {

    private static final String[] URLS =
            {"https://m.baidu.com/index.html?@target=launcher",
            "https://m.baidu.com/index.html?@target=notFound",
            "https://m.baidu.com/index.html?@target=notFound2",
            "https://m.baidu.com/index.html?@target=withData&from=UrlRouter2Activity",
            "https://m.baidu.com/index.html",
            "https://m.baidu.com/index.html?@target=unknown"};

    private TextView mTvUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("封装成UrlRouterDelegate");
        setContentView(R.layout.activity_router_url);
        initViews();
    }

    private void initViews(){
        mTvUrl = findViewById(R.id.tv_url);
        mTvUrl.setText(URLS[0]);
    }

    public void configUrl(View v){
        showUrlListDialog();
    }

    public void routePage(View v){
        UrlRouter2Delegate.routePage(this, mTvUrl.getText().toString().trim());
    }

    private void showUrlListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("配置Url");
        builder.setItems(URLS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mTvUrl.setText(URLS[which]);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

}
