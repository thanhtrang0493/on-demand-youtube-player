package com.vcoders.on_demand_youtube_player.features.listVideo;

import com.vcoders.on_demand_youtube_player.architecture.BaseView;
import com.vcoders.on_demand_youtube_player.interfaces.IAddNewPlaylist;
import com.vcoders.on_demand_youtube_player.interfaces.ISelectVideo;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;

import java.util.List;


public interface ListVideoView extends BaseView, ISelectVideo, IAddNewPlaylist {
    void getVideoByPlaylistSuccess(List<Video> videoYoutubeList);
}
