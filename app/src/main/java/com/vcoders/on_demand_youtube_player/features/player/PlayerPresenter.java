package com.vcoders.on_demand_youtube_player.features.player;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;

import javax.inject.Inject;


public class PlayerPresenter extends BasePresenter<PlayerView, PlayerRouter> {

    Context context;

    @Inject
    public PlayerPresenter(Context context) {
        this.context = context;
    }
}
