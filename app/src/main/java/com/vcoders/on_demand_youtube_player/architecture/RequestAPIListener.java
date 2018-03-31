package com.vcoders.on_demand_youtube_player.architecture;


public interface RequestAPIListener<Type> {
    void onResponse(RequestAPIResponse<Type> response);
}
