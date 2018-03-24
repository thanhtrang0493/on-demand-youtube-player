package com.vcoders.on_demand_youtube_player.features.searchVideos;

import android.content.Context;
import android.os.Bundle;

import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.features.home.HomeActivity;
import com.vcoders.on_demand_youtube_player.features.listVideo.ListVideoFragment;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;
import com.vcoders.on_demand_youtube_player.utils.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class SearchVideosPresenter extends BasePresenter<SearchVideosView, SearchVideosRouter> {

    Context context;

    @Inject
    public SearchVideosPresenter(Context context) {
        this.context = context;
    }

    public List<VideoYoutube> searchVideosByName(String name) {
        List<VideoYoutube> listVideo = new ArrayList<>();
        listVideo.add(new VideoYoutube());
        listVideo.add(new VideoYoutube());
        listVideo.add(new VideoYoutube());
        listVideo.add(new VideoYoutube());
        listVideo.add(new VideoYoutube());
        return listVideo;
    }

    public void toDisplayListVideo(List<VideoYoutube> listVideo) {
        if ((BaseActivity) context != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.VIDEOS, (Serializable) listVideo);
            ((BaseActivity) context).changeFragment(new ListVideoFragment(), bundle);
        }
    }
}
