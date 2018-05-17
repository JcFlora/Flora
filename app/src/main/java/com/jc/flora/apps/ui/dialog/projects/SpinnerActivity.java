package com.jc.flora.apps.ui.dialog.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.SpinnerDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2018/5/16.
 */
public class SpinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Spinner");
        setContentView(R.layout.activity_dialog_spinner);
        initViews();
    }

    private void initViews(){
        TextView btn = (TextView) findViewById(R.id.btn_country);
        SpinnerDelegate spinnerDelegate = new SpinnerDelegate(this);
        spinnerDelegate.init(btn);
        spinnerDelegate.setOnItemSelectedListener(new SpinnerDelegate.OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(int which, boolean byUser) {
                if(byUser){
                    ToastDelegate.show(SpinnerActivity.this, "你选择了第" + (which + 1) + "个");
                }
            }
        });
    }

}