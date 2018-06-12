package com.vcoders.on_demand_youtube_player.audio.interfaces;


import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.model.Video;

import java.util.List;

/**
 * Interface which enables passing playlist to the fragments
 * Created by Teocci on 15.3.16..
 */
public interface YouTubePlaylistReceiver
{
    void onPlaylistReceived(List<PlayList> youTubePlaylistList);

    void onPlaylistNotFound(String playlistId, int errorCode);

    void onPlaylistVideoReceived(List<Video> youTubeVideos);
}
