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
        if(context == null){
            return;
        }
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(context.getApplicationContext(), text, DURATION);
        sToast.show();
    }

    public static void show(Context context, int resId) {
        if(context == null){
            return;
        }
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(context.getApplicationContext(), resId, DURATION);
        sToast.show();
    }

    public static void showIfInForeground(Activity activity, int resId) {
        if(activity == null){
            return;
        }
        //todo 有问题，特殊情况下hasWindowFocus不能作为前台的判断标准，比如正在弹出对话框
        if(!activity.hasWindowFocus()){
            return;
        }
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(activity.getApplicationContext(), resId, DURATION);
        sToast.show();
    }

    public static void showInScreenCenter(Context context, CharSequence text) {
        if(context == null){
            return;
        }
        if(sCenterToast != null){
            sCenterToast.cancel();
        }
        sCenterToast = Toast.makeText(context.getApplicationContext(), text, DURATION);
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