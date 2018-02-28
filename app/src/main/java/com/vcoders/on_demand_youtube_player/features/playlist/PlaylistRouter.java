package com.vcoders.on_demand_youtube_player.features.playlist;


import android.content.Context;
import android.os.Bundle;

import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.features.playVideo.PlayVideoActivity;
import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.utils.Utils;

public class PlaylistRouter extends BaseRouter {

    private Context context;

    public PlaylistRouter(Context context) {
        this.context = context;
    }

    public void toPlayVideo(Context context, String videoYoutubeId) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.VIDEO_YOUTUBE_ID, videoYoutubeId);
        Utils.getInstance().changeActivity(context, PlayVideoActivity.class, bundle);
    }
}
