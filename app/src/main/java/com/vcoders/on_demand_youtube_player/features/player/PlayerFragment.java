package com.vcoders.on_demand_youtube_player.features.player;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.google.android.youtube.player.YouTubePlayer;
import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.ListVideoAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BaseFragment;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.customView.YoutubePlayerCustom;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.home.HomeComponent;
import com.vcoders.on_demand_youtube_player.model.VideoYoutube;
import com.vcoders.on_demand_youtube_player.utils.Constant;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class PlayerFragment extends BaseFragment<HomeComponent> implements PlayerView {

    @Inject
    PlayerPresenter playerPresenter;

    @Inject
    PlayerRouter playerRouter;

    ListVideoAdapter adapter;
    List<VideoYoutube> listVideo;
    int positionVideoSelected;
    YouTubePlayer youTubePlayer;

    @BindView(R.id.rvListVideo)
    RecyclerView rvListVideo;

    @BindView(R.id.youtubePlayerView)
    YoutubePlayerCustom youtubePlayerView;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        getBundle();
        initListVideoAdapter();

        youtubePlayerView.initYoutubePlayer(this, this);
    }

    public String getYouTubeVideoId(String videoUrl) {

        if (videoUrl != null && videoUrl.length() > 0) {

            Uri videoUri = Uri.parse(videoUrl);
            String video_id = videoUri.getQueryParameter("v");

            return video_id;
        }
        return "";
    }

    private void getBundle() {
        listVideo = (List<VideoYoutube>) getArguments().getSerializable(Constant.VIDEOS);
        positionVideoSelected = getArguments().getInt(Constant.POSITION);
    }

    private void initListVideoAdapter() {
        adapter = new ListVideoAdapter(getActivity(), listVideo, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvListVideo.setLayoutManager(manager);
        rvListVideo.setAdapter(adapter);
    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_player;
    }

    @Override
    protected String getTitleActionBar() {
        return getResources().getString(R.string.favorite_songs);
    }

    @Override
    protected TypeActionBar[] getTypeActionBar() {
        return new TypeActionBar[]{TypeActionBar.BACK, TypeActionBar.SEARCH};
    }

    @Override
    protected BasePresenter getPresenter() {
        return playerPresenter;
    }

    @Override
    protected BaseRouter getRouter() {
        return playerRouter;
    }

    @Override
    protected void inject() {
        ((HomeComponent) getAcitivyComponent()).inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void selectPlayVideo(int position) {
        youtubePlayerView.playVideo(youTubePlayer, listVideo.get(position).getId());
    }

    @Override
    public void selectedMoreItem(int position, LinearLayout layout) {

    }

    @Override
    public void onInitYoutubePlayerSuccess(YouTubePlayer youTubePlayer) {
        this.youTubePlayer = youTubePlayer;
        if (listVideo != null && listVideo.size() > 0)
            youtubePlayerView.playVideo(youTubePlayer, listVideo.get(0).getId());
    }

    @Override
    public void onInitYoutubePlayerFailure(String errorMessage) {
        showError(errorMessage);
    }
}
