package com.vcoders.on_demand_youtube_player.interfaces;


import android.widget.ImageView;

public interface ISelectVideo {
    void selectedItemVideo(int position);

    void selectedMoreItem(int position, ImageView imageView);
}
