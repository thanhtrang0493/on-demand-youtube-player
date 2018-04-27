package com.vcoders.on_demand_youtube_player.interfaces;

import com.google.android.youtube.player.YouTubePlayer;

public interface IInitYoutubePlayerListener {
    void onInitYoutubePlayerSuccess(YouTubePlayer youTubePlayer);

    void onInitYoutubePlayerFailure(String errorMessage);
}
