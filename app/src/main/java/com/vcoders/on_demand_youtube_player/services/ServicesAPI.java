package com.vcoders.on_demand_youtube_player.services;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public interface ServicesAPI {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("/complete/search")
    Observable<List<Object>> urlSearchSuggestName(@QueryMap Map<String, String> options);

}
