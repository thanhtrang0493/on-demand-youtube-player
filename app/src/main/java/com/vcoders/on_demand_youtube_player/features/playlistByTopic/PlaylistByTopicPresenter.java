package com.vcoders.on_demand_youtube_player.features.playlistByTopic;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.model.PlayList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class PlaylistByTopicPresenter extends BasePresenter<PlaylistByTopicView, PlaylistByTopicRouter> {

    Context context;

    @Inject
    public PlaylistByTopicPresenter(Context context){

    }

    public List<PlayList> getListPlayList() {
        List<PlayList> playLists = new ArrayList<>();
        playLists.add(new PlayList());
        playLists.add(new PlayList());
        playLists.add(new PlayList());
        return playLists;
    }
}
