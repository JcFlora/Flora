package com.jc.flora.apps.scene.bluetooth.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.bluetooth.delegate.BluetoothDelegate6;

/**
 * Created by shijincheng on 2017/5/9.
 * uses-permission android:name="android.permission.BLUETOOTH"
 * uses-permission android:name="android.permission.BLUETOOTH_ADMIN"
 */
public class Bluetooth6Activity extends AppCompatActivity {

    private BluetoothDelegate6 mBluetoothDelegate;
    private SwitchCompat mSwitchBt;
    private TextView mTvBtTip;
    private View mLayoutBondedDevices;
    private RecyclerView mRvBondedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("封装已配对设备列表");
        setContentView(R.layout.activity_bluetooth4);
        initViews();
        initBluetooth();
    }

    private void initViews() {
        mSwitchBt = (SwitchCompat) findViewById(R.id.s_bluetooth);
        mTvBtTip = (TextView) findViewById(R.id.tv_bluetooth_tip);
        mLayoutBondedDevices = findViewById(R.id.layout_bonded_devices);
        mRvBondedDevices = (RecyclerView) findViewById(R.id.rv_bonded_devices);
        mRvBondedDevices.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initBluetooth() {
        mBluetoothDelegate = new BluetoothDelegate6();
        mBluetoothDelegate.setSwitchBt(mSwitchBt);
        mBluetoothDelegate.setTvBtTip(mTvBtTip);
        mBluetoothDelegate.setLayoutBondedDevices(mLayoutBondedDevices);
        mBluetoothDelegate.setRvBondedDevices(mRvBondedDevices);
        mBluetoothDelegate.init();
        mBluetoothDelegate.addToActivity(this, "bluetoothDelegate");
    }

}
