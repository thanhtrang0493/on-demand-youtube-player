package com.vcoders.on_demand_youtube_player.interactor;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;

import io.reactivex.Observable;
import retrofit2.Response;

public class GetPlaylistFromChannel extends InteractorYoutube<Data<Video>> {

    public GetPlaylistFromChannel(Context context) {
        super(context);
    }

    @Override
    protected Observable<Response<Data<Video>>> buildObservable() {
        return getYoutubeService().getPlaylistFromChannelId(body);
    }

    public GetPlaylistFromChannel execute(String channelId) {
        body.put("part", "snippet,contentDetails");
        body.put("channelId", channelId);

        run();

        return this;
    }

    public GetPlaylistFromChannel onListener(RequestAPIListener<Data<Video>> listener) {
        setRequestAPIListener(listener);

        return this;
    }
}
