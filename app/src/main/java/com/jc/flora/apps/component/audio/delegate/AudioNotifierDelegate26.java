package com.jc.flora.apps.component.audio.delegate;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.NotificationTarget;
import com.jc.flora.R;
import com.jc.flora.apps.component.audio.model.MP3;

/**
 * <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
 * Created by shijincheng on 2019/4/22.
 */

public class AudioNotifierDelegate26 {

    private static final int NOTIFICATION_ID = 0x111;
    public static final String EXTRA_PLAY = "play";
    public static final String EXTRA_PAUSE = "pause";
    public static final String EXTRA_NEXT = "next";
    public static final String EXTRA_PRE = "pre";

    private Service mService;
    private Class<? extends AppCompatActivity> mActivityClass;

    private NotificationManager mNotificationManager;
    private BaseAudioDelegate mAudioDelegate;
    // 是否是亮色主题
    private boolean mIsLightNotificationTheme;

    public AudioNotifierDelegate26(Service service, Class<? extends AppCompatActivity> activityClass, BaseAudioDelegate audioDelegate) {
        mService = service;
        mActivityClass = activityClass;
        mAudioDelegate = audioDelegate;
        initNotificationManager();
        addAudioStatusListener();
        registerAudioNotifierReceiver();
    }

    private void initNotificationManager() {
        mNotificationManager = (NotificationManager) mService.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void addAudioStatusListener() {
        mAudioDelegate.addAudioStatusListener(mAudioStatusListener);
    }

    private void registerAudioNotifierReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EXTRA_PLAY);
        intentFilter.addAction(EXTRA_PAUSE);
        intentFilter.addAction(EXTRA_NEXT);
        intentFilter.addAction(EXTRA_PRE);
        mService.registerReceiver(mAudioNotifierReceiver, intentFilter);
    }

    public void release() {
        //
        mAudioDelegate.removeAudioStatusListener(mAudioStatusListener);
        //
        mService.unregisterReceiver(mAudioNotifierReceiver);
        mNotificationManager.cancelAll();
    }

    private AudioStatusListener mAudioStatusListener = new AudioStatusListener() {
        @Override
        public void onPlay() {
            mService.startForeground(NOTIFICATION_ID, buildNotification(mAudioDelegate.getCurrentMp3(), true));
        }

        @Override
        public void onPause() {
            mService.stopForeground(false);
            mNotificationManager.notify(NOTIFICATION_ID, buildNotification(mAudioDelegate.getCurrentMp3(), false));
        }

        @Override
        public void onStop() {
            mService.stopForeground(false);
            mNotificationManager.notify(NOTIFICATION_ID, buildNotification(mAudioDelegate.getCurrentMp3(), false));
        }
    };

    private BroadcastReceiver mAudioNotifierReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            switch (intent.getAction()) {
                case EXTRA_PLAY:
                    mAudioDelegate.playAudio();
                    break;
                case EXTRA_PAUSE:
                    mAudioDelegate.pauseAudio();
                    break;
                case EXTRA_NEXT:
                    mAudioDelegate.nextAudio();
                    break;
                case EXTRA_PRE:
                    mAudioDelegate.preAudio();
                    break;
                default:
                    break;
            }
        }
    };

    private Notification buildNotification(MP3 mp3, boolean isPlaying) {
        String channelId = "flora_audio_id";
        String channelName = "flora_audio_name";
        String channelDescription = "flora_audio_description";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDescription);
            channel.setSound(null, null);
            mNotificationManager.createNotificationChannel(channel);
        }

        mIsLightNotificationTheme = NotifierUtil.isLightNotificationTheme(mService);
        Intent intent = new Intent(mService, mActivityClass);
        intent.putExtra("notification", true);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mService, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews bigRemoteViews = getBigRemoteViews(mp3, isPlaying);
        RemoteViews smallRemoteViews = getRemoteViews(mp3, isPlaying);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mService, channelId)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_audio)
                .setCustomBigContentView(bigRemoteViews)
                .setCustomContentView(smallRemoteViews);
        Notification notification = builder.build();
        setCover(bigRemoteViews, notification, mp3);
        setCover(smallRemoteViews, notification, mp3);
        return notification;
    }

    private RemoteViews getBigRemoteViews(MP3 mp3, boolean isPlaying) {
        // 创建通知栏显示的视图
        RemoteViews remoteViews = new RemoteViews(mService.getPackageName(), R.layout.layout_audio13_notifier);
        // 设置mp3名称
        remoteViews.setTextViewText(R.id.tv_title, mp3.name);
        // 设置播放／暂停按钮
        if (isPlaying) {
            setBigPauseButton(remoteViews);
        } else {
            setBigPlayButton(remoteViews);
        }
        // 设置下一首按钮
        setBigNextButton(remoteViews);
        // 设置上一首按钮
        setBigPreButton(remoteViews);
        return remoteViews;
    }

    private RemoteViews getRemoteViews(MP3 mp3, boolean isPlaying) {
        // 创建通知栏显示的视图
        RemoteViews remoteViews = new RemoteViews(mService.getPackageName(), R.layout.layout_audio12_notifier);
        // 设置mp3名称
        remoteViews.setTextViewText(R.id.tv_title, mp3.name);
        // 设置播放／暂停按钮
        if (isPlaying) {
            setPauseButton(remoteViews);
        } else {
            setPlayButton(remoteViews);
        }
        // 设置下一首按钮
        setNextButton(remoteViews);
        return remoteViews;
    }

    private void setCover(RemoteViews remoteViews, Notification notification, MP3 mp3){
        // 设置mp3封面图
//        remoteViews.setImageViewResource(R.id.iv_cover, mp3.coverImgResId);
        NotificationTarget notificationTarget = new NotificationTarget(mService, R.id.iv_cover, remoteViews, notification, NOTIFICATION_ID);
        RequestOptions options = new RequestOptions()
                .override(500, 500)
                .centerCrop();
        Glide.with(mService)
                .asBitmap()
                .load(Uri.parse(mp3.audioAlbum))
                .apply(options)
                .into(notificationTarget);
    }

    private void setPlayButton(RemoteViews remoteViews) {
        setButton(remoteViews, R.id.btn_play, getPlayIconRes(), EXTRA_PLAY, 0);
    }

    private void setPauseButton(RemoteViews remoteViews) {
        setButton(remoteViews, R.id.btn_play, getPauseIconRes(), EXTRA_PAUSE, 1);
    }

    private void setNextButton(RemoteViews remoteViews) {
        setButton(remoteViews, R.id.btn_next, getNextIconRes(), EXTRA_NEXT, 2);
    }

    private void setBigPlayButton(RemoteViews remoteViews) {
        setButton(remoteViews, R.id.btn_play, R.drawable.audio_notifier_play_big, EXTRA_PLAY, 0);
    }

    private void setBigPauseButton(RemoteViews remoteViews) {
        setButton(remoteViews, R.id.btn_play, R.drawable.audio_notifier_pause_big, EXTRA_PAUSE, 1);
    }

    private void setBigNextButton(RemoteViews remoteViews) {
        setButton(remoteViews, R.id.btn_next, R.drawable.audio_notifier_next_big, EXTRA_NEXT, 2);
    }

    private void setBigPreButton(RemoteViews remoteViews) {
        setButton(remoteViews, R.id.btn_pre, R.drawable.audio_notifier_pre_big, EXTRA_PRE, 3);
    }

    private void setButton(RemoteViews remoteViews, int btnId, int iconRes, String action, int requestId) {
        Intent nextIntent = new Intent(action);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(mService, requestId, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setImageViewResource(btnId, iconRes);
        remoteViews.setOnClickPendingIntent(btnId, nextPendingIntent);
    }

    private int getPlayIconRes() {
        return mIsLightNotificationTheme ?
                R.drawable.audio_notifier_play_dark : R.drawable.audio_notifier_play_light;
    }

    private int getPauseIconRes() {
        return mIsLightNotificationTheme ?
                R.drawable.audio_notifier_pause_dark : R.drawable.audio_notifier_pause_light;
    }

    private int getNextIconRes() {
        return mIsLightNotificationTheme ?
                R.drawable.audio_notifier_next_dark : R.drawable.audio_notifier_next_light;
    }

}