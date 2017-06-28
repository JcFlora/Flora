package com.jc.flora.apps.scene.bluetooth.delegate;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by shijincheng on 2017/5/8.
 */
public class BluetoothDelegate3 extends Fragment{

    private AppCompatActivity mActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private IntentFilter mIntentFilter;
    private SwitchCompat mSwitchBt;
    private TextView mTvBtTip;

    public void setSwitchBt(SwitchCompat switchBt) {
        mSwitchBt = switchBt;
    }

    public void setTvBtTip(TextView tvBtTip) {
        mTvBtTip = tvBtTip;
    }

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity == null){
            return;
        }
        mActivity = activity;
        activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mIntentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        initViews();
    }

    private void initViews(){
        mTvBtTip.setVisibility(isOff() ? View.VISIBLE : View.GONE);
        mSwitchBt.setEnabled(hasBluetooth());
        mSwitchBt.setChecked(isEnabled());
        mSwitchBt.setText(isEnabled() ? "开启" : "关闭");
        mSwitchBt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setText(isChecked ? "开启" : "关闭");
                buttonView.setEnabled(false);
                setBluetoothEnable(isChecked);
                mTvBtTip.setText(isChecked ? "正在打开蓝牙。。。" : "正在关闭蓝牙。。。");
            }
        });
    }

    private boolean hasBluetooth(){
        return mBluetoothAdapter != null;
    }

    private boolean isEnabled(){
        return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled();
    }

    private boolean isOff(){
        return mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled();
    }

    private void setBluetoothEnable(boolean enable){
        if(mBluetoothAdapter == null){
            return;
        }
        if(enable && !mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.enable();
        }else if(!enable && mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.disable();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        registerBtStateChangeReceiver();
    }

    private void registerBtStateChangeReceiver() {
        if (mBluetoothAdapter == null) {
            return;
        }
        // 非粘性广播，注册接收器之前需要手动调用一次状态刷新
        mBtStateChangeReceiver.handleStateChanged(mBluetoothAdapter.getState());
        // 注册广播接收器
        mActivity.registerReceiver(mBtStateChangeReceiver, mIntentFilter);
    }

    private BtStateChangeReceiver mBtStateChangeReceiver = new BtStateChangeReceiver() {
        @Override
        protected void onStateOff() {
            mSwitchBt.setEnabled(true);
            mSwitchBt.setChecked(false);
            mSwitchBt.setText("关闭");
            mTvBtTip.setText("开启蓝牙后，您的设备可以与附近的其他蓝牙设备通信。");
            mTvBtTip.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onStateTurningOn() {
            mSwitchBt.setEnabled(false);
            mTvBtTip.setText("正在打开蓝牙。。。");
            mTvBtTip.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onStateOn() {
            mSwitchBt.setEnabled(true);
            mSwitchBt.setChecked(true);
            mSwitchBt.setText("开启");
            mTvBtTip.setVisibility(View.GONE);
        }
        @Override
        protected void onStateTurningOff() {
            mSwitchBt.setEnabled(false);
            mTvBtTip.setText("正在关闭蓝牙。。。");
            mTvBtTip.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onStateError() {
            mSwitchBt.setEnabled(true);
            mSwitchBt.setChecked(false);
            mSwitchBt.setText("关闭");
            mTvBtTip.setVisibility(View.GONE);
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        // 注销广播接收器
        unregisterBtStateChangeReceiver();
    }

    private void unregisterBtStateChangeReceiver() {
        if (mBluetoothAdapter == null) {
            return;
        }
        mActivity.unregisterReceiver(mBtStateChangeReceiver);
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
