package com.vcoders.on_demand_youtube_player.features.playlistByTopic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.ListPlayListAdapter;
import com.vcoders.on_demand_youtube_player.adapter.ListTopicAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BaseFragment;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.home.HomeActivity;
import com.vcoders.on_demand_youtube_player.features.home.HomeComponent;
import com.vcoders.on_demand_youtube_player.features.playlistDetail.PlaylistDetailFragment;
import com.vcoders.on_demand_youtube_player.model.PlayList;
import com.vcoders.on_demand_youtube_player.model.Topic;
import com.vcoders.on_demand_youtube_player.utils.Constant;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class PlaylistByTopicFragment extends BaseFragment<HomeComponent> implements PlaylistByTopicView {

    List<Topic> topics;
    ListTopicAdapter listTopicAdapter;
    List<PlayList> playLists;
    ListPlayListAdapter playListAdapter;

    @Inject
    PlaylistByTopicPresenter playlistByTopicPresenter;

    @BindView(R.id.rvTopics)
    RecyclerView rvTopics;

    @BindView(R.id.rvPlayList)
    RecyclerView rvPlayList;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        getBundle();
        playLists = playlistByTopicPresenter.getListPlayList();
        initListTopicAdapter();
        initPlayListAdapter();
    }

    private void getBundle() {
        topics = (List<Topic>) getArguments().getSerializable(Constant.TOPICS);
    }


    private void initPlayListAdapter() {
        playListAdapter = new ListPlayListAdapter(getActivity(), playLists, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvPlayList.setLayoutManager(manager);
        rvPlayList.setNestedScrollingEnabled(false);
        rvPlayList.setAdapter(playListAdapter);
    }

    private void initListTopicAdapter() {
        listTopicAdapter = new ListTopicAdapter(getActivity(), topics, this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTopics.setLayoutManager(manager);
        rvTopics.setNestedScrollingEnabled(false);
        rvTopics.setAdapter(listTopicAdapter);
    }

    @Override
    protected int getViewResource() {
        return R.layout.fragment_playlist_by_topic;
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
        return playlistByTopicPresenter;
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
    public void changeTopic(int position) {

    }

    @Override
    public void onSelectPlayList(int position) {
    }
}
