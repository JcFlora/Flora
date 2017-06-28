package com.jc.flora.apps.scene.bluetooth.delegate;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shijincheng on 2017/5/8.
 */
public class BluetoothDelegate6 extends Fragment {

    private AppCompatActivity mActivity;
    private BluetoothEnabler5 mBluetoothEnabler;
    private BluetoothScanner6 mBluetoothScanner;

    public BluetoothDelegate6() {
        mBluetoothEnabler = new BluetoothEnabler5();
        mBluetoothScanner = new BluetoothScanner6();
    }

    public void setSwitchBt(SwitchCompat switchBt) {
        mBluetoothEnabler.setSwitchBt(switchBt);
    }

    public void setTvBtTip(TextView tvBtTip) {
        mBluetoothEnabler.setTvBtTip(tvBtTip);
    }

    public void setOnBtStateChangeListener(BluetoothEnabler5.OnBtStateChangeListener l) {
        mBluetoothEnabler.setOnBtStateChangeListener(l);
    }

    public void setLayoutBondedDevices(View layoutBondedDevices) {
        mBluetoothScanner.setLayoutBondedDevices(layoutBondedDevices);
    }

    public void setRvBondedDevices(RecyclerView rvBondedDevices) {
        mBluetoothScanner.setRvBondedDevices(rvBondedDevices);
    }

    public void init(){
        mBluetoothEnabler.init();
        mBluetoothScanner.init();
        mBluetoothEnabler.setOnBtStateChangeListener(mBluetoothScanner);
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity == null){
            return;
        }
        mActivity = activity;
        mBluetoothEnabler.setActivity(activity);
        mBluetoothScanner.setActivity(activity);
        activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
    }

    @Override
    public void onStart() {
        super.onStart();
        mBluetoothEnabler.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBluetoothEnabler.onStop();
    }

}
