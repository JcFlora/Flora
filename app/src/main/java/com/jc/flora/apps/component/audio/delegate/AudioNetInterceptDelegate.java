package com.jc.flora.apps.component.audio.delegate;

import android.app.Service;

import com.jc.flora.apps.component.audio.model.MP3;
import com.jc.flora.apps.component.deviceinfo.NetworkUtils;

import java.util.ArrayList;

/**
 * 音频数据源拦截器（无网络拦截）
 * Created by Shijincheng on 2019/4/24.
 */

public class AudioNetInterceptDelegate {

    /** 无网络的回调标记 */
    public static final int FLAG_NO_NET = 0x2222;

    private Service mService;
    private BaseAudioDelegate mAudioDelegate;

    public AudioNetInterceptDelegate(Service service, BaseAudioDelegate audioDelegate) {
        mService = service;
        mAudioDelegate = audioDelegate;
        mAudioDelegate.addAudioSourceInterceptor(mAudioSourceInterceptor);
    }

    private AudioSourceInterceptor mAudioSourceInterceptor = new AudioSourceInterceptor(){
        @Override
        public boolean interceptSelect(final ArrayList<MP3> mp3List, final int index) {
            return interceptPlay();
        }

        @Override
        public boolean interceptPlay() {
            // 检测网络状态
            if(NetworkUtils.isNetConnected(mService)){
                return false;
            }else{
                mAudioDelegate.notifyIntercepted(mAudioDelegate.getCurrentMp3Index(), FLAG_NO_NET);
                return true;
            }
        }
    };

    public void release() {
        mAudioDelegate.removeAudioSourceInterceptor(mAudioSourceInterceptor);
    }

}