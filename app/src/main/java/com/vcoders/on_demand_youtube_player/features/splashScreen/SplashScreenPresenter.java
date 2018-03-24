package com.vcoders.on_demand_youtube_player.features.splashScreen;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.vcoders.on_demand_youtube_player.architecture.BasePresenter;

import javax.inject.Inject;


public class SplashScreenPresenter extends BasePresenter<SplashScreenView, SplashScreenRouter> {

    Context context;
    int DELAY_SPLASH_SCREEN = 1000;

    @Inject
    public SplashScreenPresenter(Context context) {
        this.context = context;
    }

    public void showSplashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getRouter().toOnBoarding();
                ((Activity) context).finish();
            }
        }, DELAY_SPLASH_SCREEN);
    }
}
