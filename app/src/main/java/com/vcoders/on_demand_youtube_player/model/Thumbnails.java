package com.vcoders.on_demand_youtube_player.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class Thumbnails implements Parcelable{
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

    @Override
    public int describeContents() {
        return 0;
    }

    public Thumbnails(Parcel in) {
        this._default = in.readParcelable(Thumbnails.class.getClassLoader());
        this.medium = in.readParcelable(Localized.class.getClassLoader());
        this.high = in.readParcelable(ResourceId.class.getClassLoader());
        this.standard = in.readParcelable(ResourceId.class.getClassLoader());
        this.maxres = in.readParcelable(ResourceId.class.getClassLoader());
    }

    public Thumbnails() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this._default, flags);
        dest.writeParcelable(this.medium, flags);
        dest.writeParcelable(this.high, flags);
        dest.writeParcelable(this.standard, flags);
        dest.writeParcelable(this.maxres, flags);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Thumbnails createFromParcel(Parcel in) {
            return new Thumbnails(in);
        }

        public Thumbnails[] newArray(int size) {
            return new Thumbnails[size];
        }
    };
}
