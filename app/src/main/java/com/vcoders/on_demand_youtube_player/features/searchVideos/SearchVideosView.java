package com.vcoders.on_demand_youtube_player.features.searchVideos;


import com.vcoders.on_demand_youtube_player.architecture.BaseView;
import com.vcoders.on_demand_youtube_player.interfaces.ISelectSuggestName;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;

import java.util.List;

public interface SearchVideosView extends BaseView, ISelectSuggestName {
    void searchVideoSuccess(List<VideoYoutube> videoList, String search);
    void searchSuggestNameSuccess(List<String> nameList);
}
