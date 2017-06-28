package com.jc.flora.apps.scene.bluetooth.projects;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.bluetooth.delegate.BluetoothDelegate3;
import com.jc.flora.apps.scene.bluetooth.delegate.BluetoothDelegate4;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/5/9.
 * uses-permission android:name="android.permission.BLUETOOTH"
 * uses-permission android:name="android.permission.BLUETOOTH_ADMIN"
 */
public class Bluetooth4Activity extends AppCompatActivity {

    private BluetoothDelegate3 mBluetoothDelegate3;
    private BluetoothDelegate4 mBluetoothDelegate4;
    private SwitchCompat mSwitchBt;
    private TextView mTvBtTip;
    private View mLayoutBondedDevices;
    private RecyclerView mRvBondedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("获取已配对设备");
        setContentView(R.layout.activity_bluetooth4);
        initViews();
        initBluetooth();
        initBondedDevices();
    }

    private void initViews() {
        mSwitchBt = (SwitchCompat) findViewById(R.id.s_bluetooth);
        mTvBtTip = (TextView) findViewById(R.id.tv_bluetooth_tip);
        mLayoutBondedDevices = findViewById(R.id.layout_bonded_devices);
        mRvBondedDevices = (RecyclerView) findViewById(R.id.rv_bonded_devices);
        mRvBondedDevices.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initBluetooth() {
        mBluetoothDelegate3 = new BluetoothDelegate3();
        mBluetoothDelegate3.setSwitchBt(mSwitchBt);
        mBluetoothDelegate3.setTvBtTip(mTvBtTip);
        mBluetoothDelegate3.addToActivity(this, "bluetoothDelegate3");

        mBluetoothDelegate4 = new BluetoothDelegate4();
        mBluetoothDelegate4.init();
        mBluetoothDelegate4.addToActivity(this, "bluetoothDelegate4");
    }

    private void initBondedDevices(){
        ArrayList<BluetoothDevice> mBondedDevices = mBluetoothDelegate4.getBondedDevices();
        if(mBondedDevices == null || mBondedDevices.isEmpty()){
            mLayoutBondedDevices.setVisibility(View.GONE);
        }else{
            mLayoutBondedDevices.setVisibility(View.VISIBLE);
            BluetoothDevicesAdapter4 adapter = new BluetoothDevicesAdapter4(this, mBondedDevices);
            mRvBondedDevices.setAdapter(adapter);
        }
    }

}
