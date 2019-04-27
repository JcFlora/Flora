package com.jc.flora.apps.component.audio.delegate;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.jc.flora.apps.component.audio.model.MP3;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Samurai on 2019/4/19.
 */
public class AudioDelegate24 extends BaseAudioDelegate {

    /** 快进快退时间间隔：15秒 */
    private static final int FORWARD_POSITION = 15_000;

    // mp3列表
    private ArrayList<MP3> mMp3List;
    // 上下文
    private Context mContext;
    // 播放音频的核心组件ExoPlayer
    private SimpleExoPlayer mExoPlayer;
    // 媒体库
    private MediaSource mMediaSource;
    // 当前播放的mp3文件索引
    private int mCurrentMp3Index = 0;
    // 当前播放位置
    private int mCurrentPosition = -1;
    // 音频播放状态监听器集合
    private ArrayList<AudioStatusListener> mAudioStatusListeners = new ArrayList<>();
    // 音频数据源拦截器集合
    private ArrayList<AudioSourceInterceptor> mAudioSourceInterceptors = new ArrayList<>();

    // 播放模式
    private AudioPlayMode mPlayMode = AudioPlayMode.LOOP;

    public AudioDelegate24(Context ctx) {
        mContext = ctx;
    }

    /**
     * 设置播放的mp3列表
     * @param mp3List 播放的mp3列表
     */
    public void setMp3List(ArrayList<MP3> mp3List) {
        mMp3List = mp3List;
        mCurrentMp3Index = 0;
        recreate();
    }

    /**
     * 同步mp3列表
     */
    public void syncMp3List() {
        if (mMp3List == null || getMaxProgress() <= 0) {
            return;
        }
        for (AudioStatusListener l : mAudioStatusListeners) {
            // 同步当前切换
            l.onSelect(mCurrentMp3Index, getMaxProgress());
            // 同步播放状态
            if (mExoPlayer.getPlayWhenReady()) {
                l.onPlay();
            } else {
                l.onPause();
            }
            // 同步播放位置
            l.onProgress(getCurrentPosition());
            // 同步播放模式
            l.onModeSelect(mPlayMode.value());
        }
    }

    /**
     * 设置播放模式
     * @param audioPlayMode 播放模式
     */
    public void setPlayMode(AudioPlayMode audioPlayMode) {
        mPlayMode = audioPlayMode;
        // 切换播放模式时回调
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onModeSelect(audioPlayMode.value());
        }
    }

    /**
     * 添加音频播放状态监听回调
     * @param l 音频播放状态监听回调
     */
    public void addAudioStatusListener(AudioStatusListener l) {
        mAudioStatusListeners.add(l);
    }

    /**
     * 移除音频播放状态监听回调
     * @param l 音频播放状态监听回调
     */
    public void removeAudioStatusListener(AudioStatusListener l) {
        mAudioStatusListeners.remove(l);
    }

    /**
     * 播放音频（继续播放）
     */
    public void playAudio(){
        if (!mExoPlayer.getPlayWhenReady()) {
            // 添加拦截
            for (AudioSourceInterceptor i : mAudioSourceInterceptors) {
                if(i.interceptPlay()){
                    return;
                }
            }
            mExoPlayer.setPlayWhenReady(true);
            for (AudioStatusListener l : mAudioStatusListeners) {
                l.onPlay();
            }
        }
    }

    /**
     * 暂停播放
     */
    public void pauseAudio(){
        if (mExoPlayer.getPlayWhenReady()) {
            mExoPlayer.setPlayWhenReady(false);
            for (AudioStatusListener l : mAudioStatusListeners) {
                l.onPause();
            }
        }
    }

    /**
     * 复位（从头开始播放）
     */
    public void resetAudio() {
        release();
        recreate();
        start();
    }

    /**
     * 切换音频（从头开始播放）
     * 修改为默认列表循环播放模式
     * @param index 切换的音频索引
     */
    public void selectAudio(int index) {
        if(index < 0){
            // 第一首的上一首切换成最后一首
            mCurrentMp3Index = mMp3List.size() - 1;
        }else if(index >= mMp3List.size()){
            // 最后一首的下一首切换成第一首
            mCurrentMp3Index = 0;
        }else{
            mCurrentMp3Index = index;
        }
        resetAudio();
    }

    /**
     * 播放下一个音频
     * 修改为不设置边界，调用selectAudio实现
     */
    public void nextAudio() {
        // 原来的逻辑默认为列表循环模式，新添加单曲模式和随机模式
        switch (mPlayMode) {
            case SINGLE:
                selectAudio(mCurrentMp3Index);
                break;
            case SHUFFLE:
                selectAudio(new Random().nextInt(mMp3List.size()));
                break;
            case LOOP:
            default:
                selectAudio(mCurrentMp3Index + 1);
                break;
        }
    }

    /**
     * 播放上一个音频
     * 修改为不设置边界，调用selectAudio实现
     */
    public void preAudio() {
        // 原来的逻辑默认为列表循环模式，新添加单曲模式和随机模式
        switch (mPlayMode) {
            case SINGLE:
                selectAudio(mCurrentMp3Index);
                break;
            case SHUFFLE:
                selectAudio(new Random().nextInt(mMp3List.size()));
                break;
            case LOOP:
            default:
                selectAudio(mCurrentMp3Index - 1);
                break;
        }
    }

    /**
     * 释放资源，在页面销毁后调用
     */
    public void release() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
            // 添加停止播放的回调
            for (AudioStatusListener l : mAudioStatusListeners) {
                l.onStop();
            }
            mProgressRefreshHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 初始化，设置音频资源，设置不循环播放
     */
    public void recreate() {
        if (mExoPlayer == null) {
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory selectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(selectionFactory);
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
            mExoPlayer.addListener(new AudioEventListener(){
                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                    long duration = mExoPlayer.getDuration();
                    if(mExoPlayer.isLoading() && duration > 0){
                        // 选择或切换的回调
                        for (AudioStatusListener l : mAudioStatusListeners) {
                            l.onSelect(mCurrentMp3Index, (int) duration);
                        }
                        // 初始化播放位置
                        mCurrentPosition = -1;
                        // 开始不停地刷新播放进度
                        mProgressRefreshHandler.sendEmptyMessage(0);
                    }
                }
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if(playWhenReady && playbackState == ExoPlayer.STATE_ENDED){
                        // 播放完成自动播放下一首
                        nextAudio();
                    }
                }
            });
        }
        try {
            DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(mMp3List.get(mCurrentMp3Index).resId));
            final RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(mContext);
            rawResourceDataSource.open(dataSpec);
            DataSource.Factory factory = new DataSource.Factory() {
                @Override
                public DataSource createDataSource() {
                    return rawResourceDataSource;
                }
            };
            Uri uri = rawResourceDataSource.getUri();
            mMediaSource = new ExtractorMediaSource(uri, factory, new DefaultExtractorsFactory(),null,null);
            mExoPlayer.prepare(mMediaSource);
        } catch (RawResourceDataSource.RawResourceDataSourceException e) {
            e.printStackTrace();
        }
    }

    // 用于刷新播放进度的Handler
    private Handler mProgressRefreshHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 获取最新的播放位置
            int position = getCurrentPosition();
            // 如果和上一次的播放位置不同，则触发回调
            if(position != mCurrentPosition){
                mCurrentPosition = position;
                // 播放位置的回调
                for (AudioStatusListener l : mAudioStatusListeners) {
                    l.onProgress(position);
                }
            }
            mProgressRefreshHandler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    /**
     * 播放音频
     */
    public void start() {
        // 添加拦截
        for (AudioSourceInterceptor i : mAudioSourceInterceptors) {
            if(i.interceptPlay()){
                return;
            }
        }
        mExoPlayer.setPlayWhenReady(true);
        for (AudioStatusListener l : mAudioStatusListeners) {
            l.onPlay();
        }
    }

    /**
     * 获取当前播放的mp3文件索引
     * @return 当前播放的mp3文件索引
     */
    public int getCurrentMp3Index() {
        return mCurrentMp3Index;
    }

    /**
     * 获取当前播放的mp3
     * @return 当前播放的mp3
     */
    public MP3 getCurrentMp3() {
        return mMp3List.get(mCurrentMp3Index);
    }

    /**
     * 获取歌曲长度
     **/
    public int getMaxProgress() {
        return (int)mExoPlayer.getDuration();
    }

    /**
     * 获取播放位置
     */
    public int getCurrentPosition() {
        int position = (int)mExoPlayer.getCurrentPosition();
        return position < 0 ? 0 : position;
    }

    /**
     * 播放指定位置
     */
    public void seekToPosition(int msec) {
        mExoPlayer.seekTo(msec);
    }


    /**
     * 添加音频数据源拦截器
     * @param i 音频数据源拦截器
     */
    public void addAudioSourceInterceptor(AudioSourceInterceptor i) {
        mAudioSourceInterceptors.add(i);
    }

    /**
     * 移除音频数据源拦截器
     * @param i 音频数据源拦截器
     */
    public void removeAudioSourceInterceptor(AudioSourceInterceptor i) {
        mAudioSourceInterceptors.remove(i);
    }

    /**
     * 快退15秒
     */
    public void rewind(){
        int rewindPosition = mCurrentPosition - FORWARD_POSITION;
        if(rewindPosition < 0){
            rewindPosition = 0;
        }
        mExoPlayer.seekTo(rewindPosition);
    }

    /**
     * 快进15秒
     */
    public void forward(){
        int forwardPosition = mCurrentPosition + FORWARD_POSITION;
        if(forwardPosition > getMaxProgress()){
            forwardPosition = getMaxProgress();
        }
        mExoPlayer.seekTo(forwardPosition);
    }

}