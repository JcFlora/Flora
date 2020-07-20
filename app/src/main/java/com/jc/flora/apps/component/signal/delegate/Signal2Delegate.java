package com.jc.flora.apps.component.signal.delegate;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.apps.component.signal.projects.SetResult2TestActivity;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * Created by Shijincheng on 2019/1/16.
 */

public class Signal2Delegate extends Fragment {

    private static int REQUEST_CODE = 1;
    private static int RESULT_CODE = 1;
    private static String RESULT_KEY = "result";

    public void addToActivity(AppCompatActivity activity, String tag) {
        if(activity != null){
            activity.getSupportFragmentManager().beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    public void goAndReceiveData() {
        Intent intent = new Intent(getActivity(), SetResult2TestActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public static void setResult(AppCompatActivity activity, String result){
        Intent data = new Intent();
        data.putExtra(RESULT_KEY, result);
        activity.setResult(RESULT_CODE, data);
        activity.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_CODE){
            String s = data.getStringExtra(RESULT_KEY);
            ToastDelegate.show(getActivity(),"返回数据"+s);
        }
    }

}
