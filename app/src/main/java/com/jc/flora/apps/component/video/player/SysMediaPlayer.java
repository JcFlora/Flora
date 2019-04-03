package com.jc.flora.apps.component.video.player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;

import com.jc.flora.apps.component.video.delegate.VideoStatusListener;

import java.io.IOException;

/**
 * Created by Shijincheng on 2019/4/3.
 */

public class SysMediaPlayer extends BasePlayer {

    final String TAG = "SysMediaPlayer";

    private MediaPlayer mMediaPlayer;

    @Override
    public void init() {
        mMediaPlayer = new MediaPlayer();
    }

    @Override
    public boolean available() {
        return mMediaPlayer != null;
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public void start() {
        mMediaPlayer.start();
    }

    @Override
    public void pause() {
        mMediaPlayer.pause();
    }

    @Override
    public void seekTo(int msc) {
        mMediaPlayer.seekTo(msc);
    }

    @Override
    public void stop() {
        mMediaPlayer.stop();
    }

    @Override
    public void release() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
        // 添加停止播放的回调
        callbackWhenStop();
    }

    @Override
    public void setSurface(Surface surface) {
        mMediaPlayer.setSurface(surface);
    }

    @Override
    public void setDataSource(Context context, Uri uri) {
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(mMediaPreparedListener);
        mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mMediaPlayer.setOnInfoListener(mOnInfoListener);
        mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        mMediaPlayer.setOnErrorListener(mOnErrorListener);
        try {
            mMediaPlayer.setDataSource(context, uri, null);
        } catch (IOException ex) {
            Log.w(TAG, "Unable to open content: " + uri, ex);
            mOnErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            return;
        } catch (IllegalArgumentException ex) {
            Log.w(TAG, "Unable to open content: " + uri, ex);
            mOnErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            return;
        }
        mMediaPlayer.prepareAsync();
    }

    private MediaPlayer.OnPreparedListener mMediaPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if(mOnPreparedListener != null){
                mOnPreparedListener.onPrepared(SysMediaPlayer.this);
            }
        }
    };

    private MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            // 添加缓冲进度变化的回调
            for (VideoStatusListener l : mVideoStatusListeners) {
                l.onBufferingUpdate(percent);
            }
        }
    };

    private MediaPlayer.OnInfoListener mOnInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                // 添加缓冲开始的回调
                for (VideoStatusListener l : mVideoStatusListeners) {
                    l.onBufferingStart();
                }
            }else if(what == MediaPlayer.MEDIA_INFO_BUFFERING_END){
                // 添加缓冲结束的回调
                for (VideoStatusListener l : mVideoStatusListeners) {
                    l.onBufferingEnd();
                }
            }
            return true;
        }
    };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // 添加播放完成的回调
            for (VideoStatusListener l : mVideoStatusListeners) {
                l.onComplete();
            }
        }
    };

    private MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            // 添加播放出错的回调
            for (VideoStatusListener l : mVideoStatusListeners) {
                l.onError();
            }
            return true;
        }
    };

}
