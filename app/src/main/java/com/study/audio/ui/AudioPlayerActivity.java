package com.study.audio.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.study.audio.MediaData;
import com.study.audio.MyMediaPlayer;
import com.study.audio.R;

import java.io.IOException;
import java.util.List;

import static android.media.AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE;
import static android.media.AudioManager.STREAM_MUSIC;

public class AudioPlayerActivity extends AppCompatActivity {

    private final String TAG = "AudioPlayerActivity";

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private List<MediaData> mediaDataList;
    private ImageView previousImg;
    private ImageView playImg;
    private ImageView nextImg;
    private ImageView artImg;
    private TextView titleTextView;
    private TextView artistTextView;
    private Button b;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        mediaDataList = getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        currentPosition = getIntent().getIntExtra("CURRENT_POSITION", 0);

        initMediaPlayer();
        findView();
        setView();
    }

    private void initMediaPlayer() {
        int volume = 0;
        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            volume = audioManager.getStreamVolume(STREAM_MUSIC);
        }

        mediaPlayer = MyMediaPlayer.getMediaPlayer();
        try {
            mediaPlayer.setVolume((float) volume / 20, (float) volume / 20);
            mediaPlayer.setDataSource(mediaDataList.get(currentPosition).getData());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findView() {
        previousImg = findViewById(R.id.button_previous);
        playImg = findViewById(R.id.button_play);
        nextImg = findViewById(R.id.button_next);
        artImg = findViewById(R.id.album_art);
        titleTextView = findViewById(R.id.song_title);
        artistTextView = findViewById(R.id.song_artist);
        b = findViewById(R.id.button_vol);
    }

    private void setView() {
        setUiElement();
        final ClickListener clickListener = new ClickListener();
        previousImg.setOnClickListener(clickListener);
        playImg.setOnClickListener(clickListener);
        nextImg.setOnClickListener(clickListener);
        b.setOnClickListener(this::showPopWindows);
    }

    private void setUiElement() {
        RequestOptions requestOptions =
                new RequestOptions().centerCrop()
                        .placeholder(R.drawable.ic_album_black_24dp);
        Glide.with(this)
                .load(mediaDataList.get(currentPosition).getAlbumId())
                .apply(requestOptions)
                .into(artImg);

        titleTextView.setText(mediaDataList.get(currentPosition).getDisplayName());
        artistTextView.setText(mediaDataList.get(currentPosition).getArtist());
    }

    private void showPopWindows(View v) {
        PopupWindow popup = new PopupWindow(AudioPlayerActivity.this);
        View layout = getLayoutInflater().inflate(R.layout.volbar, null);
        popup.setContentView(layout);

        // Set content width and height
        popup.setHeight(300);
        popup.setWidth(300);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);

        // Show anchored to button
        //popup.showAsDropDown(v);
        popup.showAtLocation(v, Gravity.START, 0, 200);

        SeekBar volBar = layout.findViewById(R.id.seekBar_vol);
        volBar.setBackgroundColor(Color.RED);
        volBar.setProgress(audioManager.getStreamVolume(STREAM_MUSIC) * 5);
        volBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = (float) progress / 100;
                mediaPlayer.setVolume(volume, volume);
                audioManager.setStreamVolume(STREAM_MUSIC, progress / 5, FLAG_REMOVE_SOUND_AND_VIBRATE);
            }
        });
    }

    private void changeSong(int index) {
        if (index >= 0 && index < mediaDataList.size()) {
            currentPosition = index;
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(mediaDataList.get(currentPosition).getData());
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            setUiElement();
            playImg.setSelected(false);
        } else {
            Toast.makeText(AudioPlayerActivity.this, "No previous song~~~", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPlayFiveSecs() {
        return mediaPlayer.getCurrentPosition() / 1000 > 5f;
    }

    private class ClickListener implements View.OnClickListener {

        // TODO: 2018/9/3 Implement the Functionality of Music Player

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_previous:
                    if (isPlayFiveSecs()) {
                        mediaPlayer.seekTo(0);
                    } else {
                        changeSong(currentPosition - 1);
                    }
                    break;
                case R.id.button_play:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        playImg.setSelected(true);
                    } else {
                        mediaPlayer.start();
                        playImg.setSelected(false);
                    }
                    break;
                case R.id.button_next:
                    changeSong(currentPosition + 1);
                    break;
            }
        }
    }

    // TODO: 2018/9/3 Create the Class to Load the Content of Music
}
