package com.study.audio;

import android.os.Parcel;
import android.os.Parcelable;

public class MusicData implements Parcelable{

    private long id;
    private String title;
    private String artist;
    private String album;
    private long albumId;
    private long duration;
    private String path;

    public MusicData(long id, String title, String artist, String album, long albumId, long duration, String path) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.albumId = albumId;
        this.duration = duration;
        this.path = path;
    }

    protected MusicData(Parcel in) {
        id = in.readLong();
        title = in.readString();
        artist = in.readString();
        album = in.readString();
        albumId = in.readLong();
        duration = in.readLong();
        path = in.readString();
    }

    public static final Creator<MusicData> CREATOR = new Creator<MusicData>() {
        @Override
        public MusicData createFromParcel(Parcel in) {
            return new MusicData(in);
        }

        @Override
        public MusicData[] newArray(int size) {
            return new MusicData[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(artist);
        parcel.writeString(album);
        parcel.writeLong(albumId);
        parcel.writeLong(duration);
        parcel.writeString(path);
    }
}
