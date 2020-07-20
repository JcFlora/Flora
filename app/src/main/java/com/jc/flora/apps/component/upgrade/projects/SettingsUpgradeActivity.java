package com.jc.flora.apps.component.upgrade.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.jc.flora.R;
import com.jc.flora.apps.component.upgrade.renovate.Renovate;

/**
 * Created by shijincheng on 2017/3/1.
 */
public class SettingsUpgradeActivity extends AppCompatActivity {

    /** 版本升级器 */
    private Renovate mRenovate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("设置页手动检测升级");
        setContentView(R.layout.activity_upgrade_settings);
        initViews();
        initDelegate();
    }

    private void initViews(){
        SwitchCompat sAutoCheckUpgrade = (SwitchCompat) findViewById(R.id.s_auto_check_upgrade);
        sAutoCheckUpgrade.setChecked(!Renovate.sIsUserRefuseUpgrade);
        sAutoCheckUpgrade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Renovate.sIsUserRefuseUpgrade = !isChecked;
            }
        });
    }

    /** 业务管理初始化 */
    private void initDelegate() {
        mRenovate = Renovate.create(this);
    }

    public void checkUpgrade(View v) {
        // 开始升级检测的业务
        mRenovate.manualCheck();
    }

}