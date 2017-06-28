package com.jc.flora.apps.scene.bluetooth.delegate;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by shijincheng on 2017/5/8.
 */
public class BluetoothDelegate1{

    private BluetoothAdapter mBluetoothAdapter;

    public void init(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean hasBluetooth(){
        return mBluetoothAdapter != null;
    }

    public boolean isEnabled(){
        return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled();
    }

    public boolean isOff(){
        return mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled();
    }

    public void setBluetoothEnable(boolean enable){
        if(mBluetoothAdapter == null){
            return;
        }
        if(enable && !mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.enable();
        }else if(!enable && mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.disable();
        }
    }

}
