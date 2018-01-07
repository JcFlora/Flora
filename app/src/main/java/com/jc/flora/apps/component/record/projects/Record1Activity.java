package com.jc.flora.apps.component.record.projects;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.component.record.delegate.RecordDelegate1;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Samurai on 2017/11/18.
 */
public class Record1Activity extends AppCompatActivity {

    private RecordDelegate1 mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用MediaRecorder录制AAC格式音频");
        setContentView(R.layout.activity_record1);
        mDelegate = new RecordDelegate1(this);
        mDelegate.init();
    }

    public void startRecord(View v){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.RECORD_AUDIO},1);
        }else {
            mDelegate.startRecord();
        }
    }

    public void stopRecord(View v){
        mDelegate.stopRecord();
    }

    public void playRecord(View v){
        mDelegate.startAudio();
    }

    /**
     * 重写onRequestPermissionsResult方法
     * 获取动态权限请求的结果,再开启录制音频
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            mDelegate.startRecord();
        }else {
            ToastDelegate.show(this,"用户拒绝了权限");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDelegate.stopRecord();
        mDelegate.releaseAudio();
    }

}