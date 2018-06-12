package com.vcoders.on_demand_youtube_player.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class ContentDetails implements Parcelable{
    @SerializedName("itemCount")
    @Expose
    private Integer itemCount;
    @SerializedName("videoId")
    @Expose
    private String videoId;
    @SerializedName("videoPublishedAt")
    @Expose
    private String videoPublishedAt;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoPublishedAt() {
        return videoPublishedAt;
    }

    public void setVideoPublishedAt(String videoPublishedAt) {
        this.videoPublishedAt = videoPublishedAt;
    }


    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ContentDetails(Parcel in) {
        this.videoPublishedAt = in.readString();
        this.videoId = in.readString();
        this.itemCount = in.readInt();
    }

    public ContentDetails() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.videoPublishedAt);
        dest.writeString(this.videoId);
        dest.writeInt(this.itemCount);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ContentDetails createFromParcel(Parcel in) {
            return new ContentDetails(in);
        }

        public ContentDetails[] newArray(int size) {
            return new ContentDetails[size];
        }
    };
}
