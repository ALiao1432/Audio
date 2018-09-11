package com.study.audio.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.audio.MediaData;
import com.study.audio.R;

import java.util.ArrayList;
import java.util.List;

public class SongTextAdapter extends RecyclerView.Adapter<SongTextAdapter.ViewHolder> {

    private final String TAG = "SongTextAdapter";

    private Context mContext;
    private List<MediaData> mediaDataList;

    SongTextAdapter(List<MediaData> mediaDataList) {
        // TODO: 2018/9/3 Constructor with Music Structure
        this.mediaDataList = mediaDataList;
    }

    @NonNull
    @Override
    public SongTextAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View songView = inflater.inflate(R.layout.textview_song, parent, false);

        return new SongTextAdapter.ViewHolder(songView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongTextAdapter.ViewHolder holder, int position) {

        // TODO: 2018/8/20 Load the Media Data
        String songText = mediaDataList.get(position).getDisplayName();
        String artistText = mediaDataList.get(position).getArtist();
        int duration = Integer.valueOf(mediaDataList.get(position).getDuration()) / 1000;
        int min = duration / 60;
        int sec = duration % 60;
        String time;
        if (sec < 10) {
            time = min + ":0" + sec;
        } else {
            time = min + ":" + sec;
        }

        holder.songTextView.setText(songText);
        holder.songTextView.setEllipsize(TextUtils.TruncateAt.END);
        holder.songTextView.setSingleLine(true);
        holder.songTextView.setTextColor(Color.BLACK);

        holder.songArtistTextView.setText(artistText);

        holder.songTimeTextView.setText(time);

        holder.songCardView.setOnClickListener(v -> {
            mediaDataList.forEach(mediaData -> Log.d(TAG, "onBindViewHolder: mediaData : " + mediaData));
            Intent i = new Intent(mContext, AudioPlayerActivity.class);
            i.putExtra("CURRENT_POSITION", position);
            i.putParcelableArrayListExtra(Intent.EXTRA_STREAM, (ArrayList<MediaData>) mediaDataList);
            mContext.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return mediaDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView songCardView;
        TextView songTextView;
        TextView songArtistTextView;
        TextView songTimeTextView;

        ViewHolder(View itemView) {
            super(itemView);
            songCardView = itemView.findViewById(R.id.text_cardView);
            songTextView = itemView.findViewById(R.id.text_song_name);
            songArtistTextView = itemView.findViewById(R.id.text_song_artist);
            songTimeTextView = itemView.findViewById(R.id.text_song_time);
        }
    }
}
