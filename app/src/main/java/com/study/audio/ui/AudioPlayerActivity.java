package com.study.audio.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_previous:

                    break;
                case R.id.button_play:

                    break;
                case R.id.button_next:

                    break;
            }
        }
    }
}
