package com.jc.flora.apps.ui.transition.delegate;

import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;

/**
 * Created by Shijincheng on 2019/1/24.
 */

public class PendingTransitionDelegate {

    public static final String PUSH = "push";
    public static final String FADE = "fade";
    public static final String NO = "";

    public static void initPendingTransition(AppCompatActivity activity, String flag){
        switch (flag) {
            case PUSH:
                activity.overridePendingTransition(R.anim.page_enter_slide, R.anim.page_exit_slide);
                break;
            case FADE:
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case NO:
            default:
                break;
        }
    }

    public static void finishPendingTransition(AppCompatActivity activity, String flag){
        switch (flag) {
            case PUSH:
                activity.overridePendingTransition(R.anim.page_reenter_slide, R.anim.page_return_slide);
                break;
            case FADE:
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case NO:
            default:
                break;
        }
    }

}
