package com.vcoders.on_demand_youtube_player.features.playVideo;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.architecture.BaseYoutubeActivity;
import com.vcoders.on_demand_youtube_player.customView.YoutubePlayerViewCustom;
import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import butterknife.BindView;

public class PlayVideoActivity extends BaseYoutubeActivity implements PlayVideoView {

    private String videoYoutubeId;
    private PlayVideoPresenter playVideoPresenter = new PlayVideoPresenter(this);
    private PlayVideoRouter playVideoRouter = new PlayVideoRouter(this);

    @BindView(R.id.mYoutubePlayerView)
    YoutubePlayerViewCustom youtubePlayerView;

    @Override
    protected void initializeView() {
        getBundle();

        youtubePlayerView.initYoutubePlayerView(this, videoYoutubeId, this);
    }

    private void getBundle() {
//        if (this.getIntent().getExtras() != null) {
//            videoYoutubeId = this.getIntent().getExtras().getString(Constant.VIDEO_YOUTUBE_ID);
//        }

        String youtubeVideoUrl = "https://www.youtube.com/watch?v=4vNZq5U1PmY";
        String youtubePlaylistUrl = "https://www.youtube.com/playlist?list=PLBNfDJ7LGzb4hC7s8txFKxu_IqRphztKj";
        videoYoutubeId = playVideoPresenter.getYouTubeVideoId(youtubeVideoUrl);
        String playlistId = playVideoPresenter.getYouTubePlaylistId(youtubePlaylistUrl);
        String youtubeChannelUrl = "https://www.youtube.com/channel/UC-9-kyTW8ZkZNDHQJ6FgpwQ";
        String channelId = playVideoPresenter.getYouTubeChannelId(youtubeChannelUrl);
    }

    @Override
    protected int setViewResource() {
        return R.layout.activity_play_video;
    }

    @Override
    protected void onInitYoutubePlayerSuccess(YouTubePlayer youTubePlayer, Boolean wasRestored) {
        youtubePlayerView.initYoutubePlayer(youTubePlayer, wasRestored);
    }

    @Override
    protected BasePresenter setPresenter() {
        return playVideoPresenter;
    }

    @Override
    protected BaseRouter setRouter() {
        return playVideoRouter;
    }

    @Override
    public void showError(String error) {
        Utils.getInstance().showError(this, error);
    }

    @Override
    public void showLoading(boolean isShow) {
        Utils.getInstance().showLoading(this, isShow);
    }
}
