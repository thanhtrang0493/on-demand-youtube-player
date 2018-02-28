package com.vcoders.on_demand_youtube_player.architecture;


public interface YoutubePlayerListener<Type> {
    void onResponse(YoutubePlayerResponse<Type> response);
}
