package com.vcoders.on_demand_youtube_player.features.playlistDetail;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;

import javax.inject.Inject;


public class PlaylistDetailPresenter extends BasePresenter<PlaylistDetailView, PlaylistDetailRouter> {

    Context context;

    @Inject
    public PlaylistDetailPresenter(Context context) {
        this.context = context;
    }
}
