package com.vcoders.on_demand_youtube_player.features.channels;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vcoders.on_demand_youtube_player.adapter.ChannelsAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;
import com.vcoders.on_demand_youtube_player.interactor.GetPlaylistFromChannel;
import com.vcoders.on_demand_youtube_player.model.Channel;
import com.vcoders.on_demand_youtube_player.model.PlayList;

import java.util.List;

public class ChannelsPresenter extends BasePresenter<ChannelsView, ChannelsRouter> {

    private Context context;

    public ChannelsPresenter(Context context) {
        this.context = context;
    }

    public void getPlaylistFromChannelId(String channelId) {
        getView().showLoading(true);
        GetPlaylistFromChannel.getInstance().getPlaylistFromChannel(context, channelId)
                .onResponse(new RequestAPIListener<List<PlayList>>() {
                    @Override
                    public void onResponse(ResponseAPIListener<List<PlayList>> response) {
                        if (response.getErrorMessage() == null) {
//                            getView().getPlaylistSuccess(response.getData());
                            getView().showLoading(false);
                        } else {
                            getView().showError(response.getErrorMessage());
                        }
                    }
                });
    }

    public ChannelsAdapter initChannelAdapter(RecyclerView rvChannels, List<Channel> channels,
                                              ChannelsView channelsView) {
        ChannelsAdapter adapter = new ChannelsAdapter(context, channels, channelsView);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvChannels.setLayoutManager(manager);
        rvChannels.setAdapter(adapter);
        return adapter;
    }
}
