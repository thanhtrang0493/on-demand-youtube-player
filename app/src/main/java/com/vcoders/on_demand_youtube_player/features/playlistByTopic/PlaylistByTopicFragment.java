package com.vcoders.on_demand_youtube_player.features.playlistByTopic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.ListPlayListAdapter;
import com.vcoders.on_demand_youtube_player.adapter.ListTopicAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BaseFragment;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.features.home.HomeComponent;
import com.vcoders.on_demand_youtube_player.model.Topic;
import com.vcoders.on_demand_youtube_player.model.Video;
import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.utils.DialogLoading;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class PlaylistByTopicFragment extends BaseFragment<HomeComponent> implements PlaylistByTopicView {

    List<Topic> topics;
    ListTopicAdapter listTopicAdapter;
    List<Video> playLists;
    ListPlayListAdapter playListAdapter;

    @Inject
    PlaylistByTopicPresenter playlistByTopicPresenter;

    @Inject
    PlaylistByTopicRouter playlistByTopicRouter;

    @BindView(R.id.rvTopics)
    RecyclerView rvTopics;

    @BindView(R.id.rvPlayList)
    RecyclerView rvPlayList;

    @Inject
    DialogLoading dialogLoading;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        playLists = new ArrayList<>();
        initListTopicAdapter();
        initPlayListAdapter();
        init();
    }

    private void init() {
        playlistByTopicPresenter.getListTopic(this);
    }

//    private void getBundle() {
//        topics = (List<Topic>) getArguments().getSerializable(Constant.TOPICS);
//        if (topics == null)
//            topics = new ArrayList<>();
//    }


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
        return getActivity().getResources().getString(R.string.home);
    }

    @Override
    protected TypeActionBar[] getTypeActionBar() {
        return new TypeActionBar[]{TypeActionBar.BACK, TypeActionBar.SEARCH};
    }

    @Override
    protected BasePresenter getPresenter() {
        return playlistByTopicPresenter;
    }

    @Override
    protected BaseRouter getRouter() {
        return playlistByTopicRouter;
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
        dialogLoading.show(isShow);
    }

    @Override
    public void changeTopic(int position) {
        playlistByTopicPresenter.getPlaylistByChannelId(topics.get(position).getId());
    }

    @Override
    public void onSelectPlayList(int position) {
        String id = playLists.get(position).getId().toString();
        playlistByTopicPresenter.toListVideo(id);
    }

    @Override
    public void onMorePlayList(int position) {

    }

    @Override
    public void getPlaylistSuccessed(List<Video> playLists) {
        this.playLists = playLists;
        playListAdapter.updateAdapter(this.playLists);
    }

    @Override
    public void onDatabaseResponse(List<Topic> response) {
        if (response != null) {
            this.topics = playlistByTopicPresenter.selectFirstTopic(response);
            if (topics.size() > 0)
                playlistByTopicPresenter.getPlaylistByChannelId(topics.get(0).getId());
            listTopicAdapter.updateAdapter(this.topics);
        }
    }

    @Override
    public void onDatabaseError() {

    }
}
