package com.jc.flora.apps.scene.bluetooth.delegate;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jc.flora.apps.scene.bluetooth.projects.BluetoothDevicesAdapter4;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/5/9.
 */
public class BluetoothScanner6 implements BluetoothEnabler5.OnBtStateChangeListener{

    private AppCompatActivity mActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private View mLayoutBondedDevices;
    private RecyclerView mRvBondedDevices;

    public void setLayoutBondedDevices(View layoutBondedDevices) {
        mLayoutBondedDevices = layoutBondedDevices;
    }

    public void setRvBondedDevices(RecyclerView rvBondedDevices) {
        mRvBondedDevices = rvBondedDevices;
    }

    public void init() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void setActivity(AppCompatActivity activity) {
        mActivity = activity;
    }

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
        setBondedDevices();
    }

    @Override
    public void onStateTurningOff() {
        mLayoutBondedDevices.setVisibility(View.GONE);
    }

    @Override
    public void onStateError() {
        mLayoutBondedDevices.setVisibility(View.GONE);
    }

}
