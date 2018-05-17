package com.vcoders.on_demand_youtube_player.youtubeApi.base;


import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;

public interface RequestAPIListener<Type> {
    void onResponse(ResponseAPIListener<Type> response);
}
