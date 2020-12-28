package com.jc.flora.apps.component.record.delegate;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import com.jc.flora.apps.component.folder.FolderUtils;

import java.io.IOException;

/**
 * <uses-permission android:name="android.permission.RECORD_AUDIO" />
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * Created by shijincheng on 2017/11/18.
 */

public class RecordDelegate1 {

    //private static final String SAVE_PATH = FolderUtils.getAppFolderPath();

    private static final String SAVE_NAME = "test1.acc";

    // 上下文
    private Context mContext;
    private String mSavePath = "";
    private MediaRecorder mRecorder;
    // 播放音频的核心组件MediaPlayer
    private MediaPlayer mMediaPlayer;

    public RecordDelegate1(Context ctx) {
        mContext = ctx;
        mSavePath = FolderUtils.getAppFolderPath(mContext);
    }

    public void init(){
        FolderUtils.createFile(mSavePath, SAVE_NAME);
    }

    public void startRecord() {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
        }
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setOutputFile(mSavePath + SAVE_NAME);
        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecord() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    /**
     * 播放音频（从头开始播放）
     */
    public void startAudio(){
        releaseAudio();
        recreateAudio();
        start();
    }

    /**
     * 释放资源，在页面销毁后调用
     */
    public void releaseAudio(){
        if(mMediaPlayer != null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /**
     * 初始化，设置音频资源，设置不循环播放
     */
    public void recreateAudio(){
        if(mMediaPlayer == null){
            // 播放声音
            mMediaPlayer = MediaPlayer.create(mContext, FolderUtils.getUriByFilePath(mSavePath, SAVE_NAME));
            if(mMediaPlayer != null){
                // 不循环
                mMediaPlayer.setLooping(false);
            }
        }
    }

    /**
     * 播放音频
     */
    public void start() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

}
