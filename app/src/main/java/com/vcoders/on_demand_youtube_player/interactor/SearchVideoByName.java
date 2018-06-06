package com.vcoders.on_demand_youtube_player.interactor;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.utils.Utils;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.InteractorYoutube;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;

import io.reactivex.Observable;
import retrofit2.Response;

public class SearchVideoByName extends InteractorYoutube<Data<Video>> {
    public SearchVideoByName(Context context) {
        super(context);
    }

    @Override
    protected Observable<Response<Data<Video>>> buildObservable() {
        return getYoutubeService().searchVideoByName(body);
    }

    public SearchVideoByName execute(String name) {
        body.put("key", Constant.API_KEY);
        body.put("maxResults", 50);
        body.put("part", "snippet");
        body.put("q", Utils.getInstance().includeSpaceIntoString(name));
        body.put("type", "video");

        run();

        return this;
    }

    public SearchVideoByName onListener(RequestAPIListener<Data<Video>> listener) {
        setRequestAPIListener(listener);

        return this;
    }
}
