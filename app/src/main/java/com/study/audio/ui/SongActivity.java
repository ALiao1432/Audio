package com.study.audio.ui;

import android.R.drawable;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.study.audio.R;

public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageView collapseImg = (ImageView) findViewById(R.id.img_collapse);
        collapseImg.setBackgroundColor(Color.GRAY);
        collapseImg.setImageResource(drawable.ic_media_play);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_song);
        //Adapter
        SongTextAdapter songTextAdapter = new SongTextAdapter();
        //LayoutManager
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rv.setAdapter(songTextAdapter);
        rv.setLayoutManager(linearLayoutManager);
    }
}
