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
import com.jc.flora.apps.scene.bluetooth.delegate.BluetoothDelegate10;

/**
 * Created by shijincheng on 2017/5/9.
 * uses-permission android:name="android.permission.BLUETOOTH"
 * uses-permission android:name="android.permission.BLUETOOTH_ADMIN"
 * uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"
 * uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"
 * uses-permission android:name="android.permission.WRITE_SECURE_SETTINGS"
 *
 * 扫描可用设备需要申请3、4权限，6.0以上系统需要显式动态申请这两个权限
 *
 * 由于非系统级应用无法申请第5个权限，所以无法将可检测性写入系统配置；
 * 因此，可检测性属性无法与系统设置页面同步，并且会被系统设置页面覆盖；
 * 但只要不开启系统设置页面，可检测性可以正常开启关闭。
 */
public class Bluetooth10Activity extends AppCompatActivity {

    private BluetoothDelegate10 mBluetoothDelegate;
    private SwitchCompat mSwitchBt;
    private SwitchCompat mSwitchBtDiscoverable;
    private TextView mTvBtTip;
    private View mLayoutBondedDevices;
    private View mLayoutUnbondedDevices;
    private RecyclerView mRvBondedDevices;
    private RecyclerView mRvUnbondedDevices;
    private ProgressBar mPbUnbondedDevices;
    private View mBtnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加刷新按钮");
        setContentView(R.layout.activity_bluetooth10);
        initViews();
        initBluetooth();
    }

    private void initViews() {
        mSwitchBt = (SwitchCompat) findViewById(R.id.s_bluetooth);
        mSwitchBtDiscoverable = (SwitchCompat) findViewById(R.id.s_discoverable);
        mTvBtTip = (TextView) findViewById(R.id.tv_bluetooth_tip);
        mLayoutBondedDevices = findViewById(R.id.layout_bonded_devices);
        mLayoutUnbondedDevices = findViewById(R.id.layout_unbonded_devices);
        mRvBondedDevices = (RecyclerView) findViewById(R.id.rv_bonded_devices);
        mRvUnbondedDevices = (RecyclerView) findViewById(R.id.rv_unbonded_devices);
        mPbUnbondedDevices = (ProgressBar) findViewById(R.id.pb_unbonded_devices);
        mBtnRefresh = findViewById(R.id.btn_refresh);
        mRvBondedDevices.setLayoutManager(new LinearLayoutManager(this));
        mRvUnbondedDevices.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initBluetooth() {
        mBluetoothDelegate = new BluetoothDelegate10();
        mBluetoothDelegate.setSwitchBt(mSwitchBt);
        mBluetoothDelegate.setSwitchBtDiscoverable(mSwitchBtDiscoverable);
        mBluetoothDelegate.setTvBtTip(mTvBtTip);
        mBluetoothDelegate.setLayoutBondedDevices(mLayoutBondedDevices);
        mBluetoothDelegate.setRvBondedDevices(mRvBondedDevices);
        mBluetoothDelegate.setLayoutUnbondedDevices(mLayoutUnbondedDevices);
        mBluetoothDelegate.setRvUnbondedDevices(mRvUnbondedDevices);
        mBluetoothDelegate.setPbUnbondedDevices(mPbUnbondedDevices);
        mBluetoothDelegate.setBtnRefresh(mBtnRefresh);
        mBluetoothDelegate.init();
        mBluetoothDelegate.addToActivity(this, "bluetoothDelegate");
    }

}
