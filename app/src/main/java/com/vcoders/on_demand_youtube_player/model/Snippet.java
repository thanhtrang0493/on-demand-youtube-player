package com.vcoders.on_demand_youtube_player.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class Snippet implements Parcelable {
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;
    @SerializedName("channelId")
    @Expose
    private String channelId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("thumbnails")
    @Expose
    private Thumbnails thumbnails;
    @SerializedName("channelTitle")
    @Expose
    private String channelTitle;
    @SerializedName("localized")
    @Expose
    private Localized localized;
    @SerializedName("playlistId")
    @Expose
    private String playlistId;
    @SerializedName("liveBroadcastContent")
    @Expose
    private String liveBroadcastContent;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("resourceId")
    @Expose
    private ResourceId resourceId;

    private String duration;


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public Localized getLocalized() {
        return localized;
    }

    public void setLocalized(Localized localized) {
        this.localized = localized;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public void setResourceId(ResourceId resourceId) {
        this.resourceId = resourceId;
    }

    public String getLiveBroadcastContent() {
        return liveBroadcastContent;
    }

    public void setLiveBroadcastContent(String liveBroadcastContent) {
        this.liveBroadcastContent = liveBroadcastContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Snippet(Parcel in) {
        this.publishedAt = in.readString();
        this.channelId = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.channelTitle = in.readString();
        this.playlistId = in.readString();
        this.liveBroadcastContent = in.readString();
        this.duration = in.readString();
        this.position = in.readInt();
        this.thumbnails = in.readParcelable(Thumbnails.class.getClassLoader());
        this.localized = in.readParcelable(Localized.class.getClassLoader());
        this.resourceId = in.readParcelable(ResourceId.class.getClassLoader());
    }

    public Snippet() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.publishedAt);
        dest.writeString(this.channelId);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.channelTitle);
        dest.writeString(this.playlistId);
        dest.writeString(this.liveBroadcastContent);
        dest.writeString(this.duration);
        dest.writeInt(this.position == null ? 0 : this.position);
        dest.writeParcelable(this.thumbnails, flags);
        dest.writeParcelable(this.localized, flags);
        dest.writeParcelable(this.resourceId, flags);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Snippet createFromParcel(Parcel in) {
            return new Snippet(in);
        }

        public Snippet[] newArray(int size) {
            return new Snippet[size];
        }
    };
}
