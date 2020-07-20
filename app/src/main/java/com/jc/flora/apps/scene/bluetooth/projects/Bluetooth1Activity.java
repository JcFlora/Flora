package com.jc.flora.apps.scene.bluetooth.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.bluetooth.delegate.BluetoothDelegate1;

/**
 * Created by shijincheng on 2017/5/8.
 * uses-permission android:name="android.permission.BLUETOOTH"
 * uses-permission android:name="android.permission.BLUETOOTH_ADMIN"
 */
public class Bluetooth1Activity extends AppCompatActivity {

    private BluetoothDelegate1 mBluetoothDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("打开蓝牙");
        setContentView(R.layout.activity_bluetooth1);
        initBluetooth();
        initViews();
    }

    private void initBluetooth() {
        mBluetoothDelegate = new BluetoothDelegate1();
        mBluetoothDelegate.init();
    }

    private void initViews() {
        final SwitchCompat sBluetooth = (SwitchCompat) findViewById(R.id.s_bluetooth);
        final TextView tvBluetoothTip = (TextView) findViewById(R.id.tv_bluetooth_tip);
        tvBluetoothTip.setVisibility(mBluetoothDelegate.isOff() ? View.VISIBLE : View.GONE);
        sBluetooth.setEnabled(mBluetoothDelegate.hasBluetooth());
        sBluetooth.setChecked(mBluetoothDelegate.isEnabled());
        sBluetooth.setText(mBluetoothDelegate.isEnabled() ? "开启" : "关闭");
        sBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBluetoothDelegate.setBluetoothEnable(isChecked);
                buttonView.setText(isChecked ? "开启" : "关闭");
                tvBluetoothTip.setVisibility(isChecked ? View.GONE : View.VISIBLE);
            }
        });
    }

}
