package com.study.audio.ui;

import android.R.drawable;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.study.audio.MediaData;
import com.study.audio.R;

import java.util.List;

public class SongActivity extends AppCompatActivity {

    private final String TAG = "SongActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        Toolbar toolbar = findViewById(R.id.toolbar_song);
//        toolbar.setBackgroundColor(Color.BLACK);
        toolbar.setNavigationIcon(drawable.ic_menu_revert);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), AudioPlayerActivity.class);
            startActivity(i);
        });

        // TODO: 2018/9/3 Paste the Album Text and Image

        List<MediaData> mediaDataList = getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);

        ImageView collapseImg = findViewById(R.id.img_collapse);
        collapseImg.setBackgroundColor(Color.GRAY);

        RequestOptions requestOptions =
                new RequestOptions().centerCrop()
                        .placeholder(R.drawable.ic_album_black_24dp);
        Glide.with(this)
                .load(mediaDataList.get(0).getAlbumId())
                .apply(requestOptions)
                .into(collapseImg);

        RecyclerView rv = findViewById(R.id.rv_song);
        //Adapter
        SongTextAdapter songTextAdapter = new SongTextAdapter(mediaDataList);
        //LayoutManager
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rv.setAdapter(songTextAdapter);
        rv.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                Log.i("OptionsSelect", "Return" + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }
}
