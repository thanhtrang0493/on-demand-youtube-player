package com.vcoders.on_demand_youtube_player.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class ResourceId implements Parcelable{
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("videoId")
    @Expose
    private String videoId;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ResourceId(Parcel in) {
        this.videoId = in.readString();
        this.kind = in.readString();
    }

    public ResourceId() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kind);
        dest.writeString(this.kind);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ResourceId createFromParcel(Parcel in) {
            return new ResourceId(in);
        }

        public ResourceId[] newArray(int size) {
            return new ResourceId[size];
        }
    };
}
