package com.jc.flora.apps.scene.bluetooth.delegate;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2017/5/8.
 */
public class BluetoothDelegate2 extends Fragment{

    private AppCompatActivity mActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private IntentFilter mIntentFilter;
    private BtStateChangeReceiver mBtStateChangeReceiver;

    public void setBtStateChangeReceiver(BtStateChangeReceiver btStateChangeReceiver) {
        mBtStateChangeReceiver = btStateChangeReceiver;
    }

    public void init(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mIntentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity == null){
            return;
        }
        mActivity = activity;
        activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
    }

    @Override
    public void onStart() {
        super.onStart();
        registerBtStateChangeReceiver();
    }

    private void registerBtStateChangeReceiver() {
        if (mBluetoothAdapter == null || mBtStateChangeReceiver == null) {
            return;
        }
        // 非粘性广播，注册接收器之前需要手动调用一次状态刷新
        mBtStateChangeReceiver.handleStateChanged(mBluetoothAdapter.getState());
        // 注册广播接收器
        mActivity.registerReceiver(mBtStateChangeReceiver, mIntentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        // 注销广播接收器
        unregisterBtStateChangeReceiver();
    }

    private void unregisterBtStateChangeReceiver() {
        if (mBluetoothAdapter == null || mBtStateChangeReceiver == null) {
            return;
        }
        mActivity.unregisterReceiver(mBtStateChangeReceiver);
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

    public static abstract class BtStateChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            handleStateChanged(state);
        }

        private void handleStateChanged(int state){
            switch (state) {
                case BluetoothAdapter.STATE_OFF:
                    onStateOff();
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    onStateTurningOn();
                    break;
                case BluetoothAdapter.STATE_ON:
                    onStateOn();
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    onStateTurningOff();
                    break;
                default:
                    onStateError();
                    break;
            }
        }

        protected abstract void onStateOff();
        protected abstract void onStateTurningOn();
        protected abstract void onStateOn();
        protected abstract void onStateTurningOff();
        protected abstract void onStateError();
    }

}
