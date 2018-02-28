package com.vcoders.on_demand_youtube_player.features.playlist;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vcoders.on_demand_youtube_player.adapter.VideoYoutubeAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.YoutubePlayerListener;
import com.vcoders.on_demand_youtube_player.architecture.YoutubePlayerResponse;
import com.vcoders.on_demand_youtube_player.interactor.GetVideoFromPlaylist;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaylistPresenter extends BasePresenter<PlaylistView, PlaylistRouter> {

    private Context context;

    public PlaylistPresenter(Context context) {
        this.context = context;
    }

    public void getVideoYoutubeFromPlaylistId(String playlistId) {
        getView().showLoading(true);
        GetVideoFromPlaylist.getInstance().getVideoFromPlaylist(context, playlistId)
                .onResponse(new YoutubePlayerListener<List<VideoYoutube>>() {
                    @Override
                    public void onResponse(YoutubePlayerResponse<List<VideoYoutube>> response) {
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
        getRouter().toPlayVideo(context, videoYoutubes.get(position).getVideoId());
    }


}
