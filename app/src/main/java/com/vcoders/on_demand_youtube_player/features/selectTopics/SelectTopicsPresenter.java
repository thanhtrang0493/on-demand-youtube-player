package com.vcoders.on_demand_youtube_player.features.selectTopics;


import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.model.Topic;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SelectTopicsPresenter extends BasePresenter<SelectTopicsView, SelectTopicsRouter> {

    Context context;

    @Inject
    public SelectTopicsPresenter(Context context) {
        this.context = context;
    }

    public List<Topic> getListTopic() {
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

    public void toHome(List<Topic> topics) {
        getRouter().toHome(topics);
    }
}
