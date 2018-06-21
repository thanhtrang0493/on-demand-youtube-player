package com.vcoders.on_demand_youtube_player.features.splashScreen;


import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.features.home.HomeActivity;
import com.vcoders.on_demand_youtube_player.features.onBoarding.OnBoardingActivity;
import com.vcoders.on_demand_youtube_player.features.selectTopics.SelectTopicsActivity;
import com.vcoders.on_demand_youtube_player.utils.Utils;

import javax.inject.Inject;


public class SplashScreenRouter extends BaseRouter {

    @Inject
    public SplashScreenRouter(Context context) {
        super(context);
    }

    public void toOnBoarding() {
        Utils.getInstance().changeActivity(context, OnBoardingActivity.class);
    }

    public void toSelectTopic() {
        Utils.getInstance().changeActivity(context, SelectTopicsActivity.class);
    }

    public void toHome(){
        Utils.getInstance().changeActivity(context, HomeActivity.class);
    }
}
