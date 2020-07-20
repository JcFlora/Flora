package com.jc.flora.apps.scene.bluetooth.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.bluetooth.delegate.BluetoothDelegate8;

/**
 * Created by shijincheng on 2017/5/9.
 * uses-permission android:name="android.permission.BLUETOOTH"
 * uses-permission android:name="android.permission.BLUETOOTH_ADMIN"
 * uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"
 * uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"
 *
 * 扫描可用设备需要申请3、4权限，6.0以上系统需要显式动态申请这两个权限
 */
public class Bluetooth8Activity extends AppCompatActivity {

    private BluetoothDelegate8 mBluetoothDelegate;
    private SwitchCompat mSwitchBt;
    private TextView mTvBtTip;
    private View mLayoutBondedDevices;
    private View mLayoutUnbondedDevices;
    private RecyclerView mRvBondedDevices;
    private RecyclerView mRvUnbondedDevices;
    private ProgressBar mPbUnbondedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加扫描中进度条");
        setContentView(R.layout.activity_bluetooth8);
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
        mPbUnbondedDevices = (ProgressBar) findViewById(R.id.pb_unbonded_devices);
        mRvBondedDevices.setLayoutManager(new LinearLayoutManager(this));
        mRvUnbondedDevices.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initBluetooth() {
        mBluetoothDelegate = new BluetoothDelegate8();
        mBluetoothDelegate.setSwitchBt(mSwitchBt);
        mBluetoothDelegate.setTvBtTip(mTvBtTip);
        mBluetoothDelegate.setLayoutBondedDevices(mLayoutBondedDevices);
        mBluetoothDelegate.setRvBondedDevices(mRvBondedDevices);
        mBluetoothDelegate.setLayoutUnbondedDevices(mLayoutUnbondedDevices);
        mBluetoothDelegate.setRvUnbondedDevices(mRvUnbondedDevices);
        mBluetoothDelegate.setPbUnbondedDevices(mPbUnbondedDevices);
        mBluetoothDelegate.init();
        mBluetoothDelegate.addToActivity(this, "bluetoothDelegate");
    }

}
