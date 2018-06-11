package com.vcoders.on_demand_youtube_player.features.myPlaylist;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.interactor.GetMyPlaylist;
import com.vcoders.on_demand_youtube_player.interactor.GetVideoFromPlaylist;
import com.vcoders.on_demand_youtube_player.model.Data;
import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.utils.AccountUtils;
import com.vcoders.on_demand_youtube_player.youtubeApi.base.RequestAPIListener;
import com.vcoders.on_demand_youtube_player.youtubeApi.response.ResponseAPIListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MyPlaylistPresenter extends BasePresenter<MyPlaylistView, MyPlaylistRouter> {

    Context context;

    @Inject
    public MyPlaylistPresenter(Context context) {
        this.context = context;
    }

    public List<PlayList> getListPlayListRecently() {
        List<PlayList> playListRecentlies = new ArrayList<>();
        playListRecentlies.add(new PlayList());
        playListRecentlies.add(new PlayList());
        playListRecentlies.add(new PlayList());
        playListRecentlies.add(new PlayList());
        return playListRecentlies;
    }

    public List<Video> getListPlayList() {
        List<Video> playLists = new ArrayList<>();
        playLists.add(new Video());
        playLists.add(new Video());
        playLists.add(new Video());
        playLists.add(new Video());
        playLists.add(new Video());
        playLists.add(new Video());
        playLists.add(new Video());
        playLists.add(new Video());
        return playLists;
    }

    public void getMyPlaylist() {
        if (AccountUtils.getInstance().isLogin()) {
            getView().showLoading(true);
            new GetMyPlaylist(context).execute()
                    .onListener(new RequestAPIListener<Data<PlayList>>() {
                        @Override
                        public void onResponse(ResponseAPIListener<Data<PlayList>> response) {
                            if (response.getErrorMessage() == null) {
                                getView().onGetMyPlaylistSuccess(response.getData().getItems());
                            } else {
                                getView().showError(response.getErrorMessage());
                            }

                            getView().showLoading(false);
                        }
                    });
        }
    }

    public void getVideoByPlaylistId(String playlistId) {
        getView().showLoading(true);
        new GetVideoFromPlaylist(context).execute(playlistId)
                .onListener(new RequestAPIListener<Data<Video>>() {
                    @Override
                    public void onResponse(ResponseAPIListener<Data<Video>> response) {
                        if (response.getErrorMessage() == null) {
                            getView().onGetListVideoSuccess(response.getData().getItems());
                        } else {
                            getView().showError(response.getErrorMessage());
                        }

                        getView().showLoading(false);
                    }
                });
    }
}
