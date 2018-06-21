package com.vcoders.on_demand_youtube_player.features.selectTopics;


import android.app.Activity;
import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.database.tables.topic.TopicLoader;
import com.vcoders.on_demand_youtube_player.model.Topic;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SelectTopicsPresenter extends BasePresenter<SelectTopicsView, SelectTopicsRouter> {

    Context context;
    TopicLoader topicLoader;

    @Inject
    public SelectTopicsPresenter(Context context) {
        this.context = context;
        topicLoader = new TopicLoader(context);
    }

    public List<Topic> getListTopic() {
        List<Topic> topicList = new ArrayList<>();
        topicList.add(new Topic("UCg6gzoAckLMBZIJ4Fw5yBiQ", "Travel"));
        topicList.add(new Topic("UC-9-kyTW8ZkZNDHQJ6FgpwQ", "Music"));
        topicList.add(new Topic("UCdCefhp41oki2DHptq_5rRQ", "Movies"));
        topicList.add(new Topic("UCbqgDzBvusOKtSWvoePrjLA", "Health"));
        topicList.add(new Topic("UC3yA8nDwraeOfnYfBWun83g", "Education"));
        topicList.add(new Topic("UCPIvT-zcQl2H0vabdXJGcpg", "Pets"));
        topicList.add(new Topic("UCYfdidRxbB8Qhf0Nx7ioOYw", "News"));
        topicList.add(new Topic("UC6Vn29fE39QcxEX0yX6p1fQ", "Drama"));
        topicList.add(new Topic("UCDfxwuwJQYqh1t9vYfsBuRw", "Shows"));
        topicList.add(new Topic("UCiDF_uaU1V00dAc8ddKvNxA", "Technology"));
        topicList.add(new Topic("UCi8e0iOVk1fEOogdfu4YgfA", "Trailers"));
        topicList.add(new Topic("UCsP7Bpw36J666Fct5M8u-ZA", "How-To"));
        topicList.add(new Topic("UC5ezaYrzZpyItPSRG27MLpg", "Kids"));
        topicList.add(new Topic("UCWOA1ZGywLbqmigxE4Qlvuw", "Netflix"));
        topicList.add(new Topic("UCM0YvsRfYfsniGAhjvYFOSA", "Meditation"));
        return topicList;
    }

    public void toHome() {
        getRouter().toHome();
        ((Activity) context).finish();
    }

    public void toHome(List<Topic> topics) {
        insertTopic(topics);
        getRouter().toHome();
        ((Activity) context).finish();
    }

    private void insertTopic(List<Topic> topics) {
        if (topics != null) {
            for (Topic topic : topics) {
                topicLoader.addTopicLoader(topic);
            }
        }
    }
}
