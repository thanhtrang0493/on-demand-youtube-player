package com.vcoders.on_demand_youtube_player.features.splashScreen;


import android.content.Context;

import com.vcoders.on_demand_youtube_player.architecture.BaseRouter;
import com.vcoders.on_demand_youtube_player.features.onBoarding.OnBoardingActivity;
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
}
