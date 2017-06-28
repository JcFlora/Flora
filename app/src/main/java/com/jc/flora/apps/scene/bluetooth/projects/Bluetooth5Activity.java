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
import com.jc.flora.apps.scene.bluetooth.delegate.BluetoothDelegate5;
import com.jc.flora.apps.scene.bluetooth.delegate.BluetoothEnabler5;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/5/9.
 * uses-permission android:name="android.permission.BLUETOOTH"
 * uses-permission android:name="android.permission.BLUETOOTH_ADMIN"
 */
public class Bluetooth5Activity extends AppCompatActivity {

    private BluetoothDelegate5 mBluetoothDelegate;
    private SwitchCompat mSwitchBt;
    private TextView mTvBtTip;
    private View mLayoutBondedDevices;
    private RecyclerView mRvBondedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("动态获取已配对设备");
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
        mBluetoothDelegate = new BluetoothDelegate5();
        mBluetoothDelegate.setSwitchBt(mSwitchBt);
        mBluetoothDelegate.setTvBtTip(mTvBtTip);
        mBluetoothDelegate.init();
        mBluetoothDelegate.addToActivity(this, "bluetoothDelegate");
    }

    private void initBondedDevices(){
        mBluetoothDelegate.setOnBtStateChangeListener(new BluetoothEnabler5.OnBtStateChangeListener() {
            @Override
            public void onStateOff() {
                mLayoutBondedDevices.setVisibility(View.GONE);
            }

            @Override
            public void onStateTurningOn() {
                mLayoutBondedDevices.setVisibility(View.GONE);
            }

            @Override
            public void onStateOn() {
                getBondedDevices();
            }

            @Override
            public void onStateTurningOff() {
                mLayoutBondedDevices.setVisibility(View.GONE);
            }

            @Override
            public void onStateError() {
                mLayoutBondedDevices.setVisibility(View.GONE);
            }
        });


    }

    private void getBondedDevices() {
        ArrayList<BluetoothDevice> mBondedDevices = mBluetoothDelegate.getBondedDevices();
        if(mBondedDevices == null || mBondedDevices.isEmpty()){
            mLayoutBondedDevices.setVisibility(View.GONE);
        }else{
            mLayoutBondedDevices.setVisibility(View.VISIBLE);
            BluetoothDevicesAdapter4 adapter = new BluetoothDevicesAdapter4(this, mBondedDevices);
            mRvBondedDevices.setAdapter(adapter);
        }
    }

}
