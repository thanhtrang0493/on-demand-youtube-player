package com.vcoders.on_demand_youtube_player.features.playlist;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vcoders.on_demand_youtube_player.adapter.VideoYoutubeAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;
import com.vcoders.on_demand_youtube_player.interactor.GetVideoFromPlaylist2;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;

import java.util.List;

public class PlaylistPresenter extends BasePresenter<PlaylistView, PlaylistRouter> {

    private Context context;

    public PlaylistPresenter(Context context) {
        this.context = context;
    }

    public void getVideoYoutubeFromPlaylistId(String playlistId) {
        getView().showLoading(true);
        GetVideoFromPlaylist2.getInstance().getVideoFromPlaylist(context, playlistId)
                .onResponse(new RequestAPIListener<List<VideoYoutube>>() {
                    @Override
                    public void onResponse(ResponseAPIListener<List<VideoYoutube>> response) {
                        if (response.getErrorMessage() == null) {
                            getView().getVideoYoutubeSuccess(response.getData());
                            getView().showLoading(false);
                        } else {
                            getView().showError(response.getErrorMessage());
                        }
                    }
                });
    }

    public VideoYoutubeAdapter initVideoYoutubeAdapter(RecyclerView rvVideoYoutube,
                                                       List<VideoYoutube> videoYoutubes, PlaylistView playlistView) {
        VideoYoutubeAdapter adapter = new VideoYoutubeAdapter(context, videoYoutubes, playlistView);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvVideoYoutube.setLayoutManager(manager);
        rvVideoYoutube.setAdapter(adapter);
        return adapter;
    }

    public void playVideoYoutube(int position, List<VideoYoutube> videoYoutubes) {
        getRouter().toPlayVideo(context, videoYoutubes.get(position).getId());
    }


}
