package com.jc.flora.apps.scene.album.projects;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2019/4/6.
 */
public class Album1Activity extends AppCompatActivity {

    private ImageView mIvPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用系统相册");
        setContentView(R.layout.activity_album1);
        findViews();
    }

    private void findViews(){
        mIvPhoto = findViewById(R.id.iv_photo);
    }

    public void openSysAlbum(View v){
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }else{
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 101);
    }

    public void openAlbumApp(View v){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent, 102);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 101 || requestCode == 102){
                mIvPhoto.setImageURI(data.getData());
            }
        }
    }
}
