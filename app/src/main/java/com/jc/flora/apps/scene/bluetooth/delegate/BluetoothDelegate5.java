package com.jc.flora.apps.scene.bluetooth.delegate;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/5/8.
 */
public class BluetoothDelegate5 extends Fragment {

    private AppCompatActivity mActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothEnabler5 mBluetoothEnabler;

    public BluetoothDelegate5() {
        mBluetoothEnabler = new BluetoothEnabler5();
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

    public void init(){
        mBluetoothEnabler.init();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity == null){
            return;
        }
        mActivity = activity;
        mBluetoothEnabler.setActivity(activity);
        activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
    }

    public ArrayList<BluetoothDevice> getBondedDevices(){
        if(mBluetoothAdapter == null){
            return null;
        }
        return new ArrayList<>(mBluetoothAdapter.getBondedDevices());
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
