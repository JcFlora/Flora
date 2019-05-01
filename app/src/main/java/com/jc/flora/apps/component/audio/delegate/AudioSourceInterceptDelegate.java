package com.jc.flora.apps.component.audio.delegate;

import android.os.Handler;

import com.jc.flora.apps.component.audio.model.MP3;

import java.util.ArrayList;

/**
 * 音频数据源拦截器（支付拦截+异步获取url拦截）
 * Created by Shijincheng on 2019/4/24.
 */
public class AudioSourceInterceptDelegate {

    /** 获取真实URI的回调标记 */
    public static final int FLAG_GET_URI_BY_ID = 0x1357;
    /** 触发支付的回调标记 */
    public static final int FLAG_GO_TO_PAY = 0x2468;

    private BaseAudioDelegate mAudioDelegate;
    /** 用于模拟支付和异步获取url的Handler */
    private Handler mHandler = new Handler();
    /** 是否正在拦截 */
    private boolean mIsBusying;

    public AudioSourceInterceptDelegate(BaseAudioDelegate audioDelegate) {
        mAudioDelegate = audioDelegate;
        mAudioDelegate.addAudioSourceInterceptor(mAudioSourceInterceptor);
    }

    private AudioSourceInterceptor mAudioSourceInterceptor = new AudioSourceInterceptor(){
        @Override
        public boolean interceptSelect(final ArrayList<MP3> mp3List, final int index) {
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
        }
    };

    public void release() {
        mAudioDelegate.removeAudioSourceInterceptor(mAudioSourceInterceptor);
        mHandler.removeCallbacksAndMessages(null);
    }

}