package com.jc.flora.apps.scene.qrcode.projects;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.jc.flora.R;
import com.jc.flora.apps.component.folder.FolderUtils;
import com.jc.flora.apps.scene.album.delegate.MatisseDelegate;
import com.jc.flora.apps.scene.album.model.PickImage;
import com.jc.flora.apps.scene.qrcode.delegate.QrcodeFetchDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.util.ArrayList;

/**
 * Created by Shijincheng on 2020/5/18.
 */
public class Qrcode2Activity extends AppCompatActivity {

    private MatisseDelegate mMatisseDelegate;
    private ImageView mIvQrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("从图库识别二维码");
        setContentView(R.layout.activity_qrcode2);
        findViews();
        initViews();
        initDelegate();
    }

    private void findViews(){
        mIvQrcode = (ImageView) findViewById(R.id.iv_qrcode);
    }

    private void initViews(){
    }

    private void initDelegate(){
        mMatisseDelegate = new MatisseDelegate();
        mMatisseDelegate.setCaptureEnable(true);
        mMatisseDelegate.setMultiMode(false);
        mMatisseDelegate.addToActivity(this, "matisse");
    }

    public void openAlbum(View v){
        mMatisseDelegate.openAlbum(1, mOnImagePickedCallback);
    }

    public void fetchQrcodeText(View v){
        fetchQrcode(mIvQrcode);
    }

    public void openAlbum2(View v){
        mMatisseDelegate.openAlbum(1, mOnImagePickedCallback2);
    }

    private MatisseDelegate.OnImagePickedCallback mOnImagePickedCallback = new MatisseDelegate.OnImagePickedCallback() {
        @Override
        public void onImagePicked(ArrayList<PickImage> imageList) {
            imageList.get(0).showImage(mIvQrcode);
        }

        @Override
        public void onCancel() {
        }
    };

    private MatisseDelegate.OnImagePickedCallback mOnImagePickedCallback2 = new MatisseDelegate.OnImagePickedCallback() {
        @Override
        public void onImagePicked(ArrayList<PickImage> imageList) {
            fetchQrcode(imageList.get(0).getBitmap());
        }

        @Override
        public void onCancel() {
        }
    };

    private void fetchQrcode(ImageView ivQrcode){
        showQrcodeText(QrcodeFetchDelegate.fetch(ivQrcode));
    }

    private void fetchQrcode(Bitmap bitmap){
        showQrcodeText(QrcodeFetchDelegate.fetch(bitmap));
    }

    private void showQrcodeText(String text){
        if(TextUtils.isEmpty(text)){
            ToastDelegate.show(this, "无法识别");
        }else{
            ToastDelegate.show(this, text);
        }
    }

    @Override
    protected void onDestroy() {
        FolderUtils.delete(FolderUtils.getAppFolderPath() + "album/");
        super.onDestroy();
    }

}