package com.study.audio;

import android.os.Parcel;
import android.os.Parcelable;

public class MediaData implements Parcelable {
    private String data;
    private String displayName;
    private String album;
    private String albumId;
    private String artist;
    private String duration;

    public MediaData(String data, String displayName, String album, String albumId, String artist, String duration) {
        this.data = data;
        this.displayName = displayName;
        this.album = album;
        this.albumId = albumId;
        this.artist = artist;
        this.duration = duration;
    }

    protected MediaData(Parcel in) {
        data = in.readString();
        displayName = in.readString();
        album = in.readString();
        albumId = in.readString();
        artist = in.readString();
        duration = in.readString();
    }

    public static final Creator<MediaData> CREATOR = new Creator<MediaData>() {
        @Override
        public MediaData createFromParcel(Parcel in) {
            return new MediaData(in);
        }

        @Override
        public MediaData[] newArray(int size) {
            return new MediaData[size];
        }
    };

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "MediaData{" +
//                "data='" + data + '\'' +
                ", displayName='" + displayName + '\'' +
                ", album='" + album + '\'' +
//                ", albumId='" + albumId + '\'' +
//                ", artist='" + artist + '\'' +
//                ", duration='" + duration + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
        dest.writeString(displayName);
        dest.writeString(album);
        dest.writeString(albumId);
        dest.writeString(artist);
        dest.writeString(duration);
    }
}
