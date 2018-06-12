package com.vcoders.on_demand_youtube_player.audio.interfaces;

import com.vcoders.on_demand_youtube_player.model.Video;

import java.util.List;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2017-Jun-13
 */

public interface YouTubeVideoUpdateListener
{
    void onYouTubeVideoChanged(Video youTubeVideo);

    void onYouTubeVideoRetrieveError();

    void onCurrentQueueIndexUpdated(int queueIndex);

    void onQueueUpdated(String title, List<Video> newQueue);
}
