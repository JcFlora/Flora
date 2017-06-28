package com.jc.flora.apps.scene.bluetooth.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.bluetooth.delegate.BluetoothDelegate2;

/**
 * Created by shijincheng on 2017/5/8.
 * uses-permission android:name="android.permission.BLUETOOTH"
 * uses-permission android:name="android.permission.BLUETOOTH_ADMIN"
 */
public class Bluetooth2Activity extends AppCompatActivity {

    private BluetoothDelegate2 mBluetoothDelegate;
    private SwitchCompat mSwitchBt;
    private TextView mTvBtTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("监控蓝牙开关状态");
        setContentView(R.layout.activity_bluetooth1);
        initBluetooth();
        initViews();
    }

    private void initBluetooth() {
        mBluetoothDelegate = new BluetoothDelegate2();
        mBluetoothDelegate.setBtStateChangeReceiver(mMyReceiver);
        mBluetoothDelegate.init();
        mBluetoothDelegate.addToActivity(this, "bluetoothDelegate");
    }

    private void initViews() {
        mSwitchBt = (SwitchCompat) findViewById(R.id.s_bluetooth);
        mTvBtTip = (TextView) findViewById(R.id.tv_bluetooth_tip);
        mTvBtTip.setVisibility(mBluetoothDelegate.isOff() ? View.VISIBLE : View.GONE);
        mSwitchBt.setEnabled(mBluetoothDelegate.hasBluetooth());
        mSwitchBt.setChecked(mBluetoothDelegate.isEnabled());
        mSwitchBt.setText(mBluetoothDelegate.isEnabled() ? "开启" : "关闭");
        mSwitchBt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setText(isChecked ? "开启" : "关闭");
                buttonView.setEnabled(false);
                mBluetoothDelegate.setBluetoothEnable(isChecked);
                mTvBtTip.setText(isChecked ? "正在打开蓝牙。。。" : "正在关闭蓝牙。。。");
            }
        });
    }

    private BluetoothDelegate2.BtStateChangeReceiver mMyReceiver = new BluetoothDelegate2.BtStateChangeReceiver() {
        @Override
        protected void onStateOff() {
            mSwitchBt.setEnabled(true);
            mSwitchBt.setChecked(false);
            mSwitchBt.setText("关闭");
            mTvBtTip.setText("开启蓝牙后，您的设备可以与附近的其他蓝牙设备通信。");
            mTvBtTip.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onStateTurningOn() {
            mSwitchBt.setEnabled(false);
            mTvBtTip.setText("正在打开蓝牙。。。");
            mTvBtTip.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onStateOn() {
            mSwitchBt.setEnabled(true);
            mSwitchBt.setChecked(true);
            mSwitchBt.setText("开启");
            mTvBtTip.setVisibility(View.GONE);
        }
        @Override
        protected void onStateTurningOff() {
            mSwitchBt.setEnabled(false);
            mTvBtTip.setText("正在关闭蓝牙。。。");
            mTvBtTip.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onStateError() {
            mSwitchBt.setEnabled(true);
            mSwitchBt.setChecked(false);
            mSwitchBt.setText("关闭");
            mTvBtTip.setVisibility(View.GONE);
        }
    };

}
