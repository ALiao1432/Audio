package com.study.audio.ui;

import android.R.drawable;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.audio.R;


public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder>{

    private Context mContext;
    private MediaMetadataCompat media;

    public SongListAdapter() {
        //this.mContext = mContext;

    }

    @NonNull
    @Override
    public SongListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View songView = inflater.inflate(R.layout.cardview_song, parent, false);
        songView.setOnClickListener(v -> {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                //intent.putExtra("metadata", media);
//                startActivity(intent);
                }
        );
        //AlbumGalleryAdapter.ViewHolder viewHolder = new AlbumGalleryAdapter.ViewHolder(albumView);

        return new SongListAdapter.ViewHolder(songView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongListAdapter.ViewHolder holder, int position) {

        // TODO: 2018/8/20 Load the Media Data

//        media = MusicLibrary.getMetadata(mContext,"Jazz_In_Paris");
//        holder.albumCardView.setBackgroundResource(R.drawable.album_youtube_audio_library_rock_2);
//        holder.albumImageView.setImageBitmap(MusicLibrary.getAlbumBitmap(
//                mContext,
//                media.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)));
//        holder.albumImageView.setBackgroundResource(drawable.ic_media_play);
        holder.songImageView.setImageResource(drawable.ic_media_play);
        holder.songImageView.setBackgroundColor(Color.GRAY);
        holder.songTextView.setText("Jazz_In_Paris");
        holder.songTextView.setTextColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView songCardView;
        public TextView songTextView;
        public TextView songArtistTextView;
        public TextView songTimeTextView;
        public ImageView songImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            songCardView = (CardView) itemView.findViewById(R.id.card_view);
            songTextView = (TextView) itemView.findViewById(R.id.card_song_name);
            songImageView = (ImageView) itemView.findViewById(R.id.card_song_img);
            songArtistTextView = (TextView) itemView.findViewById(R.id.card_song_artist);
            songTimeTextView = (TextView) itemView.findViewById(R.id.card_song_time);
        }
    }

}