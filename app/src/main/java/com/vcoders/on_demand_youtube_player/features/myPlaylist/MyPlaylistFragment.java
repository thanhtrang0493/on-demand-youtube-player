package com.vcoders.on_demand_youtube_player.features.myPlaylist;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.GridPlayListAdapter;
import com.vcoders.on_demand_youtube_player.adapter.ListPlayListAdapter;
import com.vcoders.on_demand_youtube_player.adapter.RecentlyPlayedAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BaseFragment;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.home.HomeActivity;
import com.vcoders.on_demand_youtube_player.features.home.HomeComponent;
import com.vcoders.on_demand_youtube_player.features.playlistDetail.PlaylistDetailFragment;
import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class MyPlaylistFragment extends BaseFragment<HomeComponent> implements MyPlaylistView {

    @Inject
    MyPlaylistPresenter myPlaylistPresenter;

    @Inject
    MyPlaylistRouter myPlaylistRouter;

    @BindView(R.id.rvRecentlyPlayed)
    RecyclerView rvRecentlyPlayed;

    @BindView(R.id.rvPlaylist)
    RecyclerView rvPlaylist;

    RecentlyPlayedAdapter recentlyPlayedAdapter;
    ListPlayListAdapter listPlayListAdapter;
    GridPlayListAdapter gridPlayListAdapter;
    List<PlayList> playLists;
    List<Video> videoList;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        init();
        myPlaylistPresenter.getMyPlaylist();
    }

    private void init() {
        playLists = new ArrayList<>();
        videoList = new ArrayList<>();

        initRecentlyPlayedAdapter();
        initPlayListAdapter();
    }

    private void initRecentlyPlayedAdapter() {
        recentlyPlayedAdapter = new RecentlyPlayedAdapter(getActivity(), playLists, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvRecentlyPlayed.setLayoutManager(manager);
        rvRecentlyPlayed.setAdapter(recentlyPlayedAdapter);
    }

    private void initPlayListAdapter() {
        listPlayListAdapter = new ListPlayListAdapter(getActivity(), videoList, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvPlaylist.setLayoutManager(manager);
        rvPlaylist.setAdapter(listPlayListAdapter);
        rvPlaylist.setPadding(0, 0, 0, 0);
    }

    private void initGridPlayListAdapter() {
        gridPlayListAdapter = new GridPlayListAdapter(getActivity(), videoList, this);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rvPlaylist.setLayoutManager(manager);
        rvPlaylist.setAdapter(gridPlayListAdapter);
        rvPlaylist.setPadding(10, 0, 10, 0);
    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_my_playlist;
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
        return myPlaylistPresenter;
    }

    @Override
    protected BaseRouter getRouter() {
        return myPlaylistRouter;
    }

    @Override
    protected void inject() {
        ((HomeComponent) getAcitivyComponent()).inject(this);
    }

    @OnClick(R.id.imgGrid)
    public void onImgGridClick() {
        initGridPlayListAdapter();
    }

    @OnClick(R.id.imgList)
    public void onImgListClick() {
        initPlayListAdapter();
    }

    @Override
    public void showError(String error) {
        Utils.getInstance().showError(getActivity(), error);
    }

    @Override
    public void showLoading(boolean isShow) {
        Utils.getInstance().showLoading(getActivity(), isShow);
    }

    @Override
    public void onSelectPlayList(int position) {
        ((HomeActivity) getActivity()).changeFragment(new PlaylistDetailFragment(), null);
    }

    @Override
    public void onMorePlayList(int position) {

    }

    @Override
    public void onGetMyPlaylistSuccess(List<PlayList> playLists) {
        this.playLists = playLists;
        recentlyPlayedAdapter.updateAdapter(this.playLists);

        if (this.playLists != null && this.playLists.size() > 0) {
            myPlaylistPresenter.getVideoByPlaylistId(this.playLists.get(0).getId());
        }
    }

    @Override
    public void onGetListVideoSuccess(List<Video> videoList) {
        this.videoList = videoList;
        listPlayListAdapter.updateAdapter(this.videoList);
    }

    @Override
    public void onSelectedItem(int position) {
        myPlaylistPresenter.getVideoByPlaylistId(playLists.get(position).getId());
    }
}
