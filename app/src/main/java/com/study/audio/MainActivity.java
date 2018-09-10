package com.study.audio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.study.audio.ui.SongListAdapter;

// test for git
public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        setView();
    }

    private void findView() {
        recyclerView = findViewById(R.id.main_recyclerView);
    }

    private void setView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SongListAdapter());
    }
}
