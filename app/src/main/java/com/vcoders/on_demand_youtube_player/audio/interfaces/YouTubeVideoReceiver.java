package com.vcoders.on_demand_youtube_player.audio.interfaces;


import com.vcoders.on_demand_youtube_player.model.Video;

import java.util.List;

/**
 * Interface which enables passing videos to the fragments
 * Created by Teocci on 10.3.16..
 */
public interface YouTubeVideoReceiver
{
    void onVideosReceived(List<Video> youTubeVideos, String currentPageToken, String nextPageToken);
}