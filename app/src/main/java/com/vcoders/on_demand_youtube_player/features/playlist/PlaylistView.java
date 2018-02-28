package com.vcoders.on_demand_youtube_player.features.playlist;


import com.vcoders.on_demand_youtube_player.architecture.BaseView;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;

import java.util.List;

public interface PlaylistView extends BaseView {
    void getVideoYoutubeSuccess(List<VideoYoutube> videoYoutubes);

    void playVideoYoutube(int position);
}
