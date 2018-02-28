package com.vcoders.on_demand_youtube_player.architecture;


import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.vcoders.on_demand_youtube_player.services.YoutubeAPI;

import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public abstract class BaseYoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public int REQUEST_VIDEO = 12334;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(setViewResource());
        ButterKnife.bind(this);

        initializeView();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        onInitYoutubePlayerSuccess(youTubePlayer, b);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(BaseYoutubeActivity.this, REQUEST_VIDEO);
        } else {
            Log.d(TAG, "play video error");
        }
    }

    protected abstract void initializeView();

    protected abstract int setViewResource();

    protected abstract void onInitYoutubePlayerSuccess(YouTubePlayer youTubePlayer, Boolean wasRestored);

    protected abstract BasePresenter setPresenter();

    protected abstract BaseRouter setRouter();
}
