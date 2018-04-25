package com.vcoders.on_demand_youtube_player.features.listVideo;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;

import java.util.List;

import javax.inject.Inject;


public class ListVideoPresenter extends BasePresenter<ListVideoView, ListVideoRouter> {

    Context context;

    @Inject
    public ListVideoPresenter(Context context) {

    }

    public void playVideo(List<VideoYoutube> videoYoutubes, int position) {
        getRouter().toPlayer(videoYoutubes, position);
    }
}
