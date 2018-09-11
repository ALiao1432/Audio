package com.study.audio.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaActionSound;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


class AlbumGalleryAdapter extends RecyclerView.Adapter<AlbumGalleryAdapter.ViewHolder> {

    private final String TAG = "AlbumGalleryAdapter";

    private Context mContext;
    private Map<String, List<MediaData>> albumMap;
    private List<String> keyList;

    AlbumGalleryAdapter(List<MediaData> mediaDataList) {
        // TODO: 2018/9/3 Constructor with Music Structure
        albumMap = mediaDataList
                .stream()
                .collect(Collectors.groupingBy(MediaData::getAlbum));
        keyList = new ArrayList<>(albumMap.keySet());
    }

    @NonNull
    @Override
    public AlbumGalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View albumView = inflater.inflate(R.layout.cardview_album, parent, false);

        return new AlbumGalleryAdapter.ViewHolder(albumView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumGalleryAdapter.ViewHolder holder, int position) {

        // TODO: 2018/8/20 Load the Media Data
        List<MediaData> tempList = albumMap.get(keyList.get(position));
        String albumArt = tempList.get(0).getAlbumId();
        String album = tempList.get(0).getAlbum();
        String artist = tempList.get(0).getArtist();

        RequestOptions requestOptions =
                new RequestOptions().centerCrop()
                        .placeholder(R.drawable.ic_album_black_24dp);
        Glide.with(mContext)
                .load(albumArt)
                .apply(requestOptions)
                .into(holder.albumImageView);

        holder.albumTextView.setText(album);
        holder.albumTextView.setTextColor(Color.BLACK);
        holder.albumTextView.setSingleLine(true);
        holder.albumTextView.setEllipsize(TextUtils.TruncateAt.END);

        holder.albumArtistTextView.setText(artist);

        holder.albumCardView.setOnClickListener(v -> {
            Intent i = new Intent(mContext, SongActivity.class);
            i.putParcelableArrayListExtra(Intent.EXTRA_STREAM, (ArrayList<MediaData>) tempList);
            mContext.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return keyList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView albumCardView;
        TextView albumTextView;
        TextView albumArtistTextView;
        ImageView albumImageView;

        ViewHolder(View itemView) {
            super(itemView);
            albumCardView = itemView.findViewById(R.id.card_view);
            albumTextView = itemView.findViewById(R.id.card_album_text);
            albumArtistTextView = itemView.findViewById(R.id.card_album_artist);
            albumImageView = itemView.findViewById(R.id.card_album_img);
        }
    }
}
