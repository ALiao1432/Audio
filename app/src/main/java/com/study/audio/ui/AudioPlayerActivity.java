package com.study.audio.ui;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.study.audio.R;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AudioPlayerActivity extends AppCompatActivity {

    private final String TAG = "AudioPlayerActivity";

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private ImageView btn_play;
    private ImageView btn_previous;
    private ImageView btn_next;
    private ImageView artImg;
    private TextView titleText;
    private TextView artistText;
    private SeekBar skBar;
    private SeekBar volbar1;
    private Button b;
    private List<Song> songList;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        songList = getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        currentPosition = getIntent().getIntExtra("CURRENT_POSITION", 0);

        initMediaPlayer();
        findView();
        setView();
    }

    private void showPopWindows(View v) {
        PopupWindow popup = new PopupWindow(AudioPlayerActivity.this);
        View layout = getLayoutInflater().inflate(R.layout.volbar, null);
        popup.setContentView(layout);

        // Set content width and height
        popup.setHeight(300);
        popup.setWidth(300);
        popup.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);

        // Show anchored to button
        //popup.showAsDropDown(v);
        popup.showAtLocation(v, Gravity.LEFT, 0, 200);

        volbar1 = layout.findViewById(R.id.seekBar_vol);
        volbar1.setBackgroundColor(Color.RED);
        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //给volume bar设置最大值和默认值（先获取系统Music音量的最大值和当前值）
        volbar1.setMax(maxVol);
        volbar1.setProgress(currentVol);
        //volbar1.setProgress(currentProgr);

        volbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

                int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                int currentVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                volbar1.setMax(maxVol);
                volbar1.setProgress(currentVol);
            }
        });

        myRegisterReceiver();
    }

    private void findView() {
        b = findViewById(R.id.button_vol);
        btn_previous = findViewById(R.id.button_previous);
        btn_play = findViewById(R.id.button_play);
        btn_next = findViewById(R.id.button_next);
        skBar = findViewById(R.id.seekbar_audio);
        artImg = findViewById(R.id.album_art);
        titleText = findViewById(R.id.song_title);
        artistText = findViewById(R.id.song_artist);

        titleText.setText(songList.get(currentPosition).getDisplayName());
        artistText.setText(songList.get(currentPosition).getArtist());

        RequestOptions requestOptions =
                new RequestOptions().centerCrop().placeholder(R.drawable.ic_album_black_24dp);
        Glide.with(this)
                .load(songList.get(currentPosition).getAlbumId())
                .apply(requestOptions)
                .into(artImg);
    }

    private void setView() {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindows(v);
            }
        });
        final ClickListener clickListener = new ClickListener();
        btn_previous.setOnClickListener(clickListener);
        btn_play.setOnClickListener(clickListener);
        btn_next.setOnClickListener(clickListener);
        skBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skBar.getProgress());
            }
        });
    }

    private class ClickListener implements View.OnClickListener {
        // TODO: 2018/9/19 hi Anly, you can switch previous/next song base on 'songList' and 'currentPosition'

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_previous:

                    break;
                case R.id.button_play:
                    SetPlay();
                    updateSeekBar();
                    // System.out.println("按钮被点击了");
                    break;
                case R.id.button_next:

                    break;
            }
        }
    }


    public void initMediaPlayer() {
        try {
            mediaPlayer.setDataSource(songList.get(currentPosition).getData());
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SetPlay() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            //btn_play.setImageResource(com.android.internal.R.drawable.ic_media_pause);

            btn_play.setImageResource(Resources.getSystem().getIdentifier("ic_media_pause", "drawable", "android"));
        } else {
            mediaPlayer.pause();
            btn_play.setImageResource(Resources.getSystem().getIdentifier("ic_media_play", "drawable", "android"));

        }

    }

    private void updateSeekBar() {
        final int duration = mediaPlayer.getDuration();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //一秒钟获取一次当前进度
                int currentPosition = mediaPlayer.getCurrentPosition();
                //设置seekbar的进度
                skBar.setMax(duration);
                skBar.setProgress(currentPosition);
            }
        };
        timer.schedule(task, 100, 1000);

    }

    /**
     * 注册当音量发生变化时接收的广播
     */
    private void myRegisterReceiver() {
        MyVolumeReceiver mVolumeReceiver = new MyVolumeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        this.registerReceiver(mVolumeReceiver, filter);
        // Context.
    }

    /**
     * 处理音量变化时的界面显示
     *
     * @author long
     */
    private class MyVolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //如果音量发生变化则更改seekbar的位置
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                int currVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);// 当前的媒体音量
                volbar1.setProgress(currVolume);
            }
        }
    }
}
