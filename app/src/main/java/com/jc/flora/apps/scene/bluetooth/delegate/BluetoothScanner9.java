package com.jc.flora.apps.scene.bluetooth.delegate;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jc.flora.apps.scene.bluetooth.projects.BluetoothDevicesAdapter4;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/5/9.
 */
public class BluetoothScanner9 implements BluetoothEnabler9.OnBtStateChangeListener{

    private AppCompatActivity mActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private IntentFilter mIntentFilter;
    private View mLayoutBondedDevices;
    private View mLayoutUnbondedDevices;
    private RecyclerView mRvBondedDevices;
    private RecyclerView mRvUnbondedDevices;

    private ProgressBar mPbUnbondedDevices;

    private ArrayList<BluetoothDevice> mUnbondedDevices = new ArrayList<>();
    private BluetoothDevicesAdapter4 mUnbondedDevicesdapter;

    public void setLayoutBondedDevices(View layoutBondedDevices) {
        mLayoutBondedDevices = layoutBondedDevices;
    }

    public void setRvBondedDevices(RecyclerView rvBondedDevices) {
        mRvBondedDevices = rvBondedDevices;
    }

    public void setLayoutUnbondedDevices(View layoutUnbondedDevices) {
        mLayoutUnbondedDevices = layoutUnbondedDevices;
    }

    public void setRvUnbondedDevices(RecyclerView rvUnbondedDevices) {
        mRvUnbondedDevices = rvUnbondedDevices;
    }

    public void setPbUnbondedDevices(ProgressBar pbUnbondedDevices) {
        mPbUnbondedDevices = pbUnbondedDevices;
    }

    public void init() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mIntentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        mIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
    }

    public void setActivity(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void onStart() {
        registerBtScanReceiver();
    }

    private void registerBtScanReceiver() {
        if (mBluetoothAdapter == null) {
            return;
        }
        // 注册广播接收器
        mActivity.registerReceiver(mBtScanReceiver, mIntentFilter);
    }

    private BroadcastReceiver mBtScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //获取设备的发送的广播
            if (intent.getAction().equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)) {
                Toast.makeText(context, "蓝牙扫描开始", Toast.LENGTH_SHORT).show();
                mPbUnbondedDevices.setVisibility(View.VISIBLE);
            } else if (intent.getAction().equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                Toast.makeText(context, "蓝牙扫描完毕", Toast.LENGTH_SHORT).show();
                mPbUnbondedDevices.setVisibility(View.GONE);
            } else {
                //获取蓝牙设置对象
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getBondState() == BluetoothDevice.BOND_BONDED){
                    return;
                }
                if(mUnbondedDevices.contains(device)){
                    return;
                }
                //把设备放到对应的集合中
                mUnbondedDevices.add(device);
                mUnbondedDevicesdapter.notifyDataSetChanged();
            }
        }
    };

    private void setBondedDevices() {
        ArrayList<BluetoothDevice> bondedDevices = getBondedDevices();
        if(bondedDevices == null || bondedDevices.isEmpty()){
            mLayoutBondedDevices.setVisibility(View.GONE);
        }else{
            mLayoutBondedDevices.setVisibility(View.VISIBLE);
            BluetoothDevicesAdapter4 adapter = new BluetoothDevicesAdapter4(mActivity, bondedDevices);
            mRvBondedDevices.setAdapter(adapter);
        }
    }

    private ArrayList<BluetoothDevice> getBondedDevices(){
        if(mBluetoothAdapter == null){
            return null;
        }
        return new ArrayList<>(mBluetoothAdapter.getBondedDevices());
    }

    private void scanDevices(){
        if(mBluetoothAdapter == null){
            mLayoutUnbondedDevices.setVisibility(View.GONE);
            return ;
        }
        if(mBluetoothAdapter.isDiscovering()){
            return;
        }
        mLayoutUnbondedDevices.setVisibility(View.VISIBLE);
        mUnbondedDevices.clear();
        mUnbondedDevicesdapter = new BluetoothDevicesAdapter4(mActivity, mUnbondedDevices);
        mRvUnbondedDevices.setAdapter(mUnbondedDevicesdapter);
        mBluetoothAdapter.startDiscovery();
//        //这个只能扫描BLE,无法扫描传统蓝牙
//        mBluetoothAdapter.startLeScan(new BluetoothAdapter.LeScanCallback() {
//            @Override
//            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
//                if(!mUnbondedDevices.contains(device)){
//                    mUnbondedDevices.add(device);
//                }
//                mUnbondedDevicesdapter.notifyDataSetChanged();
//            }
//        });
    }

    public void onStop() {
        cancelDiscovery();
        unregisterBtStateChangeReceiver();
    }

    private void cancelDiscovery(){
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isDiscovering()) {
            return;
        }
        mBluetoothAdapter.cancelDiscovery();
    }

    private void unregisterBtStateChangeReceiver() {
        if (mBluetoothAdapter == null) {
            return;
        }
        // 注销广播接收器
        mActivity.unregisterReceiver(mBtScanReceiver);
    }

    @Override
    public void onStateOff() {
        mLayoutBondedDevices.setVisibility(View.GONE);
        mLayoutUnbondedDevices.setVisibility(View.GONE);
    }

    @Override
    public void onStateTurningOn() {
        mLayoutBondedDevices.setVisibility(View.GONE);
        mLayoutUnbondedDevices.setVisibility(View.GONE);
    }

    @Override
    public void onStateOn() {
        setBondedDevices();
        scanDevices();
    }

    @Override
    public void onStateTurningOff() {
        mLayoutBondedDevices.setVisibility(View.GONE);
        mLayoutUnbondedDevices.setVisibility(View.GONE);
    }

    @Override
    public void onStateError() {
        mLayoutBondedDevices.setVisibility(View.GONE);
        mLayoutUnbondedDevices.setVisibility(View.GONE);
    }

}
