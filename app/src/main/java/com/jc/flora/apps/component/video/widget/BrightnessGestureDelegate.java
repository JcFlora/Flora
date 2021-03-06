package com.jc.flora.apps.component.video.widget;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Shijincheng on 2019/3/26.
 */

public class BrightnessGestureDelegate {

    private static final float SCALE_TIMES = 0.5f;

    private View mLayoutBrightnessBox;
    private TextView mTvBrightnessText;
    private float mBrightness = -1;

    public void setLayoutBrightnessBox(View layoutBrightnessBox) {
        mLayoutBrightnessBox = layoutBrightnessBox;
    }

    public void setTvBrightnessText(TextView tvBrightnessText) {
        mTvBrightnessText = tvBrightnessText;
    }

    public void setBrightnessBoxState(boolean state) {
        if (mLayoutBrightnessBox != null) {
            mLayoutBrightnessBox.setVisibility(state ? View.VISIBLE : View.GONE);
        }
    }

    public void onLeftVerticalSlide(float percent){
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        percent *= SCALE_TIMES;
        if (mBrightness < 0) {
            mBrightness = activity.getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f){
                mBrightness = getSystemBrightness() / 255f;
            }else if (mBrightness < 0.01f){
                mBrightness = 0.01f;
            }
        }
        setBrightnessBoxState(true);
        WindowManager.LayoutParams lpa = activity.getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f){
            lpa.screenBrightness = 1.0f;
        }else if (lpa.screenBrightness < 0.01f){
            lpa.screenBrightness = 0.01f;
        }
        mTvBrightnessText.setText(((int) (lpa.screenBrightness * 100))+"%");
        activity.getWindow().setAttributes(lpa);
    }

    public void onEndGesture() {
        mBrightness = -1f;
        setBrightnessBoxState(false);
    }

    private int getSystemBrightness() {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return systemBrightness;
    }

    private Activity getActivity(){
        Context context = mLayoutBrightnessBox.getContext();
        if(context instanceof Activity){
            return (Activity) context;
        }
        return null;
    }

}
