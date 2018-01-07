package com.jc.flora.apps.component.audio.delegate;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.jc.flora.R;

/**
 * Created by Samurai on 2018/1/7.
 */
public class AudioDelegate19 {

    // 声音资源
    private static final int AUDIO_ID = R.raw.audio_fairyland;
    // 上下文
    private Context mContext;
    // 播放音频的核心组件ExoPlayer
    private SimpleExoPlayer mExoPlayer;
    // 媒体库
    private MediaSource mMediaSource;

    public AudioDelegate19(Context ctx) {
        mContext = ctx;
        init();
    }

    private void init(){
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory selectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(selectionFactory);
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext,trackSelector);
        try {
            DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(AUDIO_ID));
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

    /**
     * 播放音频
     */
    public void playAudio(){
        if(!mExoPlayer.getPlayWhenReady()){
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * 暂停音频
     */
    public void pauseAudio(){
        if(mExoPlayer.getPlayWhenReady()){
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    /**
     * 停止音频
     */
    public void stopAudio() {
        mExoPlayer.stop();
    }

    /**
     * 播放音频（从头开始播放）
     */
    public void startAudio(){
        mExoPlayer.prepare(mMediaSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    /**
     * 释放资源，在页面销毁后调用
     */
    public void release(){
        if(mExoPlayer != null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

}