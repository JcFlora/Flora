package com.jc.flora.apps.component.cache.projects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.jc.flora.R;
import com.jc.flora.apps.component.cache.delegate.Cacher;
import com.jc.flora.apps.component.cache.delegate.User;
import com.jc.flora.apps.component.cache.delegate.UserCacher2;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.util.UUID;

/**
 * Created by shijincheng on 2017/1/25.
 */
public class Cacher2Activity extends AppCompatActivity {

    private EditText mEt1,mEt2,mEt3,mEt4;
    private User mUser = new User();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_cacher2);
        setTitle("实体数据类型的静态变量缓存");
        mEt1 = (EditText) findViewById(R.id.et1);
        mEt2 = (EditText) findViewById(R.id.et2);
        mEt3 = (EditText) findViewById(R.id.et3);
        mEt4 = (EditText) findViewById(R.id.et4);
        Cacher.init(this);
        showCache();
    }

    public void createToken(View v){
        String token = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        mEt4.setText(token);
    }

    public void saveCache(View v) {
        mUser.name = mEt1.getText().toString().trim();
        mUser.nickName = mEt2.getText().toString().trim();
        mUser.phone = mEt3.getText().toString().trim();
        mUser.token = mEt4.getText().toString().trim();
        UserCacher2.writeUser(mUser);
    }

    public void showCache(View v) {
        showCache();
    }

    public void clearCache(View v) {
        UserCacher2.clearUser();
    }

    private void showCache(){
        User user = UserCacher2.readUser();
        if(user == null){
            ToastDelegate.show(this,"没有用户信息");
            mEt1.setText("");
            mEt2.setText("");
            mEt3.setText("");
            mEt4.setText("");
        }else{
            mEt1.setText(user.name);
            mEt2.setText(user.nickName);
            mEt3.setText(user.phone);
            mEt4.setText(user.token);
        }
    }

}
