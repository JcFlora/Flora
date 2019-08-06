package com.jc.flora.apps.ui.dialog.delegate;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by shijincheng on 2017/3/1.
 */
public class ToastDelegate {

    private static final int DURATION = Toast.LENGTH_SHORT;

    private static Toast sToast;
    private static Toast sCenterToast;

    public static void show(Context context, CharSequence text) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), text, DURATION);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }

    public static void show(Context context, int resId) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), resId, DURATION);
        } else {
            sToast.setText(resId);
        }
        sToast.show();
    }

    public static void showIfInForeground(Activity activity, int resId) {
        //todo 有问题
        if(!activity.hasWindowFocus()){
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(activity.getApplicationContext(), resId, DURATION);
        } else {
            sToast.setText(resId);
        }
        sToast.show();
    }

    public static void showInScreenCenter(Context context, CharSequence text) {
        if (sCenterToast == null) {
            sCenterToast = Toast.makeText(context.getApplicationContext(), text, DURATION);
        } else {
            sCenterToast.setText(text);
        }
        sCenterToast.setGravity(Gravity.CENTER,0,0);
        sCenterToast.show();
    }

    public static void cancel(){
        if(sToast != null){
            sToast.cancel();
        }
        if(sCenterToast != null){
            sCenterToast.cancel();
        }
    }

    public static void onAppExit(){
        sToast = null;
        sCenterToast = null;
    }

}
