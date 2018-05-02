package com.vcoders.on_demand_youtube_player.features.listVideo;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.architecture.RequestAPIResponse;
import com.vcoders.on_demand_youtube_player.interactor.GetVideoFromPlaylist;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;

import java.util.List;

import javax.inject.Inject;


public class ListVideoPresenter extends BasePresenter<ListVideoView, ListVideoRouter> {

    Context context;

    @Inject
    public ListVideoPresenter(Context context) {
        this.context = context;
    }

    public void playVideo(List<VideoYoutube> videoYoutubes, int position) {
        getRouter().toPlayer(videoYoutubes, position);
    }

    public void getVideoByPlaylistId(String playlistId) {
        new GetVideoFromPlaylist().getVideoFromPlaylist(context, playlistId)
                .onResponse(new RequestAPIListener<List<VideoYoutube>>() {
                    @Override
                    public void onResponse(RequestAPIResponse<List<VideoYoutube>> response) {
                        if (response.getErrorMessage() == null) {
                            getView().getVideoByPlaylistSuccess(response.getData());
                        } else
                            getView().showError(response.getErrorMessage());
                    }
                });
    }
}
