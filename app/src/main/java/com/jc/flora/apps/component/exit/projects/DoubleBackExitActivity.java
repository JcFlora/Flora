package com.jc.flora.apps.component.exit.projects;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.apps.component.exit.delegate.DoubleClickDelegate;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by shijincheng on 2017/1/25.
 */
public class DoubleBackExitActivity extends AppCompatActivity {

    private DoubleClickDelegate mDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("再按一次返回键退出");
        mDelegate = new DoubleClickDelegate();
    }

    @Override
    public void onBackPressed() {
        if(mDelegate.isDoubleClick()){
            ToastDelegate.cancel();
            ToastDelegate.onAppExit();
            finish();
        }else{
            ToastDelegate.show(this, "再按一次返回键退出");
        }
    }

}
