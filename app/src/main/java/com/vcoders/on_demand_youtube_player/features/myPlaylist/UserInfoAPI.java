package com.vcoders.on_demand_youtube_player.features.myPlaylist;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserInfoAPI {

    @GET("./")
    public Call<UserInfoResult> getUserInfo();
}

