package com.study.audio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.study.audio.ui.Song;
import com.study.audio.ui.SongListAdapter;

import java.util.ArrayList;

// test for git
public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private final int MY_WRITE_EXTERNAL_REQUEST_CODE = 999;
    private RecyclerView recyclerView;
    private ArrayList<Song> songList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (needToAskPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                || needToAskPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showPermissionDialog();
        } else {
            if (getMusics()) {
                findView();
                setView();
            }
        }
    }

    private boolean needToAskPermission(String p) {
        return this.checkPermission(p, Process.myPid(), Process.myUid())
                == PackageManager.PERMISSION_DENIED;
    }

    private void showPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.need_permission)
                .setMessage(R.string.permission_dialog_content)
                .setPositiveButton(R.string.permission_dialog_accept, (dialogInterface, i) -> askPermission())
                .setNegativeButton(R.string.permission_dialog_cancel, (dialogInterface, i) -> finish())
                .setCancelable(false)
                .show();
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_WRITE_EXTERNAL_REQUEST_CODE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_WRITE_EXTERNAL_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (getMusics()) {
                    findView();
                    setView();
                }
            } else {
                finish();
            }
        }
    }

    private void findView() {
        recyclerView = findViewById(R.id.main_recyclerView);
    }

    private void setView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        songList.forEach(song -> Log.d(TAG, "setView!!!: " + song));

        recyclerView.setAdapter(new SongListAdapter(songList));
    }

    private boolean getMusics() {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projections = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION
        };
        String order = MediaStore.Audio.Media.ARTIST;

        try {
            Cursor mediaCursor = getContentResolver().query(
                    uri,
                    projections,
                    null,
                    null,
                    order
            );
            if (mediaCursor != null) {
                while (mediaCursor.moveToNext()) {
                    songList.add(new Song(
                            mediaCursor.getString(0),
                            mediaCursor.getString(1),
                            mediaCursor.getString(2),
                            "",
                            mediaCursor.getString(3),
                            mediaCursor.getString(4)
                    ));
                }
                mediaCursor.close();
            }

            getAlbumArtPath();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void getAlbumArtPath() {
        String order = MediaStore.Audio.AlbumColumns.ARTIST;
        String[] projections = {
                MediaStore.Audio.AlbumColumns.ALBUM_ART,
                MediaStore.Audio.AlbumColumns.ALBUM
        };
        String ALBUM_URI = "content://media/external/audio/albums";

        Cursor cursor = getContentResolver().query(
                Uri.parse(ALBUM_URI),
                projections,
                null,
                null,
                order
        );

        ArrayList<String> tempAlbumArtList = new ArrayList<>();
        ArrayList<String> tempAlbumList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                tempAlbumArtList.add(cursor.getString(0));
                tempAlbumList.add(cursor.getString(1));
            }
        }

        songList.forEach(song -> {
            int i = tempAlbumList.indexOf(song.getAlbum());
            song.setAlbumId(tempAlbumArtList.get(i));
        });

        if (cursor != null) {
            cursor.close();
        }
    }
}
