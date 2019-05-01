package com.jc.flora.apps.component.audio.delegate;

import android.app.Service;

import com.jc.flora.apps.component.audio.model.MP3;
import com.jc.flora.apps.component.deviceinfo.NetworkUtils;

import java.util.ArrayList;

/**
 * 音频数据源拦截器（移动网络拦截）
 * Created by Shijincheng on 2019/4/25.
 */

public class AudioMobileNetInterceptDelegate {

    /** 是否全局忽视移动网络检测 */
    private static final boolean IGNORE_MOBILE_CHECK = false;

    /** 移动网络的回调标记 */
    public static final int FLAG_MOBILE_NET = 0x4444;

    private Service mService;
    private BaseAudioDelegate mAudioDelegate;

    public static boolean sUserAgreeMobile = false;

    public AudioMobileNetInterceptDelegate(Service service, BaseAudioDelegate audioDelegate) {
        mService = service;
        mAudioDelegate = audioDelegate;
        mAudioDelegate.addAudioSourceInterceptor(mAudioSourceInterceptor);
    }

    private AudioSourceInterceptor mAudioSourceInterceptor = new AudioSourceInterceptor(){
        @Override
        public boolean interceptSelect(final ArrayList<MP3> mp3List, final int index) {
            // 检测网络状态
            int networkState = NetworkUtils.getNetworkState(mService);
            if(canPlayInMobileNet() || !NetworkUtils.isMobile(networkState)){
                return false;
            }else{
                mAudioDelegate.notifyIntercepted(index, FLAG_MOBILE_NET);
                return true;
            }
        }
    };

    public void release() {
        mAudioDelegate.removeAudioSourceInterceptor(mAudioSourceInterceptor);
        sUserAgreeMobile = false;
    }

    public static boolean canPlayInMobileNet(){
        return IGNORE_MOBILE_CHECK || sUserAgreeMobile;
    }

}