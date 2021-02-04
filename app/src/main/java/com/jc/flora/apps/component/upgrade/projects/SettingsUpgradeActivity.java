package com.jc.flora.apps.component.upgrade.projects;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.jc.flora.R;
import com.jc.flora.apps.component.upgrade.api.AppUpgradeInfo;
import com.jc.flora.apps.component.upgrade.api.CheckVersionUpgradeApi;
import com.jc.flora.apps.component.upgrade.delegate.RenovateDelegate;

/**
 * Created by shijincheng on 2017/3/1.
 */
public class SettingsUpgradeActivity extends AppCompatActivity {

    private Button mBtnCheckUpgrade;
    /** 版本升级器组件 */
    private RenovateDelegate mRenovateDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("设置页手动检测升级");
        setContentView(R.layout.activity_upgrade_settings);
        initViews();
        initDelegate();
        requestGetUpgradeInfo();
    }

    private void initViews(){
        mBtnCheckUpgrade = findViewById(R.id.btn_check_upgrade);
        SwitchCompat sAutoCheckUpgrade = (SwitchCompat) findViewById(R.id.s_auto_check_upgrade);
        sAutoCheckUpgrade.setChecked(RenovateDelegate.sAutoCheckUpgrade);
        sAutoCheckUpgrade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RenovateDelegate.sAutoCheckUpgrade = isChecked;
            }
        });
    }

    /** 业务管理初始化 */
    private void initDelegate() {
        // 初始化Renovate
        mRenovateDelegate = new RenovateDelegate(this);
        mRenovateDelegate.initManualCheck();
    }

    /**
     * 发起请求：获取升级信息
     */
    private void requestGetUpgradeInfo() {
        new CheckVersionUpgradeApi(new CheckVersionUpgradeApi.Listener<AppUpgradeInfo>() {
            @Override
            public void onResponse(AppUpgradeInfo response) {
                if(response.needUpdate){
                    mBtnCheckUpgrade.setText("检测升级（有新版本："+response.getNewVersionName()+"）");
                }
            }
        }, new CheckVersionUpgradeApi.ErrorListener() {
            @Override
            public void onErrorResponse() {
            }
        }).sendRequest();
    }

    public void checkUpgrade(View v) {
        // 开始升级检测的业务
        mRenovateDelegate.start();
    }

}