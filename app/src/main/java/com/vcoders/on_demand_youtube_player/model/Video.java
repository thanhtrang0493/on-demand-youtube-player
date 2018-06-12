package com.vcoders.on_demand_youtube_player.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class Video implements Parcelable {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("snippet")
    @Expose
    private Snippet snippet;
    @SerializedName("contentDetails")
    @Expose
    private ContentDetails contentDetails;
    private boolean isLiveStream;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    public ContentDetails getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(ContentDetails contentDetails) {
        this.contentDetails = contentDetails;
    }

    public boolean isLiveStream() {
        return isLiveStream;
    }

    public void setLiveStream(boolean liveStream) {
        isLiveStream = liveStream;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Video(Parcel in) {
        this.kind = in.readString();
        this.etag = in.readString();
        this.id = in.readString();
        this.snippet = in.readParcelable(Snippet.class.getClassLoader());
        this.contentDetails = in.readParcelable(ContentDetails.class.getClassLoader());
        this.isLiveStream = in.readByte() != 0 ? true : false;
    }

    public Video() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kind);
        dest.writeString(this.etag);
        dest.writeString(this.id);
        dest.writeParcelable(this.snippet, flags);
        dest.writeParcelable(this.contentDetails, flags);
        dest.writeByte((byte) (this.isLiveStream ? 1 : 0));
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
