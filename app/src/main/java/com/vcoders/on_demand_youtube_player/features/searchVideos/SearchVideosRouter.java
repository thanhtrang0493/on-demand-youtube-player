package com.vcoders.on_demand_youtube_player.features.searchVideos;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;

import javax.inject.Inject;



public class SearchVideosRouter extends BaseRouter {

    @Inject
    public SearchVideosRouter(Context context) {
        super(context);
    }
}
