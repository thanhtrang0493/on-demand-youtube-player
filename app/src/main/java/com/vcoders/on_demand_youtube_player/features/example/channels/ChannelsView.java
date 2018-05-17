package com.vcoders.on_demand_youtube_player.features.example.channels;


import com.vcoders.on_demand_youtube_player.architecture.BaseView;
import com.vcoders.on_demand_youtube_player.model.Channel;

import java.util.List;

public interface ChannelsView extends BaseView {
    void getPlaylistSuccess(List<Channel> channels);

    void selectChannelItem(int position);
}
