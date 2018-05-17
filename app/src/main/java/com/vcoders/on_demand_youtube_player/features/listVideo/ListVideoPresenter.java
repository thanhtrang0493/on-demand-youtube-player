package com.vcoders.on_demand_youtube_player.features.listVideo;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.interactor.GetVideoFromPlaylist;
import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;

import java.util.List;

import javax.inject.Inject;


public class ListVideoPresenter extends BasePresenter<ListVideoView, ListVideoRouter> {

    Context context;
    Data<Video> dataVideo;

    @Inject
    public ListVideoPresenter(Context context) {
        this.context = context;
    }

    public void playVideo(List<Video> videoYoutubes, int position) {
        getRouter().toPlayer(videoYoutubes, position);
    }

    public void getVideoByPlaylistId(String playlistId) {
        getView().showLoading(true);

        new GetVideoFromPlaylist(context).execute(playlistId)
                .onListener(new RequestAPIListener<Data<Video>>() {
                    @Override
                    public void onResponse(ResponseAPIListener<Data<Video>> response) {
                        if (response.getErrorMessage() == null) {
                            dataVideo = response.getData();
                            getView().getVideoByPlaylistSuccess(response.getData().getItems());
                        } else
                            getView().showError(response.getErrorMessage());

                        getView().showLoading(false);
                    }
                });
    }

    public void toLogin() {
        getRouter().toLogin();
    }
}
