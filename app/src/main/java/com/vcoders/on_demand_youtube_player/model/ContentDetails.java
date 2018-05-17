package com.vcoders.on_demand_youtube_player.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentDetails {
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
}
