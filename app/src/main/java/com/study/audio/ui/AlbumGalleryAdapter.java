package com.study.audio.ui;

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


class AlbumGalleryAdapter extends RecyclerView.Adapter<AlbumGalleryAdapter.ViewHolder>{

    private Context mContext;
    private MediaMetadataCompat media;

    public AlbumGalleryAdapter() {
        //this.mContext = mContext;

    }

    @NonNull
    @Override
    public AlbumGalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View albumView = inflater.inflate(R.layout.cardview_album, parent, false);
        albumView.setOnClickListener(v -> {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                //intent.putExtra("metadata", media);
//                startActivity(intent);
                }
        );
        //AlbumGalleryAdapter.ViewHolder viewHolder = new AlbumGalleryAdapter.ViewHolder(albumView);

        return new AlbumGalleryAdapter.ViewHolder(albumView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumGalleryAdapter.ViewHolder holder, int position) {

        // TODO: 2018/8/20 Load the Media Data

//        media = MusicLibrary.getMetadata(mContext,"Jazz_In_Paris");
//        holder.albumCardView.setBackgroundResource(R.drawable.album_youtube_audio_library_rock_2);
//        holder.albumImageView.setImageBitmap(MusicLibrary.getAlbumBitmap(
//                mContext,
//                media.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)));
        holder.albumImageView.setImageResource(android.R.drawable.ic_media_play);
        holder.albumImageView.setBackgroundColor(Color.GRAY);
        holder.albumTextView.setText("Jazz_In_Paris");
        holder.albumTextView.setTextColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView albumCardView;
        public TextView albumTextView;
        public TextView albumArtistTextView;
        public ImageView albumImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            albumCardView = (CardView) itemView.findViewById(R.id.card_view);
            albumTextView = (TextView) itemView.findViewById(R.id.card_album_text);
            albumArtistTextView = (TextView) itemView.findViewById(R.id.card_album_artist);
            albumImageView = (ImageView) itemView.findViewById(R.id.card_album_img);
        }
    }

}
