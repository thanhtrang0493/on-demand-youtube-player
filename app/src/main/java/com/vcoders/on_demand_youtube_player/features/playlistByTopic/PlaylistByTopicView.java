package com.vcoders.on_demand_youtube_player.features.playlistByTopic;

import com.vcoders.on_demand_youtube_player.architecture.BaseView;
import com.vcoders.on_demand_youtube_player.database.DatabaseResponseListener;
import com.vcoders.on_demand_youtube_player.interfaces.IChangeTopic;
import com.vcoders.on_demand_youtube_player.interfaces.ISelectPlaylist;
import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.model.Topic;
import com.vcoders.on_demand_youtube_player.model.Video;

import java.util.List;


public interface PlaylistByTopicView extends BaseView, IChangeTopic, ISelectPlaylist, DatabaseResponseListener<List<Topic>> {
    void getPlaylistSuccessed(List<Video> playLists);
}
