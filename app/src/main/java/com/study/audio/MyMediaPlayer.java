package com.study.audio;

import android.media.MediaPlayer;

public class MyMediaPlayer {
    static MediaPlayer mediaPlayer;

    public static MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            mediaPlayer.reset();
        }

        return mediaPlayer;
    }
}
