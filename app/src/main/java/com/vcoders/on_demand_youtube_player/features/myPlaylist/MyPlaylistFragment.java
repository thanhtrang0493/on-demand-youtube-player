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
import com.vcoders.on_demand_youtube_player.features.login.LoginActivity;
import com.vcoders.on_demand_youtube_player.features.playlistDetail.PlaylistDetailFragment;
import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.utils.Utils;

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
    List<PlayList> playListRecentlies;
    List<Video> playLists;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        playListRecentlies = myPlaylistPresenter.getListPlayListRecently();
        playLists = myPlaylistPresenter.getListPlayList();

//        initRecentlyPlayedAdapter();
//        initPlayListAdapter();
        Utils.getInstance().changeActivity(getActivity(), LoginActivity.class);
    }

    private void initRecentlyPlayedAdapter() {
        recentlyPlayedAdapter = new RecentlyPlayedAdapter(getActivity(), playListRecentlies);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvRecentlyPlayed.setLayoutManager(manager);
        rvRecentlyPlayed.setAdapter(recentlyPlayedAdapter);
    }

    private void initPlayListAdapter() {
        listPlayListAdapter = new ListPlayListAdapter(getActivity(), playLists, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvPlaylist.setLayoutManager(manager);
        rvPlaylist.setAdapter(listPlayListAdapter);
        rvPlaylist.setPadding(0, 0, 0, 0);
    }

    private void initGridPlayListAdapter() {
        gridPlayListAdapter = new GridPlayListAdapter(getActivity(), playLists, this);
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

    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void onSelectPlayList(int position) {
        ((HomeActivity) getActivity()).changeFragment(new PlaylistDetailFragment(), null);
    }

    @Override
    public void onMorePlayList(int position) {

    }
}
