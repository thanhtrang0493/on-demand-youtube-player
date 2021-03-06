package com.vcoders.on_demand_youtube_player.features.playlistDetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.ListVideoAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BaseFragment;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.home.HomeActivity;
import com.vcoders.on_demand_youtube_player.features.home.HomeComponent;
import com.vcoders.on_demand_youtube_player.features.player.PlayerFragment;
import com.vcoders.on_demand_youtube_player.model.Video;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class PlaylistDetailFragment extends BaseFragment<HomeComponent> implements PlaylistDetailView {

    @Inject
    PlaylistDetailPresenter playlistDetailPresenter;

    @Inject
    PlaylistDetailRouter playlistDetailRouter;

    @BindView(R.id.rvListVideo)
    RecyclerView rvListVideo;

    ListVideoAdapter adapter;
    List<Video> listVideo;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        getBundle();
        initListVideoAdapter();
    }

    private void getBundle() {
        listVideo = new ArrayList<>();
        listVideo.add(new Video());
        listVideo.add(new Video());
        listVideo.add(new Video());
        listVideo.add(new Video());
        listVideo.add(new Video());
    }

    private void initListVideoAdapter() {
        adapter = new ListVideoAdapter(getActivity(), listVideo, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvListVideo.setLayoutManager(manager);
        rvListVideo.setAdapter(adapter);
    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_play_list_detail;
    }

    @Override
    protected String getTitleActionBar() {
        return null;
    }

    @Override
    protected TypeActionBar[] getTypeActionBar() {
        return new TypeActionBar[0];
    }

    @Override
    protected BasePresenter getPresenter() {
        return playlistDetailPresenter;
    }

    @Override
    protected BaseRouter getRouter() {
        return playlistDetailRouter;
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
        ((HomeActivity) getActivity()).changeFragment(new PlayerFragment(), null);
    }

    @Override
    public void selectedMoreItem(int position, LinearLayout layout) {

    }
}
