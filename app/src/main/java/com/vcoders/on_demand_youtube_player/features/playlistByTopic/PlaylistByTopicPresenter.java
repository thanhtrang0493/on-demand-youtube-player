package com.vcoders.on_demand_youtube_player.features.playlistByTopic;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.architecture.RequestAPIResponse;
import com.vcoders.on_demand_youtube_player.interactor.GetPlaylistFromChannel;
import com.vcoders.on_demand_youtube_player.model.PlayList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class PlaylistByTopicPresenter extends BasePresenter<PlaylistByTopicView, PlaylistByTopicRouter> {

    Context context;

    @Inject
    public PlaylistByTopicPresenter(Context context) {
        this.context = context;
    }

    public List<PlayList> getListPlayList() {
        List<PlayList> playLists = new ArrayList<>();
        playLists.add(new PlayList());
        playLists.add(new PlayList());
        playLists.add(new PlayList());
        return playLists;
    }

    public void getPlaylistByChannelId(String channelId) {
        getView().showLoading(true);
        GetPlaylistFromChannel.getInstance().getPlaylistFromChannel(context, channelId)
                .onResponse(new RequestAPIListener<List<PlayList>>() {
                    @Override
                    public void onResponse(RequestAPIResponse<List<PlayList>> response) {
                        if (response.getErrorMessage() == null) {
                            getView().getPlaylistSuccessed(response.getData());
                        } else
                            getView().showError(response.getErrorMessage());
                        getView().showLoading(false);
                    }
                });
    }

    public void toListVideo(String playlistId){
        getRouter().toListVideo(playlistId);
    }
}
