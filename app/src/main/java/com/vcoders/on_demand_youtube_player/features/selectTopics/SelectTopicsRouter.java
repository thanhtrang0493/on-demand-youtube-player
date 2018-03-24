package com.vcoders.on_demand_youtube_player.features.selectTopics;

import android.content.Context;
import android.os.Bundle;

import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.features.home.HomeActivity;
import com.vcoders.on_demand_youtube_player.model.Topic;
import com.vcoders.on_demand_youtube_player.utils.Constant;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;


public class SelectTopicsRouter extends BaseRouter {

    @Inject
    public SelectTopicsRouter(Context context) {
        super(context);
    }

    public void toHome(List<Topic> topics) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.TOPICS, (Serializable) topics);
        Utils.getInstance().changeActivity(context, HomeActivity.class, bundle);
    }
}
