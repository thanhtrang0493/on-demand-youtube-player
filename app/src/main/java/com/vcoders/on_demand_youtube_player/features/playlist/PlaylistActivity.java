package com.vcoders.on_demand_youtube_player.features.playlist;

import android.support.v7.widget.RecyclerView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.VideoYoutubeAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;
import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PlaylistActivity extends BaseActivity implements PlaylistView {

    private PlaylistPresenter playlistPresenter = new PlaylistPresenter(PlaylistActivity.this);
    private VideoYoutubeAdapter adapter;
    private List<VideoYoutube> videoYoutubes = new ArrayList<>();
    private PlaylistRouter playlistRouter = new PlaylistRouter(this);
    private String playlistId;

    @BindView(R.id.rvVideoYoutube)
    RecyclerView rvVideoYoutube;

    @Override
    protected void initializeView() {
        getBundle();

        //init video youtube recycler view
        adapter = playlistPresenter.initVideoYoutubeAdapter(rvVideoYoutube, videoYoutubes, this);

        //get list video from playlist id
        playlistPresenter.getVideoYoutubeFromPlaylistId(playlistId);
    }

    private void getBundle() {
        if (getIntent().getExtras() != null) {
            playlistId = getIntent().getExtras().getString(Constant.PLAYLIST_ID);
        }
    }

    @Override
    protected int setViewResource() {
        return R.layout.activity_playlist;
    }

    @Override
    protected BasePresenter getPresenter() {
        return playlistPresenter;
    }

    @Override
    protected BaseRouter getRouter() {
        return playlistRouter;
    }

    @Override
    public void showError(String error) {
        Utils.getInstance().showError(this, error);
    }

    @Override
    public void showLoading(boolean isShow) {
        Utils.getInstance().showLoading(this, isShow);
    }

    @Override
    public void getVideoYoutubeSuccess(List<VideoYoutube> videoYoutubes) {
        if (videoYoutubes != null) {
            this.videoYoutubes = videoYoutubes;
            adapter.updateAdapter(videoYoutubes);
        }
    }

    @Override
    public void playVideoYoutube(int position) {
        playlistPresenter.playVideoYoutube(position, videoYoutubes);
    }
}
