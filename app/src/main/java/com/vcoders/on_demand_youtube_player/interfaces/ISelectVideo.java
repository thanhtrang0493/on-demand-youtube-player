package com.vcoders.on_demand_youtube_player.interfaces;


import android.widget.LinearLayout;

public interface ISelectVideo {
    void selectedItemVideo(int position);

    void selectedMoreItem(int position, LinearLayout layout);
}
