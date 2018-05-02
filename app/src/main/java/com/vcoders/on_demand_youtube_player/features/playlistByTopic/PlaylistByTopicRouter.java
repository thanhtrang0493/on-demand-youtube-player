package com.vcoders.on_demand_youtube_player.features.playlistByTopic;

import android.content.Context;
import android.os.Bundle;

import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.features.listVideo.ListVideoFragment;
import com.vcoders.on_demand_youtube_player.utils.Constant;

import javax.inject.Inject;



public class PlaylistByTopicRouter extends BaseRouter {

    @Inject
    public PlaylistByTopicRouter(Context context) {
        super(context);
    }

    public void toListVideo(String playlistId){
        if ((BaseActivity) context != null) {
            Bundle bundle=new Bundle();
            bundle.putString(Constant.PLAYLIST_ID, playlistId);
            ((BaseActivity) context).changeFragment(new ListVideoFragment(), bundle);
        }
    }
}
