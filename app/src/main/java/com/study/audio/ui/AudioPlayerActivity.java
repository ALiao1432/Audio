package com.study.audio.ui;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.study.audio.R;

public class AudioPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        Button b = (Button) findViewById(R.id.button_vol);
        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

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

                SeekBar volbar1 = (SeekBar) layout.findViewById(R.id.seekBar_vol);
                volbar1.setBackgroundColor(Color.RED);
                //volbar1.setProgress(currentProgr);
                volbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser) {
                        //currentProgr=progress;
                    }
                });

            }
        });

        final ClickListener clickListener = new ClickListener();
        findViewById(R.id.button_previous).setOnClickListener(clickListener);
        findViewById(R.id.button_play).setOnClickListener(clickListener);
        findViewById(R.id.button_next).setOnClickListener(clickListener);
    }

    private class ClickListener implements View.OnClickListener {

        // TODO: 2018/9/3 Implement the Functionality of Music Player

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_previous:

                    break;
                case R.id.button_play:
//                    if (mediaPlayer.isPlaying()) {
//                        playImg.setSelected(true);
//                    } else {
//                        playImg.setSelected(false);
//                    }
                    break;
                case R.id.button_next:

                    break;
            }
        }
    }

    // TODO: 2018/9/3 Create the Class to Load the Content of Music
    public ArrayList<Song> getMusics(){
		 
       ArrayList<Song> songlist = new ArrayList<Song>();
     ContentResolver contentResolver = getContentResolver();
 	  Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
 	  Cursor cursor = contentResolver.query(uri, null, null, null, null);
 	  if (cursor == null) {
 	    Log.d("=======>", "noting");
 	} else if (!cursor.moveToFirst()) {
 	    Log.d("=======>", "done");
 	} else {
      
              while (!cursor.isAfterLast()) {
       	    int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
           
           String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
           
           String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
           
           String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
         
           int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
           
           String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
           int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
           
             Song song = new Song();
             song.setId(id);
             song.setTitle(tilte);
             song.setArtist(artist);
             song.setUrl(url);
             song.setTime(duration);
             song.setAlbum(album);
             song.setAlbumId(albumId);
             songlist.add(song));

             cursor.moveToNext();
       }
   }
   return songlist;
}
}
