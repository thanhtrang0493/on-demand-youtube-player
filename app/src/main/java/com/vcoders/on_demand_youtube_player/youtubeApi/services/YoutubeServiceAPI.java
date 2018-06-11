package com.vcoders.on_demand_youtube_player.youtubeApi.services;

import com.google.gson.JsonObject;
import com.vcoders.on_demand_youtube_player.model.Channel;
import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.model.User;
import com.vcoders.on_demand_youtube_player.model.Video;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface YoutubeServiceAPI {
    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("playlistItems")
    Observable<Response<Data<Video>>> getVideoFromPlaylistId(@QueryMap Map<String, Object> body);

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("playlists")
    Observable<Response<Data<Video>>> getPlaylistFromChannelId(@QueryMap Map<String, Object> body);

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("search")
    Observable<Data<Channel>> searchChannelByName(@QueryMap Map<String, String> options, @Body Map<String, Object> body);

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("search")
    Observable<Response<Data<Video>>> searchVideoByName(@QueryMap Map<String, Object> body);

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("playlists")
    Observable<Response<Data<PlayList>>> getMyPlaylist(@QueryMap Map<String, Object> body);

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("userinfo")
    Observable<Response<User>> getUserInfo();

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("playlists")
    Observable<Response<Data<String>>> createNewPlaylist(@QueryMap Map<String, Object> query, @Body JsonObject object);
}
