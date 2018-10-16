package com.study.audio;

import android.media.MediaPlayer;

public class MyMediaPlayer {

    private static MediaPlayer mediaPlayer = new MediaPlayer();

    private MyMediaPlayer() {

    }

    public static MediaPlayer getMediaPlayer() {
        mediaPlayer.reset();
        return mediaPlayer;
    }
}
