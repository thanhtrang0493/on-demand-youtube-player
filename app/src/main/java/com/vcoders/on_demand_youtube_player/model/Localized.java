package com.vcoders.on_demand_youtube_player.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class Localized implements Parcelable{
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;

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

    @Override
    public int describeContents() {
        return 0;
    }

    public Localized(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
    }

    public Localized() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Localized createFromParcel(Parcel in) {
            return new Localized(in);
        }

        public Localized[] newArray(int size) {
            return new Localized[size];
        }
    };
}
