package com.jc.flora.apps.ui.dialog.delegate;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by shijincheng on 2017/3/1.
 */
public class ToastDelegate {

    private static final int DURATION = Toast.LENGTH_SHORT;

    private static Toast sToast;

    public static void show(Context context, CharSequence text) {
        if (sToast == null) {
            sToast = Toast.makeText(context.getApplicationContext(), text, DURATION);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }

    public static void cancel(){
        if(sToast != null){
            sToast.cancel();
        }
    }

    public static void onAppExit(){
        sToast = null;
    }

}
