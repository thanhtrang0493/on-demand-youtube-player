package com.vcoders.on_demand_youtube_player.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thumbnails {
    @SerializedName("default")
    @Expose
    private ThumbnailsStandard _default;
    @SerializedName("medium")
    @Expose
    private ThumbnailsStandard medium;
    @SerializedName("high")
    @Expose
    private ThumbnailsStandard high;
    @SerializedName("standard")
    @Expose
    private ThumbnailsStandard standard;
    @SerializedName("maxres")
    @Expose
    private Maxres maxres;

    public ThumbnailsStandard getDefault() {
        return _default;
    }

    public void setDefault(ThumbnailsStandard _default) {
        this._default = _default;
    }

    public ThumbnailsStandard getMedium() {
        return medium;
    }

    public void setMedium(ThumbnailsStandard medium) {
        this.medium = medium;
    }

    public ThumbnailsStandard getHigh() {
        return high;
    }

    public void setHigh(ThumbnailsStandard high) {
        this.high = high;
    }

    public ThumbnailsStandard getStandard() {
        return standard;
    }

    public void setStandard(ThumbnailsStandard standard) {
        this.standard = standard;
    }

    public Maxres getMaxres() {
        return maxres;
    }

    public void setMaxres(Maxres maxres) {
        this.maxres = maxres;
    }
}
