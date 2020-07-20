package com.jc.flora.apps.scene.bluetooth.delegate;

import android.bluetooth.BluetoothAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import java.lang.reflect.Method;

/**
 * Created by shijincheng on 2017/5/10.
 */
public class DiscoverableEnabler9 implements BluetoothEnabler9.OnBtStateChangeListener{

    private AppCompatActivity mActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private SwitchCompat mSwitchBtDiscoverable;

    public void setSwitchBtDiscoverable(SwitchCompat switchBtDiscoverable) {
        mSwitchBtDiscoverable = switchBtDiscoverable;
    }

    public void init() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        initViews();
    }

    public void setActivity(AppCompatActivity activity) {
        mActivity = activity;
    }

    private void initViews(){
        mSwitchBtDiscoverable.setChecked(isDiscoverable());
        mSwitchBtDiscoverable.setText(isDiscoverable() ? "可被发现" : "不被发现");
        mSwitchBtDiscoverable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setText(isChecked ? "可被发现" : "不被发现");
                setDiscoverable(isChecked);
            }
        });
    }

    private boolean isDiscoverable(){
        return mBluetoothAdapter != null && mBluetoothAdapter.getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE;
    }

    private void setDiscoverable(boolean enable){
        if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()){
            return;
        }
        if(enable && !isDiscoverable()){
            setDiscoverable();
//            Log.e("isDiscoverable",isDiscoverable()+"");
        }else if(!enable && isDiscoverable()){
            closeDiscoverable();
//            Log.e("isDiscoverable",isDiscoverable()+"");
        }
    }

    public void setDiscoverable() {
        try {
            Method setDiscoverableTimeout = BluetoothAdapter.class.getMethod("setDiscoverableTimeout", int.class);
            setDiscoverableTimeout.setAccessible(true);
            Method setScanMode = BluetoothAdapter.class.getMethod("setScanMode", int.class, int.class);
            setScanMode.setAccessible(true);
            setDiscoverableTimeout.invoke(mBluetoothAdapter, 0);
            setScanMode.invoke(mBluetoothAdapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeDiscoverable() {
        try {
            Method setDiscoverableTimeout = BluetoothAdapter.class.getMethod("setDiscoverableTimeout", int.class);
            setDiscoverableTimeout.setAccessible(true);
            Method setScanMode = BluetoothAdapter.class.getMethod("setScanMode", int.class, int.class);
            setScanMode.setAccessible(true);
            setDiscoverableTimeout.invoke(mBluetoothAdapter, 1);
            setScanMode.invoke(mBluetoothAdapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStateOff() {
        mSwitchBtDiscoverable.setVisibility(View.GONE);
    }

    @Override
    public void onStateTurningOn() {

    }

    @Override
    public void onStateOn() {
        mSwitchBtDiscoverable.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateTurningOff() {

    }

    @Override
    public void onStateError() {
        mSwitchBtDiscoverable.setVisibility(View.GONE);
    }
}
