package com.jc.flora.apps.component.audio.player;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.jc.flora.apps.component.audio.delegate.AudioEventListener;
import com.jc.flora.apps.component.audio.delegate.AudioPlaySpeed;

/**
 * Created by Shijincheng on 2019/4/26.
 */

public class ExoAudioPlayer extends BaseAudioPlayer {

    private final String TAG = "ExoAudioPlayer";

    private SimpleExoPlayer mExoPlayer;

    // 是否正在准备
    private boolean mIsPreparing = true;
    // 是否正在缓冲
    private boolean mIsBuffering = false;
    private DefaultBandwidthMeter mBandwidthMeter;

    @Override
    public void init(Context context) {
        mBandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory selectionFactory = new AdaptiveTrackSelection.Factory(mBandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(selectionFactory);
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        mExoPlayer.setAudioStreamType(C.STREAM_TYPE_MUSIC);
        mExoPlayer.addListener(mAudioEventListener);
    }

    @Override
    public boolean available() {
        return mExoPlayer != null;
    }

    @Override
    public boolean isPlaying() {
        return mExoPlayer.getPlayWhenReady();
    }

    @Override
    public int getCurrentPosition() {
        int position = (int) mExoPlayer.getCurrentPosition();
        return position < 0 ? 0 : position;
    }

    @Override
    public int getDuration() {
        return (int) mExoPlayer.getDuration();
    }

    @Override
    public void start() {
        mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void pause() {
        mExoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void stop() {
        mExoPlayer.stop();
    }

    @Override
    public void seekTo(int msc) {
        mExoPlayer.seekTo(msc);
    }

    @Override
    public void release() {
        mIsPreparing = true;
        mIsBuffering = false;
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
        // 添加停止播放的回调
        callbackWhenStop();
    }

    /**
     * 倍速播放功能
     *
     * @param audioPlaySpeed
     */
    public void setSpeed(AudioPlaySpeed audioPlaySpeed) {
        if (available()) {
            PlaybackParameters parameters = new PlaybackParameters(audioPlaySpeed.value(), 1f);
            mExoPlayer.setPlaybackParameters(parameters);
        }
        // 设置播放速度时回调
        callbackWhenSpeedSelect(audioPlaySpeed.index());
    }

    @Override
    public void setDataSource(Context context, Uri uri) {
        mIsPreparing = true;
        mExoPlayer.prepare(getMediaSource(context.getApplicationContext(), uri));
        mExoPlayer.setPlayWhenReady(false);
    }

    private MediaSource getMediaSource(Context appContext, Uri uri){
        int contentType = Util.inferContentType(uri);
        DefaultDataSourceFactory dataSourceFactory =
                new DefaultDataSourceFactory(appContext,
                        Util.getUserAgent(appContext, appContext.getPackageName()), mBandwidthMeter);
        switch (contentType) {
            case C.TYPE_DASH:
                DefaultDashChunkSource.Factory factory = new DefaultDashChunkSource.Factory(dataSourceFactory);
                return new DashMediaSource(uri, dataSourceFactory, factory, null, null);
            case C.TYPE_SS:
                DefaultSsChunkSource.Factory ssFactory = new DefaultSsChunkSource.Factory(dataSourceFactory);
                return new SsMediaSource(uri, dataSourceFactory, ssFactory, null, null);
            case C.TYPE_HLS:
                return new HlsMediaSource(uri, dataSourceFactory, null, null);

            case C.TYPE_OTHER:
            default:
                // This is the MediaSource representing the media to be played.
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                return new ExtractorMediaSource(uri,
                        dataSourceFactory, extractorsFactory, null, null);
        }
        // 更新到最新版本要改成下面的代码
//        switch (contentType) {
//            case C.TYPE_DASH:
//                return new DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//            case C.TYPE_SS:
//                return new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//            case C.TYPE_HLS:
//                return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//            case C.TYPE_OTHER:
//            default:
//                // This is the MediaSource representing the media to be played.
//                return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//        }
    }

    private AudioEventListener mAudioEventListener = new AudioEventListener() {
        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            long duration = mExoPlayer.getDuration();
            if (mExoPlayer.isLoading() && duration > 0) {
                if(mOnPreparedListener != null){
                    mOnPreparedListener.onPrepared(ExoAudioPlayer.this);
                }
            }
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            if (!isLoading) {
                // 添加缓冲进度变化的回调
                callbackWhenBufferingUpdate(mExoPlayer.getBufferedPercentage());
            }
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (mIsPreparing) {
                switch (playbackState) {
                    case ExoPlayer.STATE_READY:
                        mIsPreparing = false;
                        // 添加准备结束的回调
                        callbackWhenPrepareEnd();
                        break;
                }
            }
            if (mIsBuffering) {
                switch (playbackState) {
                    case ExoPlayer.STATE_READY:
                    case ExoPlayer.STATE_ENDED:
                        mIsBuffering = false;
                        // 添加缓冲结束的回调
                        callbackWhenBufferingEnd();
                        break;
                }
            }
            if (!mIsPreparing) {
                switch (playbackState) {
                    case ExoPlayer.STATE_BUFFERING:
                        mIsBuffering = true;
                        // 添加缓冲开始的回调
                        callbackWhenBufferingStart();
                        break;
                    case ExoPlayer.STATE_ENDED:
                        // 播放完成自动播放下一首
                        if(mStatusBridge != null){
                            mStatusBridge.playNextAudio();
                        }
                        break;
                }
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            // 添加播放出错的回调
            callbackWhenError();
            // 保存状态数据
            if(mStatusBridge != null){
                mStatusBridge.saveStatusWhenError();
            }
            // 出错之后，要恢复初始状态
            release();
        }
    };

}