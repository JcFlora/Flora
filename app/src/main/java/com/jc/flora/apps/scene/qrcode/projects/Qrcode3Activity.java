package com.jc.flora.apps.scene.qrcode.projects;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.jc.flora.R;
import com.jc.flora.apps.component.permit.delegate.CameraPermitDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by Shijincheng on 2020/5/18.
 */
public class Qrcode3Activity extends AppCompatActivity {

    private static final String TAG = Qrcode3Activity.class.getSimpleName();

    private ZXingView mZXingView;

    private boolean mHasRequestCameraPermission = false;
    private CameraPermitDelegate mCameraPermitDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用bga-qrcode扫描二维码");
        setContentView(R.layout.activity_qrcode3);
        findViews();
        initViews();
        requestCameraPermission();
    }

    private void findViews(){
        mZXingView = (ZXingView) findViewById(R.id.v_zxing);
    }

    private void initViews(){
        mZXingView.setDelegate(mQrCodeDelegate);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mHasRequestCameraPermission){
            mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别
            mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        }
    }

    /**
     * 申请相机权限
     */
    private void requestCameraPermission(){
        mCameraPermitDelegate = new CameraPermitDelegate();
        mCameraPermitDelegate.setOnRequestPermissionsCallback(new CameraPermitDelegate.OnRequestPermissionsCallback() {
            @Override
            public void onGranted(int flag) {
                mHasRequestCameraPermission = true;
                mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//              mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别
                mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
            }
            @Override
            public void onDenied(int flag) {
                showCameraPermitDialog();
            }
        });
        mCameraPermitDelegate.addToActivity(this, "cameraPermit");
        mCameraPermitDelegate.requestPermission();
    }

    /**
     * 展示相机权限开启对话框
     */
    private void showCameraPermitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("开启相机权限才能继续使用此功能");
        builder.setPositiveButton("去开启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mCameraPermitDelegate.gotoAppDetailSettings();
            }
        });
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private QRCodeView.Delegate mQrCodeDelegate = new QRCodeView.Delegate(){

        @Override
        public void onScanQRCodeSuccess(String result) {
            Log.i(TAG, "result:" + result);
            showQrcodeText(result);
            mZXingView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mZXingView.startSpot();
                }
            }, 2000);
        }

        @Override
        public void onCameraAmbientBrightnessChanged(boolean isDark) {
            // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
            String tipText = mZXingView.getScanBoxView().getTipText();
            String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
            if (isDark) {
                if (!tipText.contains(ambientBrightnessTip)) {
                    mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
                }
            } else {
                if (tipText.contains(ambientBrightnessTip)) {
                    tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                    mZXingView.getScanBoxView().setTipText(tipText);
                }
            }
        }

        @Override
        public void onScanQRCodeOpenCameraError() {
            Log.e(TAG, "打开相机出错");
        }
    };

    private void showQrcodeText(String text){
        if(TextUtils.isEmpty(text)){
            ToastDelegate.show(this, "无法识别");
        }else{
            ToastDelegate.show(this, text);
        }
    }

    @Override
    protected void onStop() {
        if(mHasRequestCameraPermission){
            mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

}