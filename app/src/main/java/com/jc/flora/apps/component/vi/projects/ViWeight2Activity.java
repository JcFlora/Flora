package com.jc.flora.apps.component.vi.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.deviceinfo.DeviceUtil;

/**
 * Created by Samurai on 2017/6/7.
 */
public class ViWeight2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用Weight适配间距");
        setContentView(R.layout.activity_vi_weight2);
        TextView tv = (TextView) findViewById(R.id.tv_about_version);
        tv.setText("版本：V"+DeviceUtil.getAppVersionName(this));
    }

}
