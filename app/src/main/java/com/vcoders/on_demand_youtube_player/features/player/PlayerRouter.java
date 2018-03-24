package com.vcoders.on_demand_youtube_player.features.player;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;

import javax.inject.Inject;


public class PlayerRouter extends BaseRouter {

    @Inject
    public PlayerRouter(Context context) {
        super(context);
    }
}
