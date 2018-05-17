package com.vcoders.on_demand_youtube_player.youtubeApi.services;

import com.vcoders.on_demand_youtube_player.model.Channel;
import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public interface YoutubeServiceAPI {
    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("playlistItems")
    Observable<Response<Data<List<Video>>>> getVideoFromPlaylistId(@QueryMap Map<String, Object> body);

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("playlists")
    Observable<Data<PlayList>> getPlaylistFromChannelId(@QueryMap Map<String, String> options, @Body Map<String, Object> body);

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("search")
    Observable<Data<Channel>> searchChannelByName(@QueryMap Map<String, String> options, @Body Map<String, Object> body);

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("search")
    Observable<Data<VideoYoutube>> searchVideoByName(@QueryMap Map<String, String> options, @Body Map<String, Object> body);
}
