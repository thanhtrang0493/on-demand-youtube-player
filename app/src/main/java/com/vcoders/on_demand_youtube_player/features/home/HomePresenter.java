package com.vcoders.on_demand_youtube_player.features.home;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;
import com.vcoders.on_demand_youtube_player.database.DatabaseResponseListener;
import com.vcoders.on_demand_youtube_player.database.tables.topic.TopicLoader;
import com.vcoders.on_demand_youtube_player.model.PlayList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class HomePresenter extends BasePresenter<HomeView, HomeRouter> {
    Context context;

    @Inject
    public HomePresenter(Context context) {
        this.context = context;
    }

}
