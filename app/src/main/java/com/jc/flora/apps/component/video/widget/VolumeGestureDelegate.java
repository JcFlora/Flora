package com.jc.flora.apps.component.video.widget;

import android.content.Context;
import android.media.AudioManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.flora.R;

/**
 * Created by Shijincheng on 2019/3/26.
 */

public class VolumeGestureDelegate {

    private View mLayoutVolumeBox;
    private ImageView mIvVolumeIcon;
    private TextView mTvVolumeText;
    private int volume;
    private AudioManager audioManager;
    private int mMaxVolume;

    public VolumeGestureDelegate(Context context) {
        initAudioManager(context);
    }

    private void initAudioManager(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public void setLayoutVolumeBox(View layoutVolumeBox) {
        mLayoutVolumeBox = layoutVolumeBox;
    }

    public void setIvVolumeIcon(ImageView ivVolumeIcon) {
        mIvVolumeIcon = ivVolumeIcon;
    }

    public void setTvVolumeText(TextView tvVolumeText) {
        mTvVolumeText = tvVolumeText;
    }

    public void setVolumeBoxState(boolean state) {
        if(mLayoutVolumeBox!=null){
            mLayoutVolumeBox.setVisibility(state? View.VISIBLE: View.GONE);
        }
    }

    public void initVolume(){
        volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (volume < 0){
            volume = 0;
        }
    }

    public void onRightVerticalSlide(float percent){
        int index = (int) (percent * mMaxVolume) + volume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;
        // 变更声音
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
        // 变更进度条
        int i = (int) (index * 1.0 / mMaxVolume * 100);
        String s = i + "%";
        if (i == 0) {
            s = "OFF";
        }
        // 显示
        mIvVolumeIcon.setImageResource(i == 0 ? R.mipmap.ic_video_volume_off_white : R.mipmap.ic_video_volume_up_white);
        setVolumeBoxState(true);
        mTvVolumeText.setText(s);
    }

    public void onEndGesture() {
        volume = -1;
        setVolumeBoxState(false);
    }

}