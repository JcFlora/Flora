package com.jc.flora.apps.scene.bluetooth.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.bluetooth.delegate.BluetoothDelegate7;

/**
 * Created by shijincheng on 2017/5/9.
 * uses-permission android:name="android.permission.BLUETOOTH"
 * uses-permission android:name="android.permission.BLUETOOTH_ADMIN"
 * uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"
 * uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"
 *
 * 扫描可用设备需要申请3、4权限，6.0以上系统需要显式动态申请这两个权限
 */
public class Bluetooth7Activity extends AppCompatActivity {

    private BluetoothDelegate7 mBluetoothDelegate;
    private SwitchCompat mSwitchBt;
    private TextView mTvBtTip;
    private View mLayoutBondedDevices;
    private View mLayoutUnbondedDevices;
    private RecyclerView mRvBondedDevices;
    private RecyclerView mRvUnbondedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("扫描可用设备");
        setContentView(R.layout.activity_bluetooth7);
        initViews();
        initBluetooth();
    }

    private void initViews() {
        mSwitchBt = (SwitchCompat) findViewById(R.id.s_bluetooth);
        mTvBtTip = (TextView) findViewById(R.id.tv_bluetooth_tip);
        mLayoutBondedDevices = findViewById(R.id.layout_bonded_devices);
        mLayoutUnbondedDevices = findViewById(R.id.layout_unbonded_devices);
        mRvBondedDevices = (RecyclerView) findViewById(R.id.rv_bonded_devices);
        mRvUnbondedDevices = (RecyclerView) findViewById(R.id.rv_unbonded_devices);
        mRvBondedDevices.setLayoutManager(new LinearLayoutManager(this));
        mRvUnbondedDevices.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initBluetooth() {
        mBluetoothDelegate = new BluetoothDelegate7();
        mBluetoothDelegate.setSwitchBt(mSwitchBt);
        mBluetoothDelegate.setTvBtTip(mTvBtTip);
        mBluetoothDelegate.setLayoutBondedDevices(mLayoutBondedDevices);
        mBluetoothDelegate.setRvBondedDevices(mRvBondedDevices);
        mBluetoothDelegate.setLayoutUnbondedDevices(mLayoutUnbondedDevices);
        mBluetoothDelegate.setRvUnbondedDevices(mRvUnbondedDevices);
        mBluetoothDelegate.init();
        mBluetoothDelegate.addToActivity(this, "bluetoothDelegate");
    }

}
