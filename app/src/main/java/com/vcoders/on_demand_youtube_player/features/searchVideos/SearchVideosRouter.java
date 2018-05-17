package com.vcoders.on_demand_youtube_player.features.searchVideos;

import android.content.Context;
import android.os.Bundle;

import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.features.listVideo.ListVideoFragment;
import com.vcoders.on_demand_youtube_player.features.player.PlayerFragment;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;
import com.vcoders.on_demand_youtube_player.utils.Constant;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;


public class SearchVideosRouter extends BaseRouter {

    @Inject
    public SearchVideosRouter(Context context) {
        super(context);
    }

    public void toListVideoFragment(List<Video> listVideo, String searchName) {
        if ((BaseActivity) context != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.VIDEOS, (Serializable) listVideo);
            bundle.putString(Constant.SEARCH_NAME, searchName);
            ((BaseActivity) context).changeFragment(new ListVideoFragment(), bundle);
        }
    }
}
