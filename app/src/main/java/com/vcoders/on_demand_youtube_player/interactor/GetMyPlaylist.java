package com.vcoders.on_demand_youtube_player.interactor;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;

import io.reactivex.Observable;
import retrofit2.Response;

public class GetMyPlaylist extends InteractorYoutube<Data<PlayList>> {
    public GetMyPlaylist(Context context) {
        super(context);
    }

    @Override
    protected Observable<Response<Data<PlayList>>> buildObservable() {
        return getYoutubeService().getMyPlaylist(body);
    }

    public GetMyPlaylist execute() {
        setHeader(true);

        body.put("part", "snippet,contentDetails");
        body.put("mine", "true");
        body.put("maxResults", "25");

        run();
        return this;
    }

    public GetMyPlaylist onListener(RequestAPIListener<Data<PlayList>> listener) {
        setRequestAPIListener(listener);

        return this;
    }
}
