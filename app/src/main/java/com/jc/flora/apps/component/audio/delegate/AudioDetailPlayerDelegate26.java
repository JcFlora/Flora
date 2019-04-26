package com.jc.flora.apps.component.audio.delegate;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.audio.model.MP3;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by shijincheng on 2019/4/22.
 */

public class AudioDetailPlayerDelegate26 {

    // 进度条下面的当前进度文字，将毫秒化为mm:ss格式
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("mm:ss", Locale.getDefault());
    // 播放模式名称列表
    private static final String[] MODE_ITEMS = {"列表循环", "单曲循环", "随机播放"};
    // 播放模式图标
    private static final int[] MODE_ICON_RES_IDS = {R.drawable.audio_loop, R.drawable.audio_single, R.drawable.audio_shuffle};
    // 倍速名称列表
    private static final String[] SPEED_ITEMS = {"0.75X", "1.0X", "1.2X", "1.5X", "2.0X"};
    // mp3列表
    private static final ArrayList<MP3> MP3_LIST = new ArrayList<MP3>() {
        {
            add(new MP3(
                    "话说明朝",
                    "http://imagev2.xmcdn.com/group29/M0A/EF/E3/wKgJWVk9_IzwKhYSAALu_VmdTNU780.jpg",
                    "http://fdfs.xmcdn.com/group33/M08/D9/53/wKgJTFmlRnbh_TYKAI7ECmC5Ar0312.mp3"));
            add(new MP3(
                    "百思女神秀",
                    "http://imagev2.xmcdn.com/group24/M03/B0/DF/wKgJMFiv-vDTK5vMAAF7QprBHfM982.jpg",
                    "http://audio.xmcdn.com/group28/M08/53/44/wKgJXFlolI-h7V3tAJv65WyL-2w446.mp3"));
            add(new MP3("欢乐江湖",
                    "http://imagev2.xmcdn.com/group31/M08/C0/C1/wKgJSVl6t6yz7Uc_AAPh7XreoIY059.jpg",
                    "http://audio.xmcdn.com/group60/M06/D4/23/wKgLeVy2irnRvgn8AIajOcTEqmw907.mp3"));
            add(new MP3(
                    "万万妹想到",
                    "http://imagev2.xmcdn.com/group46/M06/1D/E6/wKgKj1tzoYvT2vTaAAG4z2Hwi0o546.jpg",
                    "http://aod.tx.xmcdn.com/group58/M0B/0F/24/wKgLc1y5nOzgvS3PAKoJOL7U_s4990.mp3"));
            add(new MP3("爆笑相声",
                    "http://fdfs.xmcdn.com/group34/M05/CD/8A/wKgJYVntnamR9f32AAfwumDq4eM178_mobile_large.jpg",
                    "http://audio.xmcdn.com/group42/M08/93/BA/wKgJ81qY_HPC8A5_AGtb0aEwncA343.mp3"));
            add(new MP3("每天一个心理知识",
                    "http://fdfs.xmcdn.com/group41/M02/09/B6/wKgJ8lqTzluhjBw7AAGHo1SIL8A073_mobile_large.jpg",
                    "http://aod.tx.xmcdn.com/group58/M01/5B/AA/wKgLc1y9ZZSwIEI_ABX7_5NHUIE189.mp3"));
            add(new MP3("世界名人英文演讲",
                    "http://imagev2.xmcdn.com/group44/M06/47/DA/wKgKkVsPo6uB84WPABR_5iGVCX8204.png",
                    "http://audio.xmcdn.com/group59/M07/60/DD/wKgLely9mLuDau_4ACwyApwo5rI031.mp3"));
            add(new MP3("悦读心时光",
                    "http://imagev2.xmcdn.com/group21/M06/42/D0/wKgJLVs10eTi3kEcAAFR5Cec3Bc569.jpg",
                    "http://aod.tx.xmcdn.com/group56/M05/11/3A/wKgLdlxQY2OC8NZcAEssQdU-UDA049.mp3"));
        }
    };

    private AppCompatActivity mActivity;
    private Class<? extends Service> mServiceClass;

    private BaseAudioDelegate mDelegate;
    // 当前mp3音频封面图
    private ImageView mIvCover;
    // 当前播放进度时间显示
    private TextView mTvCurrentTime;
    // 播放进度条
    private SeekBar mSbProgress;
    // 总进度时间显示
    private TextView mTvMaxTime;
    // 当前播放模式
    private ImageView mBtnMode;
    // 上一首按钮
    private ImageView mBtnPre;
    // 播放按钮
    private ImageView mBtnPlay;
    // 下一首按钮
    private ImageView mBtnNext;
    // 下一首按钮
    private ImageView mBtnSelect;
    // 快退15秒按钮
    private View mBtnRewind;
    // 快进15秒按钮
    private View mBtnForward;
    // 倍速按钮
    private TextView mBtnSpeed;

    // 状态标记，标识是否正在播放，用来控制播放按钮
    private boolean mIsPlaying;
    // 下一个播放模式
    private AudioPlayMode mNextMode = AudioPlayMode.SINGLE;

    public AudioDetailPlayerDelegate26(AppCompatActivity activity, Class<? extends Service> serviceClass) {
        mActivity = activity;
        mServiceClass = serviceClass;
    }

    public void setIvCover(ImageView ivCover) {
        mIvCover = ivCover;
    }

    public void setTvCurrentTime(TextView tvCurrentTime) {
        mTvCurrentTime = tvCurrentTime;
    }

    public void setSbProgress(SeekBar sbProgress) {
        mSbProgress = sbProgress;
    }

    public void setTvMaxTime(TextView tvMaxTime) {
        mTvMaxTime = tvMaxTime;
    }

    public void setBtnMode(ImageView btnMode) {
        mBtnMode = btnMode;
    }

    public void setBtnPre(ImageView btnPre) {
        mBtnPre = btnPre;
    }

    public void setBtnPlay(ImageView btnPlay) {
        mBtnPlay = btnPlay;
    }

    public void setBtnNext(ImageView btnNext) {
        mBtnNext = btnNext;
    }

    public void setBtnSelect(ImageView btnSelect) {
        mBtnSelect = btnSelect;
    }

    public void setBtnRewind(View btnRewind) {
        mBtnRewind = btnRewind;
    }

    public void setBtnForward(View btnForward) {
        mBtnForward = btnForward;
    }

    public void setBtnSpeed(TextView btnSpeed) {
        mBtnSpeed = btnSpeed;
    }

    public void init() {
        initViews();
        initDelegate();
    }

    public void release() {
        mDelegate.removeAudioStatusListener(mAudioStatusListener);
        mActivity.unbindService(mConnection);
    }

    private void initViews() {
        // 设置手动滑动监听
        mSbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //这里很重要，如果不判断是否来自用户操作进度条，会不断执行下面语句块里面的逻辑，然后就会卡顿
                if (fromUser && mDelegate != null) {
                    mDelegate.seekToPosition(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mBtnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastDelegate.show(mActivity, MODE_ITEMS[mNextMode.value()]);
                mDelegate.setPlayMode(mNextMode);
            }
        });
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsPlaying) {
                    mDelegate.pauseAudio();
                } else {
                    mDelegate.playAudio();
                }
            }
        });
        mBtnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.preAudio();
            }
        });
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.nextAudio();
            }
        });
        mBtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMp3ListDialog();
            }
        });
        mBtnRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.rewind();
            }
        });
        mBtnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.forward();
            }
        });
        mBtnSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpeedListDialog();
            }
        });
    }

    private void initDelegate() {
        Intent intent = new Intent(mActivity, mServiceClass);
        mActivity.bindService(intent, mConnection, Activity.BIND_AUTO_CREATE);
    }

    // 使用后台Service播放音频，界面和后台Service的连接对象，通过此对象进行通信
    private ServiceConnection mConnection = new ServiceConnection() {
        // 连接Service时回调，保存控制播放组件
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDelegate = (BaseAudioDelegate) service;
            mDelegate.addAudioStatusListener(mAudioStatusListener);
            mDelegate.syncMp3List();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    // 之前的界面刷新功能改成监听回调实现，实现控制反转，由播放状态本身进行控制
    private AudioStatusListener mAudioStatusListener = new AudioStatusListener() {

        @Override
        public void onModeSelect(int index) {
            mBtnMode.setImageResource(MODE_ICON_RES_IDS[index]);
            mNextMode = AudioPlayMode.valueOf((++index) % 3);
        }

        @Override
        public void onSpeedSelect(int index) {
            mBtnSpeed.setText(SPEED_ITEMS[index]);
        }

        @Override
        public void onSelect(int index, int maxProgress) {
            // 设置当前音频封面图
            MP3_LIST.get(mDelegate.getCurrentMp3Index()).loadAlbum(mIvCover);
            // 设置当前音频播放最大进度值
            mSbProgress.setMax(maxProgress);
            // 设置当前音频总时间
            mTvMaxTime.setText(FORMAT.format(maxProgress));
        }

        @Override
        public void onPlay() {
            mIsPlaying = true;
            mBtnPlay.setImageResource(R.drawable.audio_pause);
        }

        @Override
        public void onPause() {
            mIsPlaying = false;
            mBtnPlay.setImageResource(R.drawable.audio_play);
        }

        @Override
        public void onStop() {
            mIsPlaying = false;
            mBtnPlay.setImageResource(R.drawable.audio_play);
        }

        @Override
        public void onProgress(int progress) {
            // 设置当前进度值
            mSbProgress.setProgress(progress);
            // 设置时间
            mTvCurrentTime.setText(FORMAT.format(progress));
        }

    };

    private void showMp3ListDialog() {
        final String[] ITEMS = {"话说明朝", "百思女神秀", "欢乐江湖", "万万妹想到", "爆笑相声", "每天一个心理知识", "世界名人英文演讲", "悦读心时光"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("选择歌曲");
        builder.setItems(ITEMS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mDelegate.selectAudio(which);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    private void showSpeedListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("选择速度");
        builder.setItems(SPEED_ITEMS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mDelegate.setSpeed(AudioPlaySpeed.speedOf(which));
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

}