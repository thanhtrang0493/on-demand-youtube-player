package com.vcoders.on_demand_youtube_player.features.playlistByTopic;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.interactor.GetPlaylistFromChannel;
import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;


import javax.inject.Inject;


public class PlaylistByTopicPresenter extends BasePresenter<PlaylistByTopicView, PlaylistByTopicRouter> {

    Context context;
    Data<Video> dataVideo;

    @Inject
    public PlaylistByTopicPresenter(Context context) {
        this.context = context;
    }

    public void getPlaylistByChannelId(String channelId) {
        getView().showLoading(true);

        new GetPlaylistFromChannel(context).execute(channelId)
                .onListener(new RequestAPIListener<Data<Video>>() {
                    @Override
                    public void onResponse(ResponseAPIListener<Data<Video>> response) {
                        if (response.getErrorMessage() == null) {
                            dataVideo = response.getData();
                            getView().getPlaylistSuccessed(response.getData().getItems());
                        } else
                            getView().showError(response.getErrorMessage());
                        getView().showLoading(false);
                    }
                });
    }

    public void toListVideo(String playlistId) {
        getRouter().toListVideo(playlistId);
    }
}
