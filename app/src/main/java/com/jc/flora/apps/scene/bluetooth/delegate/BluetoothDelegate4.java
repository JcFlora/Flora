package com.jc.flora.apps.scene.bluetooth.delegate;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/5/8.
 */
public class BluetoothDelegate4 extends Fragment {

    private AppCompatActivity mActivity;
    private BluetoothAdapter mBluetoothAdapter;
    //private BluetoothLeScanner mBluetoothLeScanner;

    public void init(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if(DeviceUtil.isSystemVersionAfterLollipop()){
//            mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
//        }
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity == null){
            return;
        }
        mActivity = activity;
        activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
    }

    public ArrayList<BluetoothDevice> getBondedDevices(){
        if(mBluetoothAdapter == null){
            return null;
        }
        return new ArrayList<>(mBluetoothAdapter.getBondedDevices());
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public void startScan(ScanCallback callback){
//        mBluetoothLeScanner.startScan(new ScanCallback() {
//            @Override
//            public void onScanResult(int callbackType, ScanResult result) {
//                super.onScanResult(callbackType, result);
//            }
//        });
//        mBluetoothAdapter.startLeScan(new BluetoothAdapter.LeScanCallback() {
//            @Override
//            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
//
//            }
//        });
//

}
