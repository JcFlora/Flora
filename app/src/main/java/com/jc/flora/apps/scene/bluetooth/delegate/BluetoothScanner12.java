package com.jc.flora.apps.scene.bluetooth.delegate;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jc.flora.apps.scene.bluetooth.projects.BluetoothDevicesAdapter11;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/5/9.
 */
public class BluetoothScanner12 implements BluetoothEnabler9.OnBtStateChangeListener{

    private AppCompatActivity mActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private IntentFilter mIntentFilter;
    private View mLayoutBondedDevices;
    private View mLayoutUnbondedDevices;
    private RecyclerView mRvBondedDevices;
    private RecyclerView mRvUnbondedDevices;

    private ProgressBar mPbUnbondedDevices;
    private View mBtnRefresh;

    private ArrayList<BluetoothDevice> mUnbondedDevices = new ArrayList<>();
    private BluetoothDevicesAdapter11 mUnbondedDevicesdapter;

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

    public void setBtnRefresh(View btnRefresh) {
        mBtnRefresh = btnRefresh;
    }

    public void init() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mIntentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        mIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        mIntentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBondedDevices();
                scanDevices();
            }
        });
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
                mBtnRefresh.setVisibility(View.GONE);
            } else if (intent.getAction().equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                Toast.makeText(context, "蓝牙扫描完毕", Toast.LENGTH_SHORT).show();
                mPbUnbondedDevices.setVisibility(View.GONE);
                mBtnRefresh.setVisibility(View.VISIBLE);
            } else if(intent.getAction().equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                cancelDiscovery();
                setBondedDevices();
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                addOrRemoveUnbondedDevice(device);
            } else {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                addUnbondedDevice(device);
            }
        }
    };

    private void setBondedDevices() {
        final ArrayList<BluetoothDevice> bondedDevices = getBondedDevices();
        if(bondedDevices == null || bondedDevices.isEmpty()){
            mLayoutBondedDevices.setVisibility(View.GONE);
        }else{
            mLayoutBondedDevices.setVisibility(View.VISIBLE);
            BluetoothDevicesAdapter11 adapter = new BluetoothDevicesAdapter11(mActivity, bondedDevices);
            adapter.setOnItemClickListener(new BluetoothDevicesAdapter11.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    onDeviceClick(bondedDevices.get(position));
                }
            });
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
        mUnbondedDevicesdapter = new BluetoothDevicesAdapter11(mActivity, mUnbondedDevices);
        mUnbondedDevicesdapter.setOnItemClickListener(new BluetoothDevicesAdapter11.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onDeviceClick(mUnbondedDevices.get(position));
            }
        });
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

    private void onDeviceClick(BluetoothDevice device){
        cancelDiscovery();
        switch (device.getBondState()){
            case BluetoothDevice.BOND_NONE:
                device.createBond();
                break;
            case BluetoothDevice.BOND_BONDING:
                break;
            case BluetoothDevice.BOND_BONDED:
                showRemoveBondDialog(device);
                break;
        }
    }

    private void showRemoveBondDialog(final BluetoothDevice device){
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage("请开始你的操作！");
        builder.setPositiveButton("取消绑定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                removeBond(device);
            }
        });
        builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    private void removeBond(BluetoothDevice device) {
        try {
            Method method = BluetoothDevice.class.getMethod("removeBond");
            method.setAccessible(true);
            method.invoke(device);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addOrRemoveUnbondedDevice(BluetoothDevice device){
        if(device.getBondState() == BluetoothDevice.BOND_BONDED && mUnbondedDevices.contains(device)){
            mUnbondedDevices.remove(device);
            mUnbondedDevicesdapter.notifyDataSetChanged();
        }else if(device.getBondState() == BluetoothDevice.BOND_NONE && !mUnbondedDevices.contains(device)){
            mUnbondedDevices.add(device);
            mUnbondedDevicesdapter.notifyDataSetChanged();
        }
    }

    private void addUnbondedDevice(BluetoothDevice device){
        if(device.getBondState() == BluetoothDevice.BOND_NONE && !mUnbondedDevices.contains(device)){
            mUnbondedDevices.add(device);
            mUnbondedDevicesdapter.notifyDataSetChanged();
        }
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
