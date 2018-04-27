package com.vcoders.on_demand_youtube_player.features.player;

import com.vcoders.on_demand_youtube_player.architecture.BaseView;
import com.vcoders.on_demand_youtube_player.interfaces.IInitYoutubePlayerListener;
import com.vcoders.on_demand_youtube_player.interfaces.ISelectVideo;


public interface PlayerView extends BaseView, ISelectVideo, IInitYoutubePlayerListener {
}
