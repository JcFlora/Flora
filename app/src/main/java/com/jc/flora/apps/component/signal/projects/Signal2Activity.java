package com.jc.flora.apps.component.signal.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;
import com.jc.flora.apps.component.signal.delegate.Signal2Delegate;

/**
 * Created by shijincheng on 2019/1/16.
 */
public class Signal2Activity extends AppCompatActivity {

    private Signal2Delegate mSignalDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("封装setResult逻辑为Delegate");
        setContentView(R.layout.activity_signal1);
        initDelegate();
    }

    private void initDelegate(){
        mSignalDelegate = new Signal2Delegate();
        mSignalDelegate.addToActivity(this,"signal");
    }

    public void goAndReceiveData(View view) {
        mSignalDelegate.goAndReceiveData();
    }

}
