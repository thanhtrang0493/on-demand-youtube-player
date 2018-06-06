package com.vcoders.on_demand_youtube_player.interactor;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;



import io.reactivex.Observable;
import retrofit2.Response;

public class GetVideoFromPlaylist extends InteractorYoutube<Data<Video>> {

    Context context;

    public GetVideoFromPlaylist(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected Observable<Response<Data<Video>>> buildObservable() {
        return getYoutubeService().getVideoFromPlaylistId( body);
    }

    public GetVideoFromPlaylist execute(String playlistId) {
        body.put("key", Constant.API_KEY);
        body.put("maxResults", 50);
        body.put("part", "snippet,contentDetails");
        body.put("playlistId", playlistId);

        run();
        return this;
    }

    public GetVideoFromPlaylist onListener(RequestAPIListener<Data<Video>> listener) {
        setRequestAPIListener(listener);
        return this;
    }
}
