package com.study.audio.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.study.audio.MediaData;
import com.study.audio.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder> {

    private Context mContext;
    private List<MediaData> mediaDataList;

    SongListAdapter(List<MediaData> mediaDataList) {
        // TODO: 2018/9/3 Constructor with Music Structure
        this.mediaDataList = mediaDataList;
        this.mediaDataList =
                this.mediaDataList
                        .stream()
                        .sorted(Comparator.comparing(MediaData::getData))
                        .collect(Collectors.toList());
    }

    @NonNull
    @Override
    public SongListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View songView = inflater.inflate(R.layout.cardview_song, parent, false);

        return new SongListAdapter.ViewHolder(songView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongListAdapter.ViewHolder holder, int position) {
        // TODO: 2018/8/20 Load the Media Data
        RequestOptions requestOptions =
                new RequestOptions().centerCrop()
                        .placeholder(R.drawable.ic_album_black_24dp);
        Glide.with(mContext)
                .load(mediaDataList.get(position).getAlbumId())
                .apply(requestOptions)
                .into(holder.songImageView);

        int duration = Integer.valueOf(mediaDataList.get(position).getDuration()) / 1000;
        int min = duration / 60;
        int sec = duration % 60;
        String time;
        if (sec < 10) {
            time = min + ":0" + sec;
        } else {
            time = min + ":" + sec;
        }
        holder.songTimeTextView.setText(time);
        holder.songArtistTextView.setText(mediaDataList.get(position).getArtist());
        holder.songTextView.setText(mediaDataList.get(position).getDisplayName());
        holder.songTextView.setTextColor(Color.BLACK);
        holder.songTextView.setEllipsize(TextUtils.TruncateAt.END);
        holder.songTextView.setSingleLine(true);

        holder.songCardView.setOnClickListener(v -> {
            Intent i = new Intent(mContext, AudioPlayerActivity.class);
            i.putParcelableArrayListExtra(Intent.EXTRA_STREAM, (ArrayList<MediaData>) mediaDataList);
            i.putExtra("CURRENT_POSITION", position);
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
        ImageView songImageView;

        ViewHolder(View itemView) {
            super(itemView);
            songCardView = itemView.findViewById(R.id.card_view);
            songTextView = itemView.findViewById(R.id.card_song_name);
            songImageView = itemView.findViewById(R.id.card_song_img);
            songArtistTextView = itemView.findViewById(R.id.card_song_artist);
            songTimeTextView = itemView.findViewById(R.id.card_song_time);
        }
    }
}