package com.jc.flora.apps.component.video.player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by Shijincheng on 2019/4/4.
 */

public class IjkPlayer extends BasePlayer {

    static {
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    }

    private final String TAG = "IjkPlayer";

    private IjkMediaPlayer mIjkPlayer;

    @Override
    public void init(Context context) {
        mIjkPlayer = createPlayer();
    }

    private IjkMediaPlayer createPlayer(){
        IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
//        ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
        //open mediacodec
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
        //accurate seek
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "timeout", 10000000);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "reconnect", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
        return ijkMediaPlayer;
    }

    @Override
    public boolean available() {
        return mIjkPlayer != null;
    }

    @Override
    public boolean isPlaying() {
        return mIjkPlayer.isPlaying();
    }

    @Override
    public int getCurrentPosition() {
        return (int) mIjkPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return (int) mIjkPlayer.getDuration();
    }

    @Override
    public void start() {
        mIjkPlayer.start();
    }

    @Override
    public void pause() {
        mIjkPlayer.pause();
    }

    @Override
    public void seekTo(int msc) {
        mIjkPlayer.seekTo(msc);
    }

    @Override
    public void release() {
        mIjkPlayer.stop();
        mIjkPlayer.release();
        mIjkPlayer = null;
        // 添加停止播放的回调
        callbackWhenStop();
    }

    @Override
    public void setSurface(Surface surface) {
        mIjkPlayer.setSurface(surface);
    }

    @Override
    public void setDataSource(Context context, Uri uri) {
        mIjkPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mIjkPlayer.setOnPreparedListener(mMediaPreparedListener);
        mIjkPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mIjkPlayer.setOnInfoListener(mOnInfoListener);
        mIjkPlayer.setOnCompletionListener(mOnCompletionListener);
        mIjkPlayer.setOnErrorListener(mOnErrorListener);
        try {
            mIjkPlayer.setDataSource(context, uri, null);
        } catch (IOException ex) {
            Log.w(TAG, "Unable to open content: " + uri, ex);
            mOnErrorListener.onError(mIjkPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            return;
        } catch (IllegalArgumentException ex) {
            Log.w(TAG, "Unable to open content: " + uri, ex);
            mOnErrorListener.onError(mIjkPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            return;
        }
        mIjkPlayer.prepareAsync();
    }

    private IMediaPlayer.OnPreparedListener mMediaPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {
            if(mOnPreparedListener != null){
                mOnPreparedListener.onPrepared(IjkPlayer.this);
            }
        }
    };

    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            // 添加缓冲进度变化的回调
            callbackWhenBufferingUpdate(percent);
        }
    };

    private IMediaPlayer.OnInfoListener mOnInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer mp, int what, int extra) {
            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                // 添加缓冲开始的回调
                callbackWhenBufferingStart();
            }else if(what == MediaPlayer.MEDIA_INFO_BUFFERING_END){
                // 添加缓冲结束的回调
                callbackWhenBufferingEnd();
            }
            return true;
        }
    };

    private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            // 添加播放完成的回调
            callbackWhenComplete();
        }
    };

    private IMediaPlayer.OnErrorListener mOnErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            // 添加播放出错的回调
            callbackWhenError();
            return true;
        }
    };

}