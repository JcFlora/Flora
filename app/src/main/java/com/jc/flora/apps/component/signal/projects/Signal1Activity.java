package com.jc.flora.apps.component.signal.projects;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2019/1/16.
 */
public class Signal1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("setResult实现Activity组件通信");
        setContentView(R.layout.activity_signal1);
    }

    public void goAndReceiveData(View view) {
        Intent intent = new Intent(this, SetResult1TestActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            String s = data.getStringExtra("result");
            ToastDelegate.show(this,"返回数据"+s);
        }
    }
}
