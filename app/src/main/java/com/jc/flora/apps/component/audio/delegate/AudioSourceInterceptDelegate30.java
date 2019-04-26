package com.jc.flora.apps.component.audio.delegate;

import android.app.Service;
import android.os.Handler;

import com.jc.flora.apps.component.audio.model.MP3;
import com.jc.flora.apps.component.deviceinfo.NetworkUtils;

import java.util.ArrayList;

/**
 * 音频数据源拦截器（支付拦截+异步获取url拦截+无网络拦截+移动网络拦截）
 * Created by Shijincheng on 2019/4/25.
 */

public class AudioSourceInterceptDelegate30 {

    /** 是否全局忽视移动网络检测 */
    private static final boolean IGNORE_MOBILE_CHECK = false;

    /** 获取真实URI的回调标记 */
    public static final int FLAG_GET_URI_BY_ID = 0x1357;
    /** 触发支付的回调标记 */
    public static final int FLAG_GO_TO_PAY = 0x2468;
    /** 无网络的回调标记 */
    public static final int FLAG_NO_NET = 0x2222;
    /** 移动网络的回调标记 */
    public static final int FLAG_MOBILE_NET = 0x4444;

    private Service mService;
    private BaseAudioDelegate mAudioDelegate;
    /** 用于模拟支付和异步获取url的Handler */
    private Handler mHandler = new Handler();
    /** 是否正在拦截 */
    private boolean mIsBusying;

    public static boolean sUserAgreeMobile = false;

    public AudioSourceInterceptDelegate30(Service service, BaseAudioDelegate audioDelegate) {
        mService = service;
        mAudioDelegate = audioDelegate;
        mAudioDelegate.addAudioSourceInterceptor(mAudioSourceInterceptor);
    }

    private AudioSourceInterceptor mAudioSourceInterceptor = new AudioSourceInterceptor(){
        @Override
        public boolean interceptSelect(final ArrayList<MP3> mp3List, final int index) {
            // 检测网络状态
            int networkState = NetworkUtils.getNetworkState(mService);
            if(networkState < 0){
                mAudioDelegate.notifyIntercepted(index, FLAG_NO_NET);
                return true;
            } else if (networkState == NetworkUtils.NETWORK_STATE_WIFI || IGNORE_MOBILE_CHECK || sUserAgreeMobile) {
                boolean intercept = !mp3List.get(index).couldPlay();
                if(intercept && !mIsBusying){
                    mIsBusying = true;
                    // 模拟支付和异步获取url
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 模拟支付
                            mp3List.get(index).isFree = true;
                            // 模拟异步获取url
                            mp3List.get(index).audioUrl = mp3List.get(index).id;
                            // 获取到url之后播放
                            mAudioDelegate.selectAudio(index);
                            mIsBusying = false;
                        }
                    },1000);
                    mAudioDelegate.notifyIntercepted(index, FLAG_GET_URI_BY_ID);
                }
                return intercept;
            } else {
                mAudioDelegate.notifyIntercepted(index, FLAG_MOBILE_NET);
                return true;
            }
        }

        @Override
        public boolean interceptPlay() {
            // 检测网络状态
            int networkState = NetworkUtils.getNetworkState(mService);
            if(networkState < 0){
                mAudioDelegate.notifyIntercepted(mAudioDelegate.getCurrentMp3Index(), FLAG_NO_NET);
                return true;
            }else{
                return false;
            }
        }
    };

    public void release() {
        mAudioDelegate.removeAudioSourceInterceptor(mAudioSourceInterceptor);
        mHandler.removeCallbacksAndMessages(null);
        sUserAgreeMobile = false;
    }

    public static boolean canPlayInMobileNet(){
        return IGNORE_MOBILE_CHECK || sUserAgreeMobile;
    }

}