package com.vcoders.on_demand_youtube_player.features.myPlaylist;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.model.PlayList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MyPlaylistPresenter extends BasePresenter<MyPlaylistView, MyPlaylistRouter> {

    Context context;

    @Inject
    public MyPlaylistPresenter(Context context) {
        this.context = context;
    }

    public List<PlayList> getListPlayListRecently(){
        List<PlayList> playListRecentlies=new ArrayList<>();
        playListRecentlies.add(new PlayList());
        playListRecentlies.add(new PlayList());
        playListRecentlies.add(new PlayList());
        playListRecentlies.add(new PlayList());
        return playListRecentlies;
    }

    public List<PlayList> getListPlayList(){
        List<PlayList> playLists=new ArrayList<>();
        playLists.add(new PlayList());
        playLists.add(new PlayList());
        playLists.add(new PlayList());
        playLists.add(new PlayList());
        playLists.add(new PlayList());
        playLists.add(new PlayList());
        playLists.add(new PlayList());
        playLists.add(new PlayList());
        return playLists;
    }
}
