package com.jc.flora.apps.scene.bluetooth.delegate;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by shijincheng on 2017/5/8.
 */
public class BluetoothDelegate11 extends Fragment {

    private AppCompatActivity mActivity;
    private BluetoothEnabler9 mBluetoothEnabler;
    private DiscoverableEnabler9 mDiscoverableEnabler;
    private BluetoothScanner11 mBluetoothScanner;

    public BluetoothDelegate11() {
        mBluetoothEnabler = new BluetoothEnabler9();
        mDiscoverableEnabler = new DiscoverableEnabler9();
        mBluetoothScanner = new BluetoothScanner11();
    }

    public void setSwitchBt(SwitchCompat switchBt) {
        mBluetoothEnabler.setSwitchBt(switchBt);
    }

    public void setTvBtTip(TextView tvBtTip) {
        mBluetoothEnabler.setTvBtTip(tvBtTip);
    }

    public void setSwitchBtDiscoverable(SwitchCompat switchBtDiscoverable) {
        mDiscoverableEnabler.setSwitchBtDiscoverable(switchBtDiscoverable);
    }

    public void addOnBtStateChangeListener(BluetoothEnabler9.OnBtStateChangeListener l) {
        mBluetoothEnabler.addOnBtStateChangeListener(l);
    }

    public void setLayoutBondedDevices(View layoutBondedDevices) {
        mBluetoothScanner.setLayoutBondedDevices(layoutBondedDevices);
    }

    public void setRvBondedDevices(RecyclerView rvBondedDevices) {
        mBluetoothScanner.setRvBondedDevices(rvBondedDevices);
    }

    public void setLayoutUnbondedDevices(View layoutUnbondedDevices) {
        mBluetoothScanner.setLayoutUnbondedDevices(layoutUnbondedDevices);
    }

    public void setRvUnbondedDevices(RecyclerView rvUnbondedDevices) {
        mBluetoothScanner.setRvUnbondedDevices(rvUnbondedDevices);

    }

    public void setPbUnbondedDevices(ProgressBar pbUnbondedDevices) {
        mBluetoothScanner.setPbUnbondedDevices(pbUnbondedDevices);
    }

    public void setBtnRefresh(View btnRefresh) {
        mBluetoothScanner.setBtnRefresh(btnRefresh);
    }

    public void init(){
        mBluetoothEnabler.init();
        mDiscoverableEnabler.init();
        mBluetoothScanner.init();
        mBluetoothEnabler.addOnBtStateChangeListener(mDiscoverableEnabler);
        mBluetoothEnabler.addOnBtStateChangeListener(mBluetoothScanner);
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
        mBluetoothScanner.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBluetoothEnabler.onStop();
        mBluetoothScanner.onStop();
    }

}
