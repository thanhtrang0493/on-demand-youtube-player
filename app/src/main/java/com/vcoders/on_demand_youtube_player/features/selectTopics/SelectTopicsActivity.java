package com.vcoders.on_demand_youtube_player.features.selectTopics;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vcoders.on_demand_youtube_player.R;
import com.vcoders.on_demand_youtube_player.adapter.SelectTopicsAdapter;
import com.vcoders.on_demand_youtube_player.architecture.BaseActivity;
import com.vcoders.on_demand_youtube_player.architecture.BaseComponent;
import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.enums.TypeActionBar;
import com.vcoders.on_demand_youtube_player.model.Topic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SelectTopicsActivity extends BaseActivity {

    @BindView(R.id.rvSelectTopics)
    RecyclerView rvSelectTopics;

    SelectTopicsAdapter adapter;

    @Override
    protected void initializeView(Bundle savedInstanceState) {
        initSelectTopicsAdapter();
    }

    private void initSelectTopicsAdapter() {
        List<Topic> topicList = getListTopic();
        adapter = new SelectTopicsAdapter(this, topicList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvSelectTopics.setLayoutManager(manager);
        rvSelectTopics.setAdapter(adapter);
    }

    private List<Topic> getListTopic() {
        List<Topic> topicList = new ArrayList<>();
        topicList.add(new Topic("Travel"));
        topicList.add(new Topic("Music"));
        topicList.add(new Topic("Movies"));
        topicList.add(new Topic("Health"));
        topicList.add(new Topic("Education"));
        topicList.add(new Topic("Pets"));
        topicList.add(new Topic("News"));
        topicList.add(new Topic("Drama"));
        topicList.add(new Topic("Shows"));
        topicList.add(new Topic("Technology"));
        topicList.add(new Topic("Trailers"));
        topicList.add(new Topic("How-To"));
        topicList.add(new Topic("Kids"));
        topicList.add(new Topic("Netflix"));
        topicList.add(new Topic("Meditation"));
        return topicList;
    }

    @Override
    protected int getViewResource() {
        return R.layout.activity_select_topics;
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
    protected BaseRouter getRouter() {
        return null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void inject() {

    }

    @Override
    protected BaseComponent getActivityComponent() {
        return null;
    }
}
