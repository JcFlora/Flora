package com.jc.flora.apps.component.permit.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.apps.component.permit.delegate.PermitDelegate1;

/**
 * Created by Samurai on 2020/5/19.
 */
public class Permit1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("页面触发申请单个固定权限");
        requestPermission();
    }

    private void requestPermission(){
        final PermitDelegate1 delegate = new PermitDelegate1();
        delegate.addToActivity(this,"permit");
        delegate.requestPermission();
    }

}