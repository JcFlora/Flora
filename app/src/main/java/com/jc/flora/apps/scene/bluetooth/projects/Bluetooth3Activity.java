package com.jc.flora.apps.scene.bluetooth.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.bluetooth.delegate.BluetoothDelegate3;

/**
 * Created by shijincheng on 2017/5/8.
 * uses-permission android:name="android.permission.BLUETOOTH"
 * uses-permission android:name="android.permission.BLUETOOTH_ADMIN"
 */
public class Bluetooth3Activity extends AppCompatActivity {

    private BluetoothDelegate3 mBluetoothDelegate;
    private SwitchCompat mSwitchBt;
    private TextView mTvBtTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("封装开关按钮");
        setContentView(R.layout.activity_bluetooth1);
        initViews();
        initBluetooth();
    }

    private void initViews() {
        mSwitchBt = (SwitchCompat) findViewById(R.id.s_bluetooth);
        mTvBtTip = (TextView) findViewById(R.id.tv_bluetooth_tip);
    }

    private void initBluetooth() {
        mBluetoothDelegate = new BluetoothDelegate3();
        mBluetoothDelegate.setSwitchBt(mSwitchBt);
        mBluetoothDelegate.setTvBtTip(mTvBtTip);
        mBluetoothDelegate.addToActivity(this, "bluetoothDelegate");
    }

}
