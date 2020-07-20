package com.jc.flora.apps.component.audio.delegate;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.jc.flora.R;
import com.jc.flora.apps.component.audio.model.MP3;
import com.jc.flora.apps.component.cache.utils.GlideUtils;

import java.io.File;

/**
 *
 * implementation "androidx.media:media:1.1.0"
 *
 * 在低版本上，需要在manifest配置文件中添加
 *  <receiver android:name="androidx.media.session.MediaButtonReceiver" >
 *      <intent-filter>
 *          <action android:name="android.intent.action.MEDIA_BUTTON" />
 *      </intent-filter>
 *  </receiver>
 * Created by shijincheng on 2019/4/22.
 */

public class AudioSessionDelegate26 {

    private static final String TAG = "AudioSessionDelegate15";

    private static final long MEDIA_SESSION_ACTIONS = PlaybackStateCompat.ACTION_PLAY
            | PlaybackStateCompat.ACTION_PAUSE
            | PlaybackStateCompat.ACTION_PLAY_PAUSE
            | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
            | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
            | PlaybackStateCompat.ACTION_STOP
            | PlaybackStateCompat.ACTION_SEEK_TO;

    private Service mService;
    private BaseAudioDelegate mAudioDelegate;
    private MediaSessionCompat mMediaSession;
    private int mPlaybackState = PlaybackStateCompat.STATE_PAUSED;

    public AudioSessionDelegate26(Service service, BaseAudioDelegate audioDelegate) {
        mService = service;
        mAudioDelegate = audioDelegate;
        addAudioStatusListener();
        setupMediaSession();
    }

    public void release() {
        mAudioDelegate.removeAudioStatusListener(mAudioStatusListener);
        mMediaSession.setCallback(null);
        mMediaSession.setActive(false);
        mMediaSession.release();
    }

    private void addAudioStatusListener() {
        mAudioDelegate.addAudioStatusListener(mAudioStatusListener);
    }

    private void setupMediaSession() {
        mMediaSession = new MediaSessionCompat(mService, TAG);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS | MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS);
        mMediaSession.setCallback(mCallback);
        mMediaSession.setActive(true);
    }

    private AudioStatusListener mAudioStatusListener = new AudioStatusListener() {

        @Override
        public void onSelect(int index, int maxProgress) {
            updateMetaData(mAudioDelegate.getCurrentMp3());
        }

        @Override
        public void onPlay() {
            mPlaybackState = PlaybackStateCompat.STATE_PLAYING;
            updatePlaybackState();
        }

        @Override
        public void onPause() {
            mPlaybackState = PlaybackStateCompat.STATE_PAUSED;
            updatePlaybackState();
        }

        @Override
        public void onStop() {
            mPlaybackState = PlaybackStateCompat.STATE_STOPPED;
            updatePlaybackState();
        }

        @Override
        public void onProgress(int progress) {
            updatePlaybackState();
        }
    };

    private MediaSessionCompat.Callback mCallback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay() {
            mAudioDelegate.playAudio();
        }

        @Override
        public void onPause() {
            mAudioDelegate.pauseAudio();
        }

        @Override
        public void onSkipToNext() {
            mAudioDelegate.nextAudio();
        }

        @Override
        public void onSkipToPrevious() {
            mAudioDelegate.preAudio();
        }

        @Override
        public void onStop() {
            mAudioDelegate.release();
        }

        @Override
        public void onSeekTo(long pos) {
            mAudioDelegate.seekToPosition((int)pos);
        }
    };

    private void updateMetaData(MP3 mp3) {
        if (mp3 == null) {
            mMediaSession.setMetadata(null);
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(mService.getResources(), R.drawable.audio_bg);
        File imageFile =  GlideUtils.getCacheFile(mService, mp3.audioAlbum);
        if(imageFile != null){
            bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        }
        MediaMetadataCompat.Builder metaData = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, mp3.name)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "未知")
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, "未知专辑")
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST, "未知")
//                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, 5*60*1000)
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, bitmap);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            metaData.putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, 5);
        }
        mMediaSession.setMetadata(metaData.build());
    }

    private void updatePlaybackState() {
        mMediaSession.setPlaybackState(
                new PlaybackStateCompat.Builder()
                        .setActions(MEDIA_SESSION_ACTIONS)
                        .setState(mPlaybackState, mAudioDelegate.getCurrentPosition(), 1)
                        .build());
    }

}