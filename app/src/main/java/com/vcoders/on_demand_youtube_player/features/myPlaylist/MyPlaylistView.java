package com.vcoders.on_demand_youtube_player.features.myPlaylist;

import com.vcoders.on_demand_youtube_player.architecture.BaseView;
import com.vcoders.on_demand_youtube_player.interfaces.ISelectItem;
import com.vcoders.on_demand_youtube_player.interfaces.ISelectPlaylist;
import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.model.Video;

import java.util.List;


public interface MyPlaylistView extends BaseView, ISelectPlaylist, ISelectItem {
    void onGetMyPlaylistSuccess(List<PlayList> playLists);

    void onGetListVideoSuccess(List<Video> videoList);
}
