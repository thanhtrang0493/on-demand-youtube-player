package com.vcoders.on_demand_youtube_player.features.onBoarding;

import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.features.selectTopics.SelectTopicsActivity;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import javax.inject.Inject;


public class OnBoardingRouter extends BaseRouter {

    @Inject
    public OnBoardingRouter(Context context) {
        super(context);
    }

    public void toSelectTopic() {
        Utils.getInstance().changeActivity(context, SelectTopicsActivity.class);
    }
}
