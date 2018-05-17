package com.vcoders.on_demand_youtube_player.interactor;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;


import java.util.List;

import io.reactivex.Observable;

public class GetVideoFromPlaylist extends InteractorYoutube<Data<List<Video>>> {

    Context context;

    public GetVideoFromPlaylist(Context context) {
        super(context);
        this.context = context;
        setHeader(false);
    }

    @Override
    protected Observable buildObservable() {
        return getYoutubeService().getVideoFromPlaylistId( body);
    }

    public GetVideoFromPlaylist execute(String playlistId) {
        body.put("part", "snippet,contentDetails");
        body.put("playlistId", playlistId);

        run();
        return this;
    }

    public GetVideoFromPlaylist onListener(RequestAPIListener<Data<List<Video>>> listener) {
        setRequestAPIListener(listener);
        return this;
    }
}
