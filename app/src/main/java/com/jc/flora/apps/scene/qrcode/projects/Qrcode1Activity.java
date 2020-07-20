package com.jc.flora.apps.scene.qrcode.projects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jc.flora.R;
import com.jc.flora.apps.scene.qrcode.delegate.QrcodeCreateDelegate;
import com.jc.flora.apps.scene.qrcode.delegate.QrcodeFetchDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Shijincheng on 2018/10/24.
 */
public class Qrcode1Activity extends AppCompatActivity {

    private EditText mEtQrcodeText;
    private RadioGroup mRgErrorCorrectionLevel;
    private ImageView mIvQrcode;

    private QrcodeCreateDelegate mQrcodeCreateDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("生成与识别二维码");
        setContentView(R.layout.activity_qrcode1);
        findViews();
        initViews();
        initDelegate();
    }

    private void findViews(){
        mEtQrcodeText = (EditText) findViewById(R.id.et_qrcode_text);
        mRgErrorCorrectionLevel = (RadioGroup) findViewById(R.id.rg_error_correction_level);
        mIvQrcode = (ImageView) findViewById(R.id.iv_qrcode);
    }

    private void initViews(){
        mRgErrorCorrectionLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onErrorCorrectionLevelChanged(checkedId);
            }
        });
    }

    private void initDelegate(){
        mQrcodeCreateDelegate = new QrcodeCreateDelegate();
        mQrcodeCreateDelegate.setWidthAndHeight(350, 350);
    }

    private void onErrorCorrectionLevelChanged(int checkedId){
        ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.M;
        switch (checkedId) {
            case R.id.rb_l:
                errorCorrectionLevel = ErrorCorrectionLevel.L;
                break;
            case R.id.rb_m:
                errorCorrectionLevel = ErrorCorrectionLevel.M;
                break;
            case R.id.rb_q:
                errorCorrectionLevel = ErrorCorrectionLevel.Q;
                break;
            case R.id.rb_h:
                errorCorrectionLevel = ErrorCorrectionLevel.H;
                break;
        }
        mQrcodeCreateDelegate.setEncodeHintErrorCorrection(errorCorrectionLevel);
    }

    public void createQrcode(View v){
        String content = mEtQrcodeText.getText().toString().trim();
        if(!TextUtils.isEmpty(content)){
            Bitmap bitmap = mQrcodeCreateDelegate.createQrCode(content);
            mIvQrcode.setImageBitmap(bitmap);
        }
    }

    public void addLogoToQrcode(View v){
        String content = mEtQrcodeText.getText().toString().trim();
        if(!TextUtils.isEmpty(content)){
            Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            Bitmap bitmap = mQrcodeCreateDelegate.addLogoToQrCode(content, logoBitmap);
            mIvQrcode.setImageBitmap(bitmap);
        }
    }

    public void createQrcodeWithLogo(View v) {
        String content = mEtQrcodeText.getText().toString().trim();
        if (!TextUtils.isEmpty(content)) {
            Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            Bitmap bitmap = mQrcodeCreateDelegate.createQrcodeWithLogo(content, logoBitmap);
            mIvQrcode.setImageBitmap(bitmap);
        }
    }

    public void fetchQrcodeText(View v){
        String text = QrcodeFetchDelegate.fetch(mIvQrcode);
        if(TextUtils.isEmpty(text)){
            ToastDelegate.show(this, "无法识别");
        }else{
            ToastDelegate.show(this, text);
        }
    }

}